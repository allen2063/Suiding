package com.suiding.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.application.AppExceptionHandler;
import com.suiding.application.SuidingApp;
import com.suiding.bean.Filter;
import com.suiding.constant.StoreTypeEnum;
import com.suiding.dao.StoreTypeEntityDao;
import com.suiding.domain.IStoreTypeDomain;
import com.suiding.entity.StoreTypeEntity;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.model.StoreType;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.TimeSpan;
import com.suiding.util.XmlCacheUtil;

public class PreSearchActivity extends ActivityBase implements OnClickListener, 
		DialogInterface.OnClickListener {

	private static final String CACHE_DT_CHECK = "PreOrderActivity_CACHE_DT_CHECK";
	private static final String CACHE_IT_INDUSTRY = "PreOrderActivity_CACHE_INDUSTRY";
	private static final String CACHE_IT_INDUSTRYEX = "PreOrderActivity_CACHE_INDUSTRYEX";

	//定期检查行业的时间 间隔 一天
	private static final TimeSpan mCheckSpan = TimeSpan.FromDays(1);
	
	private ModuleTitleOther mTitleLayout;

	private View mBtSubmit = null;
	private View mLayoutIndustry = null;
	private View mLayoutIndustryEx = null;
	private TextView mTvIndustry = null;
	private TextView mTvIndustryEx = null;
	private EditText mEtKeyWord = null;

	private StoreType mType = null;
	private List<StoreType> mltType = null;

	private AlertDialog  mDialogIndustry = null;
	private AlertDialog  mDialogIndustryEx = null;
	
	private Filter mIndustry = new Filter("全部", StoreTypeEnum.ALL);
	private List<Filter> mltIndustry = new ArrayList<Filter>() {
		private static final long serialVersionUID = 1L;
		{
			add(new Filter("全部", StoreTypeEnum.ALL));
			add(new Filter("餐饮", StoreTypeEnum.FOODANDBEVERAGE));
			add(new Filter("酒店", StoreTypeEnum.HOTEL));
			add(new Filter("生活", StoreTypeEnum.LIFE));
			add(new Filter("娱乐", StoreTypeEnum.ENTERTAINMENT));
			add(new Filter("美女", StoreTypeEnum.GIRL));
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_presearch);
		
		mTitleLayout = new ModuleTitleOther(this);
		mTitleLayout.setTitle(R.string.title_activity_preoder);
		mTitleLayout.setOnGoBackListener(this);

		mBtSubmit = findViewById(R.id.presearch_submit);
		mTvIndustry = findTextViewById(R.id.presearch_industry_result);
		mTvIndustryEx = findTextViewById(R.id.presearch_industryex_result);
		mEtKeyWord = findEditTextById(R.id.presearch_enterkeywords_result);
		mLayoutIndustry = findViewById(R.id.presearch_industry);
		mLayoutIndustryEx = findViewById(R.id.presearch_industryex);
		
		mBtSubmit.setOnClickListener(this);
		mTvIndustry.setOnClickListener(this);
		mTvIndustryEx.setOnClickListener(this);
		mLayoutIndustry.setOnClickListener(this);
		mLayoutIndustryEx.setOnClickListener(this);
		
		//获取上次检查更新时间 如果距离现在大于 规定时间间隔 就检查更新
		Date date = XmlCacheUtil.getDate(CACHE_DT_CHECK, 0);
		if(TimeSpan.FromDate(date, new Date()).Compare(mCheckSpan) > 0){
			postTask(new PreSearchTask(PreSearchTask.TASK_CHECKCOUNT));
		}
		//读取上次缓存的类型
		int industry = XmlCacheUtil.getInt(CACHE_IT_INDUSTRY, 0);
		if(industry < mltIndustry.size()){
			mIndustry = mltIndustry.get(industry);
		}

		mTvIndustry.setText(mIndustry.getName());
		doFilterIndustryEx();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case ModuleTitleOther.ID_GOBACK:
			this.finish();
			break;
		case R.id.presearch_industry:
		case R.id.presearch_industry_result:
			showSelectIndustryDialog(mltIndustry);
			break;
		case R.id.presearch_industryex:
		case R.id.presearch_industryex_result:
			showSelectIndustryExDialog(mltType);
			break;
		case R.id.presearch_submit:
			doPerSearch();
			break;
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		if(dialog == mDialogIndustry){
			if(which < mltIndustry.size()){
				mIndustry = mltIndustry.get(which);
				mTvIndustry.setText(mIndustry.getName());
				XmlCacheUtil.putInt(CACHE_IT_INDUSTRY, which);
				doFilterIndustryEx();
			}
		}else if(dialog == mDialogIndustryEx){
			if(which < mltType.size()){
				mType = mltType.get(which);
				mTvIndustryEx.setText(mType.Name);
				XmlCacheUtil.putInt(CACHE_IT_INDUSTRYEX, which);
			}
		}
	}

	public void doFilterIndustryEx() {
		// TODO Auto-generated method stub
		mLayoutIndustryEx.setVisibility(View.GONE);
//		if(mIndustry.getInt() != StoreTypeEnum.ALL){
//			StoreTypeEntityDao dao = new StoreTypeEntityDao(getContext());
//			mltType = StoreTypeEntity.listToModel(dao.getByType(mIndustry.getInt()));
//			if(mltType.size() > 0){
//				mLayoutIndustryEx.setVisibility(View.VISIBLE);
//				mTvIndustryEx.setText("不限");
//				XmlCacheUtil.putInt(CACHE_IT_INDUSTRYEX, 0);
//			}else{
//				mLayoutIndustryEx.setVisibility(View.GONE);
//			}
//		}else{
//			mLayoutIndustryEx.setVisibility(View.GONE);
//		}
	}
	
	private void showSelectIndustryDialog(List<Filter> ltFilter) {
		// TODO Auto-generated method stub
		String[] items = new String[ltFilter.size()];
		for (int i = 0; i < ltFilter.size(); i++) {
			items[i] = ltFilter.get(i).getName();
		}
		
		Builder dialog = new Builder(this);
		dialog.setTitle("选择操作");
		dialog.setIcon(android.R.drawable.ic_dialog_info);
		dialog.setItems(items, this);
		mDialogIndustry = dialog.create();
		mDialogIndustry.show();
	}

	private void showSelectIndustryExDialog(List<StoreType> mlttype) {
		// TODO Auto-generated method stub
		String[] items = new String[mlttype.size()];
		for (int i = 0; i < mlttype.size(); i++) {
			items[i] = mlttype.get(i).Name;
		}
		
		Builder dialog = new Builder(this);
		dialog.setTitle("选择操作");
		dialog.setIcon(android.R.drawable.ic_dialog_info);
		dialog.setItems(items, this);
		mDialogIndustryEx = dialog.create();
		mDialogIndustryEx.show();
	}

	
	private void doPerSearch() {
		// TODO Auto-generated method stub
		String key = mEtKeyWord.getText().toString().trim();
		if(key.length() == 0){
			showToastShort("请输入关键字！");
		}else{
			if(mType != null){
				key = mType.Name + " " + key;
			}
			Intent intent = new Intent();
			intent.setClass(this, SearchActivity.class);
			intent.putExtra(SearchActivity.EXTRA_KEYWORD,key);
			intent.putExtra(SearchActivity.EXTRA_IT_TYPE,mIndustry.getInt());
			startActivity(intent);
		}
	}
	
	private class PreSearchTask extends TaskBase
	{
		public static final int TASK_CHECKCOUNT = 10;
		
		private int idao = 0,idomain = 0;
		private List<StoreType> lttype = null;

		public PreSearchTask(int task) {
			super(SuidingApp.getLooper(),task);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		protected void onWorking(Message msg) throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			case TASK_CHECKCOUNT:{
				IStoreTypeDomain domain = DomainFactory.getStoreTypeDomain();
				StoreTypeEntityDao dao = new StoreTypeEntityDao(getContext());
				idao = dao.getCount();
				idomain = (int)domain.GetRecordCount("");
				if(idao != idomain){
					lttype = domain.GetAll();
					try {
						dao.updateCache(StoreTypeEntity.listFormModel(lttype));
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();//handled
						AppExceptionHandler.handler(e, "搜索选项页，更新类型缓存失败");
					}
				}
			}
				break;
			}
		}
		
		@Override
		protected boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			switch (mTask) {
			case TASK_CHECKCOUNT:
				if(lttype != null && lttype.size() > 0){
					doFilterIndustryEx();
				}
				return true;
			}
			return super.onHandle(msg);
		}
	}

}

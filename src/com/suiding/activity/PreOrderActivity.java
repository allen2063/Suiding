package com.suiding.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.bean.Filter;
import com.suiding.constant.StoreTypeEnum;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.util.XmlCacheUtil;

public class PreOrderActivity extends ActivityBase implements OnClickListener, 
		DialogInterface.OnClickListener {

	private static final String CACHE_INDUSTRY = "PreOrderActivity_CACHE_INDUSTRY";

	private ModuleTitleOther mTitleLayout;

	private View mBtSubmit = null;
	private View mLayoutIndustry = null;
	private View mLayoutIndustryEx = null;
	private TextView mTvIndustry = null;
	private TextView mTvIndustryEx = null;
	private EditText mEtKeyWord = null;
	
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
		setContentView(R.layout.layout_preorder);
		
		mTitleLayout = new ModuleTitleOther(this);
		mTitleLayout.setTitle(R.string.title_activity_preoder);
		mTitleLayout.setOnGoBackListener(this);

		mBtSubmit = findViewById(R.id.preorder_submit);
		mTvIndustry = findTextViewById(R.id.preorder_industry_result);
		mTvIndustryEx = findTextViewById(R.id.preorder_industryex_result);
		mEtKeyWord = findEditTextById(R.id.preorder_enterkeywords_result);
		mLayoutIndustry = findViewById(R.id.preorder_industry);
		mLayoutIndustryEx = findViewById(R.id.preorder_industryex);
		
		mBtSubmit.setOnClickListener(this);
		mTvIndustry.setOnClickListener(this);
		mTvIndustryEx.setOnClickListener(this);
		mLayoutIndustry.setOnClickListener(this);
		mLayoutIndustryEx.setOnClickListener(this);
		mLayoutIndustryEx.setVisibility(View.GONE);
		
		int industry = XmlCacheUtil.getInt(CACHE_INDUSTRY, 0);
		if(industry < mltIndustry.size()){
			mIndustry = mltIndustry.get(industry);
		}

		mTvIndustry.setText(mIndustry.getName());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case ModuleTitleOther.ID_GOBACK:
			this.finish();
			break;
		case R.id.preorder_industry:
		case R.id.preorder_industry_result:
			showSelectDialog(mltIndustry);
			break;
		case R.id.preorder_industryex:
		case R.id.preorder_industryex_result:
			break;
		case R.id.preorder_submit:
			doPerSearch();
			break;
		}
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		if(which < mltIndustry.size()){
			mIndustry = mltIndustry.get(which);
			mTvIndustry.setText(mIndustry.getName());
			XmlCacheUtil.putInt(CACHE_INDUSTRY, which);
		}
	}

	private void showSelectDialog(List<Filter> ltFilter) {
		// TODO Auto-generated method stub
		String[] items = new String[ltFilter.size()];
		for (int i = 0; i < ltFilter.size(); i++) {
			items[i] = ltFilter.get(i).getName();
		}
		
		Builder dialog = new Builder(this);
		dialog.setTitle("选择操作");
		dialog.setIcon(android.R.drawable.ic_dialog_info);
		dialog.setItems(items, this);
		dialog.show();
	}

	private void doPerSearch() {
		// TODO Auto-generated method stub
		String key = mEtKeyWord.getText().toString().trim();
		if(key.length() == 0){
			showToastShort("请输入关键字！");
		}else{
			Intent intent = new Intent();
			intent.setClass(this, SearchActivity.class);
			intent.putExtra(SearchActivity.EXTRA_KEYWORD,key);
			intent.putExtra(SearchActivity.EXTRA_IT_TYPE,mIndustry.getInt());
			startActivity(intent);
		}
	}
}

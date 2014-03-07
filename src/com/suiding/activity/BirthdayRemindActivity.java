package com.suiding.activity;

import java.util.List;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import com.suiding.activity.framework.ListViewActivity;
import com.suiding.adapter.BirthdayListAdapter;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.application.SuidingApp;
import com.suiding.dao.BirthdayEntityDao;
import com.suiding.domain.IBirthFavoriteDomain;
import com.suiding.entity.BirthdayEntity;
import com.suiding.layoutbind.ModuleNodata;
import com.suiding.model.BirthFavorite;
import com.suiding.model.Consumer;
import com.suiding.model.Page;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.ListViewTask;
import com.suiding.util.ExtraUtil;


public class BirthdayRemindActivity extends ListViewActivity
{
    private static final int REQUEST_EDIT = 0;
    
	private int mCurrentIndex = 0;


	@Override
    protected final void onCreate()
    {
        // TODO Auto-generated method stub
        mTitleOther.setTitle(R.string.title_activity_birremind);
    }

    @Override
    protected final ListAdapterBase getAdapter(List<Object> ltData)
    {
        // TODO Auto-generated method stub
        return new BirthdayListAdapter(this, ltData, true);
    }

    @Override
    protected final ListViewTask getTask(int task)
    {
        // TODO Auto-generated method stub
        return new BirthdayTask(task);
    }

    /**
     * 当服务器加载数据为空的时候调用
     * 	处理信息提示 如果不重写这个函数
     * 	默认的提示是 抱歉，附近无相关数据
     */
	@Override
	protected final void onNodata(ModuleNodata nodata) {
		// TODO Auto-generated method stub
        //设置空数据页面
		nodata.setDescription(ModuleNodata.TEXT_NOREMIND);
	}
	
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			if(requestCode == REQUEST_EDIT){
				Object obj = ExtraUtil.getExtra(BirthdayAddActivity.EXTRA_DATA);
				mAdapter.setData(mCurrentIndex,obj);
			}
		}
	}
	
    @Override
	public void onItemClick(AdapterView<?> absview, View view, int index,
			long id) {
		// TODO Auto-generated method stub
    	index = mModuleListView.getIndex(index);
    	if(index >= 0)
    	{
    		mCurrentIndex  = index;
    		Intent intent = new Intent();
    		Object data = mltData.get(index);
    		intent.setClass(this,BirthdayAddActivity.class);
    		ExtraUtil.putExtra(BirthdayAddActivity.EXTRA_DATA, data);
    		ExtraUtil.putExtra(BirthdayAddActivity.EXTRA_TYPE, BirthdayAddActivity.EDIT);
    		startActivityForResult(intent,REQUEST_EDIT);
    	}
	}



	private class BirthdayTask extends ListViewTask
    {
        public BirthdayTask(int task)
        {
            super(new Handler(BirthdayRemindActivity.this), task);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected List<? extends Object> onLoad()
        {
            // TODO Auto-generated method stub
            BirthdayEntityDao dao = new BirthdayEntityDao(
                    getBaseContext());
            List<BirthdayEntity> ltEntity = dao.getAll();
            List<BirthFavorite> ltData = BirthdayEntity
                    .listToModel(ltEntity);
            return ltData;
        }

        @Override
        protected List<? extends Object> onRefresh() throws Exception
        {
            // TODO Auto-generated method stub
            if (SuidingApp.getLoginUser() == null)
            {
                Thread.sleep(1000);
                return mltData;
            }
            else
            {
                Consumer user = SuidingApp.getLoginUser();
                //获取数据接口
                IBirthFavoriteDomain domain = DomainFactory
                        .getBirthFavoriteDomain();
                List<BirthFavorite> ltData = domain.getListByUserID(
                        user.getID(), new Page(100, 0));
                //更新缓存
                BirthdayEntityDao dao = new BirthdayEntityDao(
                        getBaseContext());
                dao.updateCache(BirthdayEntity.listFormModel(ltData));
                return ltData;
            }
        }

    }

}

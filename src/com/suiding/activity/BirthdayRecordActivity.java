package com.suiding.activity;

import java.util.List;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.suiding.activity.framework.ListViewActivity;
import com.suiding.adapter.BirthdayListAdapter;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.application.SuidingApp;
import com.suiding.dao.BirthdayEntityDao;
import com.suiding.domain.IBirthFavoriteDomain;
import com.suiding.entity.BirthdayEntity;
import com.suiding.layoutbind.ModuleNodata;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.model.BirthFavorite;
import com.suiding.model.Consumer;
import com.suiding.model.Page;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.ListViewTask;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.ExtraUtil;


public class BirthdayRecordActivity extends ListViewActivity implements OnClickListener
{
	private static final int REQUEST_EDIT = 0;
	
	private int mIndex = 0;
	private int mResumeCount = 0;

	private BirthFavorite mBirthFavorite = null;
    @Override
    protected final void onCreate()
    {
        // TODO Auto-generated method stub
        mTitleOther.setOnAddListener(this);
        mTitleOther.setFunction(ModuleTitleOther.FUNCTION_ADD);
        mTitleOther.setTitle(R.string.title_activity_birrecord);
    }

    @Override
    protected final ListViewTask getTask(int task)
    {
        // TODO Auto-generated method stub
        return new BirthdayTask(task);
    }

    @Override
    protected final ListAdapterBase getAdapter(List<Object> ltData)
    {
        // TODO Auto-generated method stub
        return new BirthdayListAdapter(this,ltData);
    }

    /**
     * onResume ������ �����ҳ�淵�ص� ���ݿ��ܱ����
     * 	��ӵ����ݳ��˱���ӵ���������Ҳ��ֱ����ӵ�����
     * 	����TASK_LOAD���¼��ػ��漴�ɸ�����ӵ�����
     *  mResumeCount ���ڱ���һ�� onResume
     */
    @Override
	protected final void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mResumeCount++;
		if(mResumeCount > 1)
		{
			postTask(new BirthdayTask(TaskBase.TASK_LOAD));
		}
	}
    
    /**
     * ����������������Ϊ�յ�ʱ�����
     * 	������Ϣ��ʾ �������д�������
     * 	Ĭ�ϵ���ʾ�� ��Ǹ���������������
     */
	@Override
	protected final void onNodata(ModuleNodata nodata) {
		// TODO Auto-generated method stub
        //���ÿ�����ҳ��
		nodata.setButtonText(ModuleNodata.TEXT_TOADD);
		nodata.setDescription(ModuleNodata.TEXT_NOBIRTHDAY);
	}

    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			if(requestCode == REQUEST_EDIT){
				Object obj = ExtraUtil.getExtra(BirthdayAddActivity.EXTRA_DATA);
				mAdapter.setData(mIndex,obj);
			}
		}
	}

	@Override
    public final void onItemClick(AdapterView<?> absview, View view, int index,
            long id)
    {
        // TODO Auto-generated method stub
    	index = mModuleListView.getIndex(index);
    	if(index >= 0)
    	{
    		mIndex = index;
    		Intent intent = new Intent();
    		Object data = mltData.get(index);
    		intent.setClass(this,BirthdayAddActivity.class);
    		ExtraUtil.putExtra(BirthdayAddActivity.EXTRA_DATA, data);
    		ExtraUtil.putExtra(BirthdayAddActivity.EXTRA_TYPE, BirthdayAddActivity.EDIT);
    		startActivityForResult(intent,REQUEST_EDIT);
    	}
    }

    @Override
	public boolean onItemLongClick(AdapterView<?> absview, View view,
			int index, long id) {
		// TODO Auto-generated method stub
    	index = mModuleListView.getIndex(index);
    	if(index >= 0)
    	{
    		mIndex = index;
    		mBirthFavorite = (BirthFavorite)mltData.get(index);
    		Builder dialog = new Builder(this);
    		dialog.setTitle("ѡ�����");
    		dialog.setIcon(android.R.drawable.ic_dialog_info);
    		dialog.setItems(new String[] { "ɾ����¼", "�鿴��ϸ"}, this);
    		dialog.show();
    	}
		return super.onItemLongClick(absview, view, index, id);
	}
    
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		switch (which) {
		case 0:
			postTask(new BirthdayTask(BirthdayTask.TASK_DELETE));
			showProgressDialog("����ɾ��...");
			break;
		case 1:
    		Intent intent = new Intent();
    		Object data = mltData.get(mIndex);
    		intent.setClass(this,BirthdayAddActivity.class);
    		ExtraUtil.putExtra(BirthdayAddActivity.EXTRA_DATA, data);
    		ExtraUtil.putExtra(BirthdayAddActivity.EXTRA_TYPE, BirthdayAddActivity.EDIT);
    		startActivityForResult(intent,REQUEST_EDIT);
			break;
		}
	}

	@Override
    public final void onClick(View v)
    {
        // TODO Auto-generated method stub
        super.onClick(v);
        switch(v.getId()){
            case ModuleTitleOther.ID_ADD:
            case ModuleNodata.TEXT_TOADD:
                startActivity(new Intent(this, BirthdaySourceActivity.class));
                break;
        }
    }
	
	
    
    @Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
    	if(super.handleMessage(msg) == false)
    	{
    		BirthdayTask tTask = (BirthdayTask) msg.obj;
    		if(tTask.mResult == TaskBase.RESULT_FINISH){
    			if(tTask.mTask == BirthdayTask.TASK_DELETE){
    				mAdapter.remove(mIndex);
    				Toast.makeText(this, "ɾ���ɹ�", Toast.LENGTH_LONG).show();
    			}
    		}else if(tTask.mResult == TaskBase.RESULT_FAIL){

    		}
    		hideProgressDialog();
    	}
		return true;
	}



	private class BirthdayTask extends ListViewTask
    {
        public static final int TASK_DELETE = 10000;

		public BirthdayTask(int task)
        {
            super(new Handler(BirthdayRecordActivity.this), task);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected List<? extends Object> onLoad()
        {
            // TODO Auto-generated method stub
            BirthdayEntityDao dao = new BirthdayEntityDao(getBaseContext());
            List<BirthdayEntity> ltEntity = dao.getAll();
            List<BirthFavorite> ltData = BirthdayEntity.listToModel(ltEntity);
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
                //��ȡ���ݽӿ�
                IBirthFavoriteDomain domain = DomainFactory.getBirthFavoriteDomain();
                List<BirthFavorite> ltData = domain.getListByUserID(user.getID(), new Page(100, 0));
                //���»���
                BirthdayEntityDao dao = new BirthdayEntityDao(getBaseContext());
                dao.updateCache(BirthdayEntity.listFormModel(ltData));
                
                return ltData;
            }
        }

		@Override
		protected boolean onWorking() throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			case TASK_DELETE:
                //��ȡ���ݽӿ�
                IBirthFavoriteDomain domain = DomainFactory.getBirthFavoriteDomain();
                domain.DeleteByID(mBirthFavorite.getID());
                //���»���
                BirthdayEntityDao dao = new BirthdayEntityDao(getBaseContext());
                dao.delById(mBirthFavorite.getID());
				break;
			}
			return true;
		}

        
    }

}

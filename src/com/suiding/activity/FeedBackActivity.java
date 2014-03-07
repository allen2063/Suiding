package com.suiding.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.application.SuidingApp;
import com.suiding.domain.IFeedbackDomain;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.model.Consumer;
import com.suiding.model.Feedback;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.NetworkUtil;

public class FeedBackActivity extends ActivityBase implements OnClickListener, Callback
{

    private EditText mEtTitle = null;
    private EditText mEtContent = null;
    private Button mBtSubmit = null;

    private ModuleTitleOther mTitleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_feedback);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mEtTitle = (EditText)findViewById(R.id.feedback_title);
        mEtContent = (EditText)findViewById(R.id.feedback_text);
        mBtSubmit = (Button)findViewById(R.id.feedback_submit);
        mTitleLayout = new ModuleTitleOther(this);

        mTitleLayout.setTitle(R.string.title_activity_feedback);
        
        mBtSubmit.setOnClickListener(this);
        mTitleLayout.setOnGoBackListener(this);
    }

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        switch (v.getId())
        {
            case R.id.feedback_submit:
                this.feedBack();
                break;
            case ModuleTitleOther.ID_GOBACK:
                this.finish();
                break;
        }
    }

    /**
     * 执行反馈信息操作
     */
    private void feedBack()
    {
        // TODO Auto-generated method stub
        String tTitle = mEtTitle.getText().toString();
        String tContent = mEtContent.getText().toString();

        if(tTitle.length() == 0)
        {
            Toast.makeText(this, "标题不能为空！", Toast.LENGTH_LONG).show();
            return;
        }
        else  if(tContent.length() == 0)
        {
            Toast.makeText(this, "内容能够不能为空！", Toast.LENGTH_LONG).show();
            return;
        }
        
    	Feedback feedback = new Feedback();
    	feedback.Name = tTitle;
    	feedback.Content = tContent;
    	feedback.CurVersion = SuidingApp.getVersionCode();
    	
    	Consumer user = SuidingApp.getLoginUser();
    	if(user != null)
    	{
    		feedback.UserID = user.getID();
    	}
        
        //显示对话框
        showProgressDialog("正在发送...");
        
        //把任务放到线程中开始工作
        postTask(new FeedBackTask(feedback));
    }


    public boolean handleMessage(Message msg) {
        // TODO Auto-generated constructor stub
        //FeedBackTask tRegisterTask = (FeedBackTask)msg.obj;
        if(msg.what == FeedBackTask.RESULT_FINISH){
            Toast.makeText(SuidingApp.getAppContext(), "感谢你的宝贵意见，我们会好好处理和解决的。", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        else if(msg.what == FeedBackTask.RESULT_FAIL){
            Toast.makeText(this,"网络不给力啊，再试一次吧~", Toast.LENGTH_SHORT).show();
        }
        hideProgressDialog();
        return true;
    }

    private class FeedBackTask extends TaskBase
    {
    	public Feedback mFeedback = null;

        public FeedBackTask(Feedback feedback)
        {
            super(new Handler(FeedBackActivity.this));
            // TODO Auto-generated method stub
            this.mFeedback = feedback;
        }

        @Override
        protected void onWorking(Message tMessage) throws Exception
        {
            // TODO Auto-generated method stub
        	if(SuidingApp.getNetworkStatus() == NetworkUtil.TYPE_NONE)
        	{
        		throw new Exception("您的网络出现了问题，请检查是否连接到网络！");
        	}
        	IFeedbackDomain domain = DomainFactory.getFeedbackDomain();
        	domain.Insert(mFeedback);
        }
    }
}

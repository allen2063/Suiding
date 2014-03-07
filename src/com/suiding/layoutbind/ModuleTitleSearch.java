package com.suiding.layoutbind;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.SearchActivity;
import com.suiding.activity.framework.Pageable;
import com.suiding.layoutbind.framework.ILayout;


public class ModuleTitleSearch implements OnClickListener,ILayout
{
    private View mLayout = null;
    private View mBtSubmit = null;
    private View mBtGoback = null;
    private Boolean mIsValid = false;
    private TextView mTvAppName = null;
    private EditText mEtKeyword = null;
    private Activity mActivity = null;

    public ModuleTitleSearch(Pageable page)
    {
        // TODO Auto-generated constructor stub
    	mActivity = page.getActivity();
    	mBtSubmit = page.findViewById(R.id.titlebar_search_submit);
    	mBtGoback = page.findViewById(R.id.titlebar_search_goback);
    	mTvAppName = page.findTextViewById(R.id.titlebar_search_appname);
    	mEtKeyword = page.findEditTextById(R.id.titlebar_search_et_keyword);
        initialize();
    }
    
    private void initialize() {
		// TODO Auto-generated method stub
    	if(mEtKeyword != null){
            mIsValid = true;
    		mLayout = (View) mEtKeyword.getParent();
    		mBtSubmit.setOnClickListener(this);
    		mBtGoback.setOnClickListener(this);
    	}
	}

    public void setOnSubmitClickListener(OnClickListener listener) {
    	mBtSubmit.setOnClickListener(listener);
	}
    

	public void setGoBackEnable(boolean enable) {
		// TODO Auto-generated method stub
    	if(mIsValid){
    		mBtGoback.setVisibility(enable?View.VISIBLE:View.GONE);
    		mTvAppName.setVisibility(!enable?View.VISIBLE:View.GONE);
    	}
	}
	
    public View getLayout()
    {
        return mLayout;
    }

    public void hide()
    {
        // TODO Auto-generated constructor stub
        mLayout.setVisibility(View.GONE);
    }

    public void show()
    {
        // TODO Auto-generated constructor stub
        mLayout.setVisibility(View.VISIBLE);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == mBtGoback){
			mActivity.finish();
		}else if(v == mBtSubmit){
			Intent intent = new Intent();
			String keyword = mEtKeyword.getText().toString();
			intent.setClass(mActivity, SearchActivity.class);
			intent.putExtra(SearchActivity.EXTRA_KEYWORD, keyword);
			mActivity.startActivity(intent);
		}
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return mIsValid;
	}
    
    
}

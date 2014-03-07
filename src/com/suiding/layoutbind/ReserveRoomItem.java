package com.suiding.layoutbind;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.layoutbind.framework.ILayout;

public class ReserveRoomItem implements ILayout
{
    public View mLayout = null;
    public TextView mTextView = null;
    public EditText mEditText = null;
    
    private boolean mIsValid = false;

    public ReserveRoomItem(Activity activity)
    {
        // TODO Auto-generated constructor stub
        mTextView=(TextView)activity.findViewById(R.id.reserve_info_item_tv);
        mEditText=(EditText)activity.findViewById(R.id.reserve_info_item_et);
        mLayout = (View) mTextView.getParent().getParent();
        mIsValid = mLayout != null;
    }

    public ReserveRoomItem(View view)
    {
        // TODO Auto-generated constructor stub
    	mTextView=(TextView)view.findViewById(R.id.reserve_info_item_tv);
        mEditText=(EditText)view.findViewById(R.id.reserve_info_item_et);
        mLayout = (View) mTextView.getParent().getParent();
        mIsValid = mLayout != null;
    }    
    
    
    public void setText(String text)
    {
        // TODO Auto-generated constructor stub
        mTextView.setText(text);
    }

    public void setEditText(String text)
    {
        // TODO Auto-generated constructor stub
        mEditText.setText(text);
    }

    public String getEditText()
    {
        // TODO Auto-generated constructor stub
        return mEditText.getText().toString();
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
	public View getLayout() {
		// TODO Auto-generated method stub
		return mLayout;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return mIsValid;
	}
}

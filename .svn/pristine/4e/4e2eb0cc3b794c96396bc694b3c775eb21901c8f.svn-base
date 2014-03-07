package com.suiding.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.util.ExtraUtil;
import com.suiding.activity.framework.ActivityBase;


public class EditTextActivity extends ActivityBase implements OnClickListener
{
    public static final String EXTRA_FUNCTION = "edittext_extra_function";
    public static final String EXTRA_TITLE = "edittext_extra_title";
    public static final String EXTRA_VALUE = "edittext_extra_value";
    public static final String EXTRA_ALLOWNULL = "edittext_extra_allownull";
    public static final String EXTRA_CALLID = "edittext_extra_callid";
    public static final String EXTRA_HINT = "edittext_extra_hint";
    public static final String EXTRA_RESULT = "edittext_extra_result";

    public static final int FUNCTION_SINGLE = 0;
    public static final int FUNCTION_MULTI = 1;

    private Integer mCallid = 0;
    private Integer mFunction = FUNCTION_SINGLE;
    private Integer mTitle = R.string.app_name;
    private Integer mHint = R.string.edittext_hint;
    private Boolean mAllowNull = true;
    private String mValue = "";
    
    private ModuleTitleOther mLayoutTitle = null;

    private View mLayoutSingle = null;
    private View mLayoutMulti = null;

    private EditText mEtSingle = null;
    private EditText mEtMulti = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edittext);
        
        mLayoutTitle = new ModuleTitleOther(this,ModuleTitleOther.FUNCTION_OK);

        mLayoutMulti = findViewById(R.id.deittext_layout_multiline);
        mLayoutSingle = findViewById(R.id.deittext_layout_singleline);

        mEtSingle = (EditText)findViewById(R.id.deittext_edit_singleline);
        mEtMulti = (EditText)findViewById(R.id.deittext_edit_multiline);
        
        mLayoutTitle.setOnOkListener(this);
        mLayoutTitle.setOnFinishListener(this);

        mHint = (Integer)ExtraUtil.getExtra(EXTRA_HINT,mHint);
        mValue = (String)ExtraUtil.getExtra(EXTRA_VALUE, mValue);
        mCallid = (Integer)ExtraUtil.getExtra(EXTRA_CALLID,mCallid);
        mTitle = (Integer)ExtraUtil.getExtra(EXTRA_TITLE,mTitle);
        mFunction = (Integer)ExtraUtil.getExtra(EXTRA_FUNCTION,mFunction);
        mAllowNull = (Boolean)ExtraUtil.getExtra(EXTRA_ALLOWNULL,mAllowNull);
        
        
        mLayoutTitle.setTitle(mTitle);
        
        if(mFunction == FUNCTION_MULTI){
            mLayoutSingle.setVisibility(View.GONE);
            mEtMulti.setText(mValue);
            mEtMulti.setHint(mHint);
        }else{
            mLayoutMulti.setVisibility(View.GONE);
            mEtSingle.setText(mValue);
            mEtSingle.setHint(mHint);
        }

    }

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        switch (v.getId())
        {
            case ModuleTitleOther.ID_GOBACK:
                this.finish();
                break;
            case ModuleTitleOther.ID_OK:
                EditText tEditText = null;
                if(mFunction == FUNCTION_SINGLE){
                    tEditText = mEtSingle;
                }else if(mFunction == FUNCTION_MULTI){
                    tEditText = mEtMulti;
                }
                if(tEditText != null)
                {
                    if(mAllowNull == false && tEditText.getText().length()==0)
                    {
                        Toast.makeText(this, "输入内容不能为空！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String value = tEditText.getText().toString();
                    if(!value.equals(mValue)){
                        Intent result = new Intent();
                        result.putExtra(EXTRA_RESULT, value);
                        result.putExtra(EXTRA_CALLID, mCallid);
                        setResult(RESULT_OK,result);
                    }
                }
                this.finish();
                break;
        }
    }

}

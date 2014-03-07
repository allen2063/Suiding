package com.suiding.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.suiding.activity.framework.ActivityBase;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.widget.DashedLine;

public class HelperActivity extends ActivityBase implements OnClickListener {
	private ModuleTitleOther mLayoutTitle;
	private TextView txt_ques1 = null;
	private TextView txt_ques2 = null;
	private TextView txt_answer_ques1 = null;
	private TextView txt_answer_ques2 = null;
	private DashedLine divider1 = null;
	private DashedLine divider2 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_helper);
		mLayoutTitle = new ModuleTitleOther(this);
		mLayoutTitle.setTitle(R.string.title_activity_payhelp);
		txt_ques1 = (TextView) findViewById(R.id.helper_question1);
		txt_ques2 = (TextView) findViewById(R.id.helper_question2);
		txt_answer_ques1 = (TextView) findViewById(R.id.answer_ques1);
		txt_answer_ques2 = (TextView) findViewById(R.id.helper_answer2);
		divider1 = (DashedLine) findViewById(R.id.divider1);
		divider2 = (DashedLine) findViewById(R.id.divider2);

		txt_ques1.setOnClickListener(this);
		txt_ques2.setOnClickListener(this);
		mLayoutTitle.setOnGoBackListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.helper_question1:
			if (txt_answer_ques1.getVisibility() == View.GONE) {
				txt_answer_ques1.setVisibility(View.VISIBLE);
				divider1.setVisibility(View.VISIBLE);
			} else {
				txt_answer_ques1.setVisibility(View.GONE);
				divider1.setVisibility(View.GONE);
			}
			break;
		case R.id.helper_question2:
			if (txt_answer_ques2.getVisibility() == View.GONE) {
				txt_answer_ques2.setVisibility(View.VISIBLE);
				divider2.setVisibility(View.VISIBLE);
			} else {
				txt_answer_ques2.setVisibility(View.GONE);
				divider2.setVisibility(View.GONE);
			}
			break;
		case ModuleTitleOther.ID_GOBACK:
			this.finish();
			break;
		}
	}
}

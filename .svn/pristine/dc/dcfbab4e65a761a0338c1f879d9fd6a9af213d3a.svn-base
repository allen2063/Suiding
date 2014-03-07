package com.suiding.layoutbind;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.application.AppExceptionHandler;
import com.suiding.layoutbind.framework.ILayout;
import com.suiding.model.Business;
import com.suiding.util.CallPhoneUtil;

public class ModuleMerchant implements OnClickListener, ILayout {
	private TextView mTvStatus = null;
	private ProgressBar mProgressBar = null;

	private View mPanel = null;
	private View mLayout = null;

	private TextView mTvName = null;
	private TextView mTvCompany = null;
	private TextView mTvTelephone = null;
	private ImageView mIvTelephone = null;

	private boolean mIsValid = false;

	private OnMerchantClickListener mListener = null;
	private String mPhone;

	public interface OnMerchantClickListener {
		boolean onRefreshMerchant();
	}

	public ModuleMerchant(Pageable page) {
		// TODO Auto-generated constructor stub
		mPanel = page.findViewById(R.id.module_merchant_panel);
		mLayout = page.findViewById(R.id.module_merchant_title);
		mTvStatus = page.findTextViewById(R.id.module_merchant_status);
		mProgressBar = page.findProgressBarById(R.id.module_merchant_progress);

		mTvName = page.findTextViewById(R.id.module_merchant_name);
		mTvCompany = page.findTextViewById(R.id.module_merchant_company);
		mTvTelephone = page.findTextViewById(R.id.module_merchant_phone);
		mIvTelephone = page.findImageViewById(R.id.module_merchant_phoneimg);

		if (mLayout != null) {
			mIsValid = true;
			mLayout = (View) mLayout.getParent();

		}
	}

	public void setOnMerchantClickListener(OnMerchantClickListener listener) {
		this.mListener = listener;
	}

	public void setMerchant(Business business) {
		// TODO Auto-generated method stub
		try {
			if (business != null) {
				mPanel.setVisibility(View.VISIBLE);
				mTvName.setText("商家名称：" + business.Name);
				mTvCompany.setText("公司名称：" + business.CompanyNmae);
				mTvTelephone.setText("电话号码：" + business.PhoneNo);
				if (business.PhoneNo != null && business.PhoneNo.length() > 0) {
					mPhone = business.PhoneNo.replace("-", "");
					mIvTelephone.setEnabled(true);
					mIvTelephone.setOnClickListener(this);
				} else {
					mIvTelephone.setEnabled(false);
				}
			} else {
				mPanel.setVisibility(View.GONE);
			}
			mTvStatus.setOnClickListener(this);
			mTvStatus.setText("查无数据，点击刷新");
			mProgressBar.setVisibility(View.GONE);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();//handled
			AppExceptionHandler.handler(e, "商家控件，设置数据 出现异常");
		}
	}

	public void setLoadFail() {
		if (mIsValid) {
			mPanel.setVisibility(View.GONE);
			mTvStatus.setOnClickListener(this);
			mProgressBar.setVisibility(View.GONE);
			mTvStatus.setText(R.string.module_comment_error);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (mListener != null) {
			if (v.getId() == R.id.module_merchant_status) {
				if (mListener.onRefreshMerchant()) {
					mTvStatus.setOnClickListener(null);
					mTvStatus.setText(R.string.module_comment_refreshing);
					mProgressBar.setVisibility(View.VISIBLE);
				}
			} else if (v.getId() == R.id.module_merchant_phoneimg) {
				CallPhoneUtil.call(v.getContext(), mPhone);
			}
		}
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		if (mIsValid) {
			mLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		if (mIsValid) {
			mLayout.setVisibility(View.VISIBLE);
		}
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

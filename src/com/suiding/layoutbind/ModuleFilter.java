package com.suiding.layoutbind;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.adapter.FilterListAdapter;
import com.suiding.bean.Filter;
import com.suiding.layoutbind.ModulePopupList.IPopupList;
import com.suiding.layoutbind.framework.ILayout;

public class ModuleFilter implements ILayout, IPopupList, OnClickListener {

	public interface IFilter {
		/**
		 * 点击条件筛选
		 * @param type	筛选的类型
		 * @param index 筛选值所在的序号
		 * @param filter 筛选值
		 */
		void onFilter(int type,int index, Filter filter);
	}

	public static final int TYPE = 0;
	public static final int AREA = 1;
	public static final int SORT = 2;

	private View mLayout = null;
	private TextView mTvType = null;
	private TextView mTvArea = null;
	private TextView mTvSort = null;
	private boolean mIsValid = true;
	private TextView[] mTvFilter = new TextView[3];

	private List<Filter> mltType = null;
	private List<Filter> mltArea = null;
	private List<Filter> mltSort = null;

	private IFilter mIFilter = null;
	private Context mContext = null;
	private ModulePopupList mPopuplist = null;
	private FilterListAdapter[] mAdapters = new FilterListAdapter[3];

	public ModuleFilter(Pageable page, IFilter iFilter) {
		// TODO Auto-generated constructor stub
		mIFilter = iFilter;
		mPopuplist = new ModulePopupList(page, this);
		mLayout = page.findViewById(R.id.module_filter_layout);
		mTvType = page.findTextViewById(R.id.module_filter_type);
		mTvArea = page.findTextViewById(R.id.module_filter_area);
		mTvSort = page.findTextViewById(R.id.module_filter_sort);
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		mIsValid = mLayout != null;
		if (mPopuplist.isValid() && mIsValid) {
			mLayout = (View) mLayout.getParent();
			mContext = mLayout.getContext();
			mTvType.setOnClickListener(this);
			mTvArea.setOnClickListener(this);
			mTvSort.setOnClickListener(this);
			mTvFilter[0] = mTvType;
			mTvFilter[1] = mTvArea;
			mTvFilter[2] = mTvSort;
		}
	}

	public void selectFilter(int type, Filter filter) {
		if (mPopuplist.isValid() && mIsValid) {
			List<Filter> ltFilter = null;
			switch (type) {
			case TYPE:
				ltFilter = mltType;
				break;
			case AREA:
				ltFilter = mltArea;
				break;
			case SORT:
				ltFilter = mltSort;
				break;
			}
			if (ltFilter != null) {
				for (int i = 0; i < ltFilter.size(); i++) {
					Filter tFilter = ltFilter.get(i);
					if (tFilter.getValue().equals(filter.getValue())) {
						mAdapters[type].selectItem(i);
						mTvFilter[type].setText(tFilter.getName());
						break;
					}
				}
			}
		}
	}

	public void setFilter(int type, Collection<Filter> collect) {
		if (mPopuplist.isValid() && mIsValid && type < 3) {
			mAdapters[type] = new FilterListAdapter(mContext, collect);
			mPopuplist.setAdapter(type, mAdapters[type]);
			switch (type) {
			case TYPE:
				mltType = new ArrayList<Filter>(collect);
				mTvType.setText(mltType.get(0).getName());
				break;
			case AREA:
				mltArea = new ArrayList<Filter>(collect);
				mTvArea.setText(mltArea.get(0).getName());
				break;
			case SORT:
				mltSort = new ArrayList<Filter>(collect);
				mTvSort.setText(mltSort.get(0).getName());
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.module_filter_type:
			mPopuplist.popup(TYPE);
			break;
		case R.id.module_filter_area:
			mPopuplist.popup(AREA);
			break;
		case R.id.module_filter_sort:
			mPopuplist.popup(SORT);
			break;
		}
	}

	@Override
	public void onPopupItemClick(int type, int index) {
		// TODO Auto-generated method stub
		mAdapters[type].selectItem(index);
		switch (type) {
		case TYPE:
			mIFilter.onFilter(type, index, mltType.get(index));
			mTvType.setText(mltType.get(index).getName());
			break;
		case AREA:
			mIFilter.onFilter(type, index, mltArea.get(index));
			mTvArea.setText(mltArea.get(index).getName());
			break;
		case SORT:
			mIFilter.onFilter(type, index, mltSort.get(index));
			mTvSort.setText(mltSort.get(index).getName());
			break;
		}
	}

	public View getLayout() {
		return mLayout;
	}

	public void hide() {
		// TODO Auto-generated constructor stub
		if (mLayout.getVisibility() == View.VISIBLE) {
			mLayout.setVisibility(View.GONE);
			mPopuplist.hide();
		}
	}

	public void show() {
		// TODO Auto-generated constructor stub
		if (mLayout.getVisibility() != View.VISIBLE) {
			mLayout.setVisibility(View.VISIBLE);
			//mPopuplist.show();
		}
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return mIsValid;
	}
}

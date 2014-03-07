package com.suiding.layoutbind;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.suiding.activity.R;
import com.suiding.activity.framework.Pageable;
import com.suiding.layoutbind.framework.ILayout;

public class ModulePopupList implements ILayout, OnItemClickListener, OnClickListener{

	public interface IPopupList{
		/**
		 * 点击筛选列表项
		 * @param filter 筛选列表序号（筛选类型）
		 * @param index 点击列表中的序号
		 */
		void onPopupItemClick(int filter,int index);
	}
	
	private View mLayout = null;
	private List<ListView> mltListView = null;
	private boolean mIsValid = true;
	private IPopupList mIPopupList = null;

	public ModulePopupList(Pageable page,IPopupList iPopupList) {
		// TODO Auto-generated constructor stub
		mIPopupList = iPopupList;
		mLayout = page.findViewById(R.id.module_popuplist_layout);
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		mIsValid = mLayout != null;
		if(mIsValid && mLayout instanceof ViewGroup)
		{
			ViewGroup group = (ViewGroup)mLayout;
			mltListView = new ArrayList<ListView>();
			int count = group.getChildCount();
            for (int i = 0; i < count; i++)
            {
            	View view = group.getChildAt(i);
                if(view instanceof ListView)
                {
                	mltListView.add((ListView)view);
                }
            }
            mLayout.setOnClickListener(this);
            this.hide();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> absview, View view, int index, long id) {
		// TODO Auto-generated method stub
		for (int i = 0; i < mltListView.size(); i++) {
			if(mltListView.get(i) == absview)
			{
	            this.hide();
				mIPopupList.onPopupItemClick(i,index);
				break;
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == mLayout)
		{
            this.hide();
		}
	}

	
	public void setAdapter(int index,ListAdapter adapter)
	{
		if(mltListView != null && index < mltListView.size())
		{
			ListView listview = mltListView.get(index);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(this);
		}
	}
	
	public void popup(int index)
	{
		if(mltListView != null && index < mltListView.size())
		{
			for (int i = 0; i < mltListView.size(); i++) {
				ListView listview = mltListView.get(i);
				listview.setVisibility(i==index?View.VISIBLE:View.INVISIBLE);
			}
			this.show();
		}
	}

//	public void selectItem(int index, int i) {
//		// TODO Auto-generated method stub
//		if(mltListView != null && index < mltListView.size())
//		{
//			for (int i = 0; i < mltListView.size(); i++) {
//				ListView listview = mltListView.get(i);
//				listview.setVisibility(i==index?View.VISIBLE:View.INVISIBLE);
//			}
//			this.show();
//		}
//	}
	
	public View getLayout() {
		return mLayout;
	}

	public void hide() {
		// TODO Auto-generated constructor stub
		if (mLayout.getVisibility() == View.VISIBLE) {
			mLayout.setVisibility(View.GONE);
		}
	}

	public void show() {
		// TODO Auto-generated constructor stub
		if (mLayout.getVisibility() != View.VISIBLE) {
			mLayout.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return mIsValid;
	}

}

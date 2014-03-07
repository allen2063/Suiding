package com.suiding.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.bean.Filter;


public class FilterListAdapter extends BaseAdapter
{
    private int mIndex = 0;
    private int mColor = 0;
    private List<Filter> mltData = null;
    private LayoutInflater mInflater = null;

    public FilterListAdapter(Context context, Collection<Filter> collect)
    {
        mltData = new ArrayList<Filter>(collect);
        mInflater = LayoutInflater.from(context);
        mColor = context.getResources().getColor(
                R.color.theme_gray_dark);
    }

    public void selectItem(int index)
    {
        // TODO Auto-generated method stub
        mIndex = index;
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return mltData.size();
    }

    @Override
    public Object getItem(int arg0)
    {
        // TODO Auto-generated method stub
        return mltData.get(arg0);
    }

    @Override
    public long getItemId(int arg0)
    {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int index, View view, ViewGroup arg2)
    {
        // TODO Auto-generated method stub
        TextView tTextView = null;
        if (view == null)
        {
            tTextView = (TextView) mInflater.inflate(
                    R.layout.listitem_nearby_pop, null);
        }
        else
        {
            tTextView = (TextView) view;
        }
        tTextView.setText(mltData.get(index).getName());
        tTextView.setBackgroundColor(mIndex == index ? mColor
                : 0);
        return tTextView;
    }

}

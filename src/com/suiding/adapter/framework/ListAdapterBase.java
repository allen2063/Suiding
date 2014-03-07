package com.suiding.adapter.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.suiding.layoutbind.framework.ILayoutItem;


public abstract class ListAdapterBase extends BaseAdapter
{
    private LayoutInflater mInflater;
    private List<Object> mltData = null;

    protected abstract ILayoutItem getItemLayout();

    public ListAdapterBase(Context context, List<? extends Object> ltdata)
    {
        mltData = new ArrayList<Object>(ltdata);
        mInflater = LayoutInflater.from(context);
    }

    /**
     * 适配器新增 点击更多 数据追加接口
     * 
     * @param ltNews
     */
    public final void AddData(List<? extends Object> ltdata)
    {
        // TODO Auto-generated method stub
        mltData.addAll(ltdata);
        notifyDataSetChanged();
    }

    /**
     * 适配器新增 数据刷新 接口
     * 
     * @param ltNews
     */
    public final void setData(Collection<? extends Object> ltdata)
    {
        // TODO Auto-generated method stub
        mltData = new ArrayList<Object>(ltdata);
        notifyDataSetChanged();
    }

    /**
     * 适配器新增 单个数据刷新 接口
     * 
     * @param index
     * @param obj
     */
	public void setData(int index, Object obj) {
		// TODO Auto-generated method stub
		if(mltData.size() > index)
		{
			mltData.set(index, obj);
	        notifyDataSetChanged();
		}
	}
    /**
     * 适配器新增 数据删除 接口
     * 
     * @param ltNews
     */
	public void remove(int index) {
		// TODO Auto-generated method stub
		if(mltData.size() > index)
		{
			mltData.remove(index);
	        notifyDataSetChanged();
		}
	}
    @Override
    public final int getCount()
    {
        // TODO Auto-generated method stub
        return mltData.size();
    }

    @Override
    public final Object getItem(int arg0)
    {
        // TODO Auto-generated method stub
        return mltData.get(arg0);
    }

    @Override
    public final long getItemId(int arg0)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public final View getView(int position, View view, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        //列表视图获取必须检查 view 是否为空 不能每次都 inflate 否则手机内存负载不起
        ILayoutItem item = null;
        if (view == null)
        {
            item = getItemLayout();
            view = mInflater.inflate(item.getLayoutId(), null);
            item.Handle(view);
            item.Binding(mltData.get(position));
            view.setTag(item);
        }
        else
        {
            Object titem = view.getTag();
            item = (ILayoutItem) view.getTag();
            item.Binding(mltData.get(position));
            titem.toString();
        }
        return view;
    }


}

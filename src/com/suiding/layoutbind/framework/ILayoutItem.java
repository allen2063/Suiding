package com.suiding.layoutbind.framework;

import android.view.View;


public interface ILayoutItem
{
    /**
     * 从视图中取出控件
     * 
     * @param view
     */
    public abstract void Handle(View view);

    /**
     * 将数据绑定到控件显示
     * 
     * @param tData
     */
    public abstract void Binding(Object data);


    /**
     * 获取 Item 关联的 Layout ID
     * @return
     */
    public abstract int getLayoutId();

}

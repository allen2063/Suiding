package com.suiding.layoutbind.framework;

import android.view.View;


public interface ILayoutItem
{
    /**
     * ����ͼ��ȡ���ؼ�
     * 
     * @param view
     */
    public abstract void Handle(View view);

    /**
     * �����ݰ󶨵��ؼ���ʾ
     * 
     * @param tData
     */
    public abstract void Binding(Object data);


    /**
     * ��ȡ Item ������ Layout ID
     * @return
     */
    public abstract int getLayoutId();

}

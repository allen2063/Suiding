package com.suiding.broadcast;

import com.suiding.application.SuidingApp;
import com.suiding.util.NetworkUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class ConnectionChangeReceiver extends BroadcastReceiver 
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        int iNetworkState = NetworkUtil.getNetworkState(context);
        SuidingApp.getApp().setNetworkStatus(this,iNetworkState);
    }
}

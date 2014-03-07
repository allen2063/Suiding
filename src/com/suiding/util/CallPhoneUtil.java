package com.suiding.util;

import com.suiding.application.AppExceptionHandler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class CallPhoneUtil {

	public static void call(Context context, String phone) {
		// TODO Auto-generated method stub
		try {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_DIAL);
			intent.setData(Uri.parse("tel:" + phone));
			context.startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();//handled
			AppExceptionHandler.handler(e, "CallPhoneUtil，call 抛出异常 被过滤");
		}
	}

}

package com.suiding.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


public class LoadImageUtil {

	// 从一个URL获取图片
	public static BitmapDrawable getImageFromURL(Context context,String turl) throws Exception {
		BitmapDrawable bd = null;
		try {
			URL url = new URL(turl);
			// 创建连接
			HttpURLConnection hc = (HttpURLConnection) url.openConnection();
			// 获取数据
			bd = new BitmapDrawable(context.getResources(),hc.getInputStream());
			// 关闭连接
			hc.disconnect();
		} catch (Exception e) {
			throw e;
		}
		return bd;
	}
	
	/**
	 * @param urlStr
	 * @return
	 * @throws Exception 
	 */
	public static Drawable loadIBitmapDrawable(String urlStr) throws Exception{
		Drawable drawable = null;
		try {
			URL url = new URL(urlStr);
			InputStream inputStream = (InputStream) url.getContent();
			drawable = Drawable.createFromStream(inputStream, "src");
		} catch (Exception e) {
			throw e;
		}
		return drawable;
	}
}

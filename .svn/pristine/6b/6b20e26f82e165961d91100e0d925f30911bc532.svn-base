package com.suiding.service;

import com.suiding.model.User;
import com.suiding.util.HttpPost;

public class SMSService {
	public static boolean PhoneVerification(User user,String phoneNo,String vcode) throws Exception
	{
		String username = "XFTB702010";
		String scode="123456";
		String mobile=phoneNo;
		String tempid="OTW-201309023TEST";
		String content="@username@="+user.UserName+",@vcode@="+vcode+"";
		String result = HttpPost.sendmsg(username, scode, mobile, tempid, content);
		return "0".equals(result.substring(0,1))?true:false;
	}

	public static boolean PhoneVerification(String name,String phoneNo,String vcode) throws Exception
	{
		String username = "XFTB702010";
		String scode="123456";
		String mobile=phoneNo;
		String tempid="OTW-201309023TEST";
		String content="@username@="+name+",@vcode@="+vcode+"";
		String result = HttpPost.sendmsg(username, scode, mobile, tempid, content);
		return "0".equals(result.substring(0,1))?true:false;
	}
}

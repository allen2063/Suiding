package com.suiding.util;

import java.security.MessageDigest;
import java.util.Locale;

import com.suiding.application.AppExceptionHandler;

public class StringUtil
{
    public static String getMD5(byte[] byteArray){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  //handled
			AppExceptionHandler.handler(e, "StringUtil，getMD5 抛出异常 被过滤");
            return "";  
        }  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString().toLowerCase(Locale.ENGLISH);
  
    } 
}

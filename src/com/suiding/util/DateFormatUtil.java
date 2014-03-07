package com.suiding.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateFormatUtil
{
	public static SimpleDateFormat DAY = new SimpleDateFormat("M-d",Locale.ENGLISH);
	public static SimpleDateFormat DATE = new SimpleDateFormat("y-M-d",Locale.ENGLISH);
    public static SimpleDateFormat SIMPLE = new SimpleDateFormat("MM-dd HH:mm",Locale.ENGLISH);
    public static SimpleDateFormat FULL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
    
    public static String format(String format,Date date)
    {
        return new SimpleDateFormat(format,Locale.ENGLISH).format(date);
    }

	public static String formatDate(Date date) {
		// TODO Auto-generated method stub
		Calendar calender = Calendar.getInstance();
		int thisyear = calender.get(Calendar.YEAR);
		calender.setTime(date);
		int dateyear = calender.get(Calendar.YEAR);
		if(thisyear == dateyear){
			return DAY.format(date);
		}
		return DATE.format(date);
	}

	public static String formatTime(Date date) {
		// TODO Auto-generated method stub
		Calendar calender = Calendar.getInstance();
		int thisday = calender.get(Calendar.DAY_OF_MONTH);
		int thismonth = calender.get(Calendar.MONTH);
		int thisyear = calender.get(Calendar.YEAR);
		calender.setTime(date);
		int dateday = calender.get(Calendar.DAY_OF_MONTH);
		int datemonth = calender.get(Calendar.MONTH);
		int dateyear = calender.get(Calendar.YEAR);
		if(dateyear < thisyear){//������ǰ
			return DATE.format(date);
		}else if(datemonth < thismonth){//�������ǰ
			return SIMPLE.format(date);
		}else if(dateday < thisday - 2){//ǰ��֮ǰ
			return format("d�� HH:mm",date);
		}else if(dateday < thisday - 1){//����֮ǰ
			return format("ǰ�� HH:mm",date);
		}else if(dateday < thisday){//����֮ǰ
			return format("���� HH:mm",date);
		}else{
			return format("���� HH:mm",date);
		}
	}

	public static String formatDuration(Date begDate, Date endDate) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder(formatDate(begDate));
		builder.append(" - ");
		builder.append(formatDate(endDate));
		return builder.toString();
	}
}

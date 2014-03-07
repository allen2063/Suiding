package com.suiding.thread;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.location.Location;
import android.os.Handler;
import android.os.Message;

import com.suiding.application.AppExceptionHandler;
import com.suiding.application.SuidingApp;
import com.suiding.model.StoreBase;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.DistanceUtil;

public class SortStoreTask extends TaskBase implements Comparator<StoreBase> {

	public static final int NONE = -1;
	public static final int DISTANCE = 0;
	public static final int COMMENT = 1;
	public static final int PRICE = 2;
	public static final int DATE = 3;

	private Integer mType = 0;
	private Location mLocation = null;
	private List<? extends StoreBase> mltStore = null;

	public SortStoreTask(Handler handler, List<? extends StoreBase> ltstore,
			int type) {
		super(handler);
		// TODO Auto-generated constructor stub
		mType = type;
		mltStore = ltstore;
		mLocation = SuidingApp.getApp().getFixedLocation();
	}

	public SortStoreTask(int type) {
		super(SuidingApp.getLooper());
		// TODO Auto-generated constructor stub
		mType = type;
		mLocation = SuidingApp.getApp().getFixedLocation();
	}

	@Override
	protected void onWorking(Message msg) throws Exception {
		// TODO Auto-generated method stub
		if (mType != NONE) {
			try {
				Collections.sort(mltStore, this);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "店面排序任务，排序 出现异常");
			}
		}
	}

	@Override
	public int compare(StoreBase lhs, StoreBase rhs) {
		// TODO Auto-generated method stub
		boolean inverted = false;//是否倒序（倒序是指大的在前面）
		long lvalue = 0,rvalue = 0;
		switch (mType) {
		case DISTANCE:
			if (mLocation != null) {
				lvalue = DistanceUtil.getDistance(mLocation,lhs.Address);
				rvalue = DistanceUtil.getDistance(mLocation,rhs.Address);
			}
		case COMMENT:
			inverted = true;
			lvalue = (long)(lhs.Scores*100);
			rvalue = (long)(rhs.Scores*100);
		case DATE:
			Date ldate = lhs.LastDate;
			Date rdate = rhs.LastDate;
			if(ldate != null && rdate != null){
				inverted = true;
				lvalue = ldate.getTime();
				rvalue = rdate.getTime();
			}
		case PRICE:
			lvalue = (long)lhs.NowPrice;
			rvalue = (long)rhs.NowPrice;
		}
		if (lvalue == rvalue && lhs.IsBusying == rhs.IsBusying) {
			return rhs.IsBusying ? 1 : -1;
		}else if(lvalue != rvalue){
			if(inverted){//正序
				return rvalue > lvalue ? 1 : -1;
			}else{//倒序
				return rvalue > lvalue ? -1 : 1;
			}
		}
		return 0;
	}

}

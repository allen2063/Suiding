package com.suiding.constant;

public class CouponTypeEnum {

	/**
	 * 
	 */
	public static final int NONE= 0;

	/**
	 * 打折类型
	 */
	public static final int DISCOUNT= 1;
	/**
	 * 买满送
	 */
	public static final int BUYFULLGAVING= 2;
	/**
	 * 预订送
	 */
	public static final int ORDERGAVING= 3;
	
    static String[] typenames = new String[] { "无", "打折", "买满送", "预定送" };

    public static String GetCouponTypeNameBy(int CouponTypeEnum)
    {
        return typenames[CouponTypeEnum];
    }
}

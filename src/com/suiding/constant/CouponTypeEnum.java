package com.suiding.constant;

public class CouponTypeEnum {

	/**
	 * 
	 */
	public static final int NONE= 0;

	/**
	 * ��������
	 */
	public static final int DISCOUNT= 1;
	/**
	 * ������
	 */
	public static final int BUYFULLGAVING= 2;
	/**
	 * Ԥ����
	 */
	public static final int ORDERGAVING= 3;
	
    static String[] typenames = new String[] { "��", "����", "������", "Ԥ����" };

    public static String GetCouponTypeNameBy(int CouponTypeEnum)
    {
        return typenames[CouponTypeEnum];
    }
}

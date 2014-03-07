package com.suiding.model;

import java.util.UUID;

import com.suiding.constant.CouponTypeEnum;
import com.suiding.util.UUIDUtil;

public class CouponType {
	private UUID ID = UUIDUtil.Empty;
	public int Discount = 0;
	public double Fulls = 0;
	public String Gave = "";
	public String StrGaving = "";
	private int Type = 0;
	public String TypeName = "";
	
	public CouponType()
	{
		this.ID = UUID.randomUUID();
	}
	
	public CouponType(int Discount,double Fulls,String Gave,String StrGaving,int CouponTypeEnum)
	{
		this.Discount = Discount;
		this.Fulls = Fulls;
		this.Gave = Gave;
		this.StrGaving = StrGaving;
		this.ID = UUID.randomUUID();
		this.setType(CouponTypeEnum);
	}
	
	public UUID getID() {
		return ID;
	}
	public void setID(UUID iD) {
		ID = iD;
	}
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
		this.TypeName = CouponTypeEnum.GetCouponTypeNameBy(type);
	}
}

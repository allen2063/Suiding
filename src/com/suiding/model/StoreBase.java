package com.suiding.model;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.suiding.constant.StoreTypeEnum;
import com.suiding.util.UUIDUtil;

public class StoreBase {

	public static final int LIMIT_RANGE = 0;
	public static final int LIMIT_SPAN = 1;
	
	public UUID Adr_ID = UUIDUtil.Empty;
	public String HeadImg = "";
	public String Telephone = "";
	public Date BeginBss = new Date();
	public Date EndBss = new Date();
	public Boolean IsOpen = false;
	public String StopMSG = "";
	public String NetServer = "";
	public String Abstract = "";
	public Boolean IsRecommended = false;
	private UUID ID = UUIDUtil.Empty;
	public String Name = "";
	public int Type = -1;
	public double Price = 0;
	public double NowPrice = 0;
	public float Scores = 0;
	public Date LastDate = new Date();
	public boolean IsBusying = false;
	public UUID UserID = UUIDUtil.Empty;

	public Address Address;

	public boolean HasPark = false;
	public boolean HasWifi = false;
	public boolean HasToilet = false;

	public String Promotions = "";

	public int LimitDate = 0;
	public String Cellphone = "";

	public String Remark = "";
	public String SearchKeys = "";
	
	public StoreBase() {
		this.ID = UUID.randomUUID();
	}

	public StoreBase(String SbAbstract, String sbName, int Type) {

		if (SbAbstract != null)
			this.Abstract = SbAbstract;
		if (sbName != null)
			this.Name = sbName;
		this.Type = Type;

		this.ID = UUID.randomUUID();
	}

	public StoreBase(StoreBase sb) {
		this.ID = sb.ID;

		this.Adr_ID = sb.Adr_ID;
		this.Address = sb.Address;
		this.HeadImg = sb.HeadImg;
		this.Telephone = sb.Telephone;
		this.BeginBss = sb.BeginBss;
		this.EndBss = sb.EndBss;
		this.IsOpen = sb.IsOpen;
		this.StopMSG = sb.StopMSG;
		this.NetServer = sb.NetServer;
		this.Abstract = sb.Abstract;
		this.IsRecommended = sb.IsRecommended;
		this.Name = sb.Name;

		this.UserID = sb.UserID;
		this.Type = sb.Type;
		this.IsBusying = sb.IsBusying;

		this.Price = sb.Price;
		this.NowPrice = sb.NowPrice;
		this.Scores = sb.Scores;
		this.LastDate = sb.LastDate;

		this.HasPark = sb.HasPark;
		this.HasWifi = sb.HasWifi;
		this.HasToilet = sb.HasToilet;

		this.Cellphone = sb.Cellphone;
		this.LimitDate = sb.LimitDate;

		this.Remark = sb.Remark;
		this.SearchKeys = sb.SearchKeys;
		
		this.Promotions = sb.Promotions;
	}

	public int getLimitType() {
		// TODO Auto-generated method stub
		return this.LimitDate / 24;
	}
	
	public int getLimit(){
		int type = this.LimitDate / 24;
		if (type == LIMIT_RANGE) {
			return this.LimitDate % 24;
		} else if (type == LIMIT_SPAN) {
			int h = this.LimitDate % 24;
			if (h == 0)
				h = 24;
			return h;
		}
		return 18;
	}
	
	public Date getLimitDate() {
		int type = this.LimitDate / 24;
		Calendar calender = Calendar.getInstance();

		if (type == LIMIT_RANGE) {
			int h = this.LimitDate % 24;
			calender.set(Calendar.HOUR_OF_DAY, h);
			calender.set(Calendar.MINUTE, 0);
		} else if (type == LIMIT_SPAN) {
			int h = this.LimitDate % 24;
			if (h == 0)
				h = 24;
			calender.set(Calendar.HOUR_OF_DAY, h);
			calender.set(Calendar.MINUTE, 0);
		}else{
			int h = calender.get(Calendar.HOUR_OF_DAY);
			calender.set(Calendar.HOUR_OF_DAY, h+1);
			calender.set(Calendar.MINUTE, 0);
		}

		return calender.getTime();
	}

	public Address getAddress() {
		return Address;
	}

	public void setAddress(Address address) {
		Address = address;
		if (address != null) {
			this.Adr_ID = address.getID();
		}
	}

	public UUID getID() {
		return ID;
	}

	public void setID(UUID ID) {
		this.ID = ID;
	}

	/**
	 * 检查ID字段和name字段是否为空或者为“” 通过返回true 否则返回false 其他字段检查如果为空 修复为默认值
	 */
	public boolean checkModelIsPassed() {
		if (this.Adr_ID == null || Adr_ID == UUIDUtil.Empty || ID == null
				|| this.ID == UUIDUtil.Empty || this.Name == null
				|| this.Name.equals("")) {
			return false;
		}
		if (HeadImg == null)
			this.HeadImg = "";
		if (Telephone == null)
			this.Telephone = "";
		if (BeginBss == null)
			this.BeginBss = new Date();
		if (EndBss == null)
			this.EndBss = new Date();
		if (IsOpen == null)
			this.IsOpen = false;
		if (StopMSG == null)
			this.StopMSG = "";
		if (NetServer == null)
			this.NetServer = "";
		if (Abstract == null)
			this.Abstract = "";
		if (IsRecommended == null)
			this.IsRecommended = false;
		return true;
	}

	public String getIsBusyingString() {
		String rt = "座";
		if (this.Type == StoreTypeEnum.ENTERTAINMENT
				|| this.Type == StoreTypeEnum.HOTEL) {
			rt = "房";
		}
		if (!this.IsBusying) {
			rt = "有" + rt;
		} else {
			rt = "无" + rt;
		}
		return rt;
	}

}

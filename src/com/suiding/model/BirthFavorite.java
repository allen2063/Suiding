package com.suiding.model;

import java.util.Date;
import java.util.UUID;

import com.suiding.util.UUIDUtil;

/**
 * 
 * ÉúÈÕ¼ÇÂ¼
 * 
 * @author tt
 * 
 */
public class BirthFavorite {
	private UUID ID = UUIDUtil.Empty;
	public UUID User_ID = UUIDUtil.Empty;
	public String Name = "";
	public String PhoneNo = "";
	public String HeadImg = "";
	public String City = "";
	public String Area = "";
	public String Remark = "";
	public boolean Sex = false;
	public Date Birth = new Date();
	public boolean IsLunarCalendar = false;

	public BirthFavorite() {
		this.ID = UUID.randomUUID();
	}

	public BirthFavorite(BirthFavorite bf) {
		this.Name = bf.Name;
		this.PhoneNo = bf.PhoneNo;
		this.Sex = bf.Sex;
		this.HeadImg = bf.HeadImg;
		this.Birth = bf.Birth;
		this.City = bf.City;
		this.Area = bf.Area;
		this.Remark = bf.Remark;
		this.IsLunarCalendar = bf.IsLunarCalendar;
		this.User_ID = bf.User_ID;
		this.ID = UUID.randomUUID();
	}

	public UUID getID() {
		return ID;
	}

	public void setID(UUID iD) {
		ID = iD;
	}
}

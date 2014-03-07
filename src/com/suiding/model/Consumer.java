package com.suiding.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class Consumer extends User {

	public UUID User_ID = UUIDUtil.Empty;
	public String Name = "";
	public Date Birth = new Date();
	public Boolean Sex = false;
	public String Qianming = "";
	public String HeadImg = "";
	public int Score;
	public String Homepage = "";
	public int GoodTimes;
	public String EnName = "";
	public String Profession = "";
	public String Degree = "";
	public String School = "";
	private UUID Csm_ID = UUIDUtil.Empty;

	public Consumer() {
		this.Birth = new Date();
		this.Csm_ID = UUID.randomUUID();
		this.User_ID = super.getID();
	}

	public Consumer(User user) {
		super(user);
		if (user != null) {
			this.User_ID = user.getID();
		}
		this.Birth = new Date();
		this.Csm_ID = UUID.randomUUID();
	}

	public Consumer(Consumer user) {
		super(user);
		if (user != null) {
			this.User_ID = user.getID();
			this.Name = user.Name;
			this.Birth = user.Birth;
			this.Sex = user.Sex;
			this.Qianming = user.Qianming;
			this.HeadImg = user.HeadImg;
			this.Homepage = user.Homepage;
			this.EnName = user.EnName;
			this.Profession = user.Profession;
			this.Degree = user.Degree;
			this.School = user.School;
			this.Csm_ID = user.Csm_ID;
		}
	}

	public Consumer(User user, String CsmName, Boolean CsmSex) {
		super(user);
		if (user != null) {
			this.User_ID = user.getID();
		}
		this.Csm_ID = UUID.randomUUID();

		if (CsmName != null)
			this.Name = CsmName;
		if (CsmSex != null)
			this.Sex = CsmSex;
	}

	public void setUser_ID(UUID User_ID) {
		super.setID(User_ID);
		this.User_ID = User_ID;

	}

	public void setCsmBirth(Date CsmBirth) {
		Calendar calender = new GregorianCalendar(1900, 0, 0);
		if (CsmBirth.before(calender.getTime())) {
			this.Birth = new Date();
		} else {
			this.Birth = CsmBirth;
		}
	}


	/**
	 * 检查ID字段和name字段是否为空或者为“” 通过返回true 否则返回false 其他字段检查如果为空 修复为默认值
	 */
	public boolean checkModelIsPassed() {
		if (this.User_ID == null || this.Csm_ID == UUIDUtil.Empty
				|| User_ID == null || this.User_ID == UUIDUtil.Empty
				|| Name == null || Name.equals("")) {
			return false;
		}
		if (Birth == null)
			this.Birth = new Date();
		if (PhoneNo == null)
			this.PhoneNo = "";
		if (Qianming == null)
			this.Qianming = "";
		if (HeadImg == null)
			this.HeadImg = "";
		if (Homepage == null)
			this.Homepage = "";
		if (EnName == null)
			this.EnName = "";
		if (Profession == null)
			this.Profession = "";
		if (Degree == null)
			this.Degree = "";
		if (School == null)
			this.School = "";

		return true;
	}
}

package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class Business extends User {

	public UUID User_ID = UUIDUtil.Empty;
	public String Name = "";
	public String BssPhoneNo = "";
	public String Identification = "";
	public String CompanyNmae = "";
	public boolean BssIdentification = false;
	public String HeadImg = "";
	private UUID Bss_ID = UUIDUtil.Empty;

	public Business() {
		this.Bss_ID = UUID.randomUUID();
		this.User_ID = super.getID();
	}

	public Business(Business bs) {
		super(bs);
		this.Name = bs.Name;
		this.PhoneNo = bs.PhoneNo;
		this.Identification = bs.Identification;
		this.CompanyNmae = bs.CompanyNmae;
		this.Bss_ID = bs.Bss_ID;
		this.User_ID = bs.getID();
		this.HeadImg = bs.HeadImg;
	}

	public void setUser_ID(UUID User_ID) {
		super.setID(User_ID);
		this.User_ID = User_ID;
	}

	/**
	 * 检查ID字段和name字段是否为空或者为“” 通过返回true 否则返回false 其他字段检查如果为空 修复为默认值
	 */
	public boolean checkModelIsPassed() {
		if (this.Bss_ID == null || this.Role_ID == null || this.User_ID == null
				|| this.User_ID == UUIDUtil.Empty
				|| this.Bss_ID == UUIDUtil.Empty
				|| this.Role_ID == UUIDUtil.Empty || this.Name == null
				|| this.Name.equals("")) {
			this.Name = "";
			return false;
		}

		if (PhoneNo == null) {
			PhoneNo = "";
		}
		if (Identification == null) {
			PhoneNo = "";
		}
		if (CompanyNmae == null) {
			PhoneNo = "";
		}

		return true;
	}
}

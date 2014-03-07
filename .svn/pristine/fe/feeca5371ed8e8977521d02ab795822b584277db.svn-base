package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class HotelProductTemplate {

	public String Name = "";
	public double Price;
	private UUID ID = UUIDUtil.Empty;

	public HotelProductTemplate() {
		this.ID = UUID.randomUUID();
	}

	public HotelProductTemplate(String HptName, double HptPrice) {
		if (null != HptName)
			this.Name = HptName;
		this.Price = HptPrice;
		this.ID = UUID.randomUUID();
	}

	public String getHptName() {
		return Name;
	}

	public void setHptName(String HptName) {
		this.Name = HptName;
	}

	public double getHptPrice() {
		return Price;
	}

	public void setHptPrice(double HptPrice) {
		this.Price = HptPrice;
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
		if (Name == null || Name.equals("") || ID == null
				|| this.ID == UUIDUtil.Empty) {
			return false;
		}

		return true;
	}
}

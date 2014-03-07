package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class PdtItem {
	private UUID ID = UUIDUtil.Empty;
	public String HeadImg = "";
	public String Name = "";
	public double NowPrice = 0;
	public double Price = 0;
	public String Remark = "";
	public UUID Sb_ID = UUIDUtil.Empty;
	public UUID Type_ID = UUIDUtil.Empty;

	public PdtItemType PdtItemType;

	public PdtItem() {
	}

	public PdtItem(PdtItem item) {
		this.ID = item.ID;
		this.HeadImg = item.HeadImg;
		this.Name = item.Name;
		this.NowPrice = item.NowPrice;
		this.Price = item.Price;
		this.Remark = item.Remark;
		this.Sb_ID = item.Sb_ID;
		this.Type_ID = item.Type_ID;
	}
	
	public PdtItem(String Name, double Price, double NowPrice, UUID Sb_ID) {
		this.Name = Name;
		this.Price = Price;
		this.NowPrice = NowPrice;
		this.Sb_ID = Sb_ID;

		this.setPdtItemType(PdtItemType);
	}

	public UUID getID() {
		return ID;
	}

	public void setID(UUID iD) {
		ID = iD;
	}

	public String getHeadImg() {
		return HeadImg;
	}

	public void setHeadImg(String headImg) {
		HeadImg = headImg;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public double getNowPrice() {
		return NowPrice;
	}

	public void setNowPrice(double nowPrice) {
		NowPrice = nowPrice;
	}

	public double getPrice() {
		return Price;
	}

	public void setPrice(double price) {
		Price = price;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public UUID getSb_ID() {
		return Sb_ID;
	}

	public void setSb_ID(UUID sb_ID) {
		Sb_ID = sb_ID;
	}

	public UUID getType_ID() {
		return Type_ID;
	}

	public void setType_ID(UUID type_ID) {
		Type_ID = type_ID;
	}

	public PdtItemType getPdtItemType() {
		return PdtItemType;
	}

	public void setPdtItemType(PdtItemType pdtItemType) {
		PdtItemType = pdtItemType;
		if (pdtItemType != null) {
			this.Type_ID = pdtItemType.getID();
		}
	}
}

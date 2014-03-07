package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class Product {

	public UUID Sb_ID = UUIDUtil.Empty;
	public String Name = "";
	public String HeadImg = "";
	public String Abstrct = "";
	public int Discount;
	public double Price = 0;
	private UUID ID = UUIDUtil.Empty;
	public double NowPrice = 0;
	public int ProductCount = 0;
	public int BookedCount = 0;
	public int NoBookedCount = 0;

	public String Galleryful = "";
	public boolean HasToilet = false;

	public Product() {
		this.ID = UUID.randomUUID();
	}

	public Product(UUID Sb_ID, String PbName, String Abstrct, double SbPrice,
			double nowPrice) {
		if (Sb_ID != null)
			this.Sb_ID = Sb_ID;
		if (PbName != null)
			this.Name = PbName;
		if (Abstrct != null)
			this.Abstrct = Abstrct;
		this.Price = SbPrice;
		this.NowPrice = nowPrice;
		this.ID = UUID.randomUUID();
	}

	public Product(Product pb) {
		if (pb.getSb_ID() != null)
			this.Sb_ID = pb.getSb_ID();
		if (pb.getPbName() != null)
			this.Name = pb.getPbName();
		if (pb.getPbHeadImg() != null)
			this.HeadImg = pb.getPbHeadImg();
		if (pb.getPbAbstrct() != null)
			this.Abstrct = pb.getPbAbstrct();
		this.Discount = pb.getPbDiscount();
		this.Price = pb.getSbPrice();
		this.NowPrice = pb.NowPrice;
		this.ProductCount = pb.ProductCount;
		this.BookedCount = pb.BookedCount;
		this.NoBookedCount = pb.NoBookedCount;
		if (pb.getID() != null)
			this.ID = pb.getID();

		this.Galleryful = pb.Galleryful;
		this.HasToilet = pb.HasToilet;
	}

	public double getNowPrice() {
		return NowPrice;
	}

	public void setNowPrice(double nowPrice) {
		NowPrice = nowPrice;
	}

	public int getProductCount() {
		return ProductCount;
	}

	public void setProductCount(int productCount) {
		ProductCount = productCount;
	}

	public int getBookedCount() {
		return BookedCount;
	}

	public void setBookedCount(int bookedCount) {
		BookedCount = bookedCount;
	}

	public UUID getSb_ID() {
		return Sb_ID;
	}

	public void setSb_ID(UUID Sb_ID) {
		this.Sb_ID = Sb_ID;
	}

	public String getPbName() {
		return Name;
	}

	public void setPbName(String PbName) {
		this.Name = PbName;
	}

	public String getPbHeadImg() {
		return HeadImg;
	}

	public void setPbHeadImg(String PbHeadImg) {
		this.HeadImg = PbHeadImg;
	}

	public String getPbAbstrct() {
		return Abstrct;
	}

	public void setPbAbstrct(String PbAbstrct) {
		this.Abstrct = PbAbstrct;
	}

	public int getPbDiscount() {
		return Discount;
	}

	public void setPbDiscount(int PbDiscount) {
		this.Discount = PbDiscount;
	}

	public double getSbPrice() {
		return Price;
	}

	public void setSbPrice(double SbPrice) {
		this.Price = SbPrice;
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
		if (this.Sb_ID == null || this.Sb_ID == UUIDUtil.Empty || ID == null
				|| this.ID == UUIDUtil.Empty || Name == null || Name.equals("")) {
			return false;
		}
		if (HeadImg == null)
			this.HeadImg = "";
		if (Abstrct == null)
			this.Abstrct = "";

		return true;
	}
}

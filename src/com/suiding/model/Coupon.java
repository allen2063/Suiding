package com.suiding.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class Coupon {

	private UUID ID = UUIDUtil.Empty;
	public UUID For_ID = UUIDUtil.Empty;
	public UUID Pb_ID = UUIDUtil.Empty;
	public UUID Type_ID = UUIDUtil.Empty;
	public String Name = "";
	public String Content = "";
	public String Remark = "";
	public int Discount;
	public Date Date = new Date();
	public Date BegDate = new Date();
	public Date EndDate = new Date();

	public Product Product;
	public StoreBase StoreBase;
	public CouponType CouponType;

	public int OrderNumber = 0;

	public List<Photo> Photos = new ArrayList<Photo>();

	public Coupon() {
		this.ID = UUID.randomUUID();
	}

	public Coupon(Coupon coupon) {
		this.ID = coupon.ID;
		this.For_ID = coupon.For_ID;
		this.Pb_ID = coupon.Pb_ID;
		this.Type_ID = coupon.Type_ID;
		this.Name = coupon.Name;
		this.Content = coupon.Content;
		this.Remark = coupon.Remark;
		this.Discount = coupon.Discount;
		this.Date = coupon.Date;
		this.BegDate = coupon.BegDate;
		this.EndDate = coupon.EndDate;
		this.OrderNumber = coupon.OrderNumber;

		this.Photos = coupon.Photos;

		this.Product = coupon.Product;
		this.StoreBase = coupon.StoreBase;
		this.CouponType = coupon.CouponType;
	}

	public void setCouponType(CouponType couponType) {
		CouponType = couponType;
		if (couponType != null) {
			this.Type_ID = couponType.getID();
		}
	}

	public void setStoreBase(StoreBase storeBase) {
		StoreBase = storeBase;
		if (StoreBase != null) {
			this.For_ID = StoreBase.getID();
		}
	}

	public void setProduct(Product product) {
		Product = product;
		if (Product != null) {
			this.Pb_ID = Product.getID();
		}
	}

	public UUID getID() {
		return ID;
	}

	public void setID(UUID iD) {
		ID = iD;
	}
}

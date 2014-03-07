package com.suiding.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.suiding.entity.framework.StoreBaseEntity;
import com.suiding.model.Coupon;
import com.suiding.model.Order;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.util.UUIDUtil;

public class OrderEntity extends StoreBaseEntity {
	// Order的属性
	public UUID ID = UUIDUtil.Empty;
	public UUID User_ID = UUIDUtil.Empty;
	public UUID Pb_ID = UUIDUtil.Empty;
	public UUID Cp_ID = UUIDUtil.Empty;

	public int Count;
	public String Phone = "";
	public double NowTotalPrice = 0;
	public double OldTotalPrice = 0;
	public Date Date = new Date();
	public Date ArrivalTime = new Date();

	public int OrderStatus;
	public int PeopleNumber = 0;

	// Product的属性
	public String PB_Name = "";
	public String PB_HeadImg = "";
	public String PB_Abstrct = "";
	public double PB_OriginalPrice = 0;
	public double PB_Price = 0;
	public int PB_Discount;
	public int PB_ProductCount = 0;
	public int PB_BookedCount = 0;
	public int PB_NoBookedCount = 0;

	public String PB_Galleryful = "";
	public boolean PB_HasToilet = false;
	// Coupon的属性
	public String CP_Name = "";
	public int CP_Discount;
	public UUID CP_For_ID = UUIDUtil.Empty;
	public UUID CP_Type_ID = UUIDUtil.Empty;
	public String CP_Content = "";
	public String CP_Remark = "";
	public Date CP_Date = new Date();
	public Date CP_BegDate = new Date();
	public Date CP_EndDate = new Date();

	public OrderEntity(Order model) {
		// TODO Auto-generated constructor stub
		super(model.getStoreBase());

		this.ID = model.getID();
		this.Pb_ID = model.Pb_ID;
		this.Cp_ID = model.Cp_ID;
		this.User_ID = model.User_ID;

		this.Phone = model.Phone;
		this.Date = model.Date;
		this.Count = model.Count;
		this.OrderStatus = model.OrderStatus;
		this.PeopleNumber = model.PeopleNumber;
		this.ArrivalTime = model.ArrivalTime;
		this.OldTotalPrice = model.TotalPrice;
		this.NowTotalPrice = model.NowTotalPrice;

		Product product = model.getProductBase();
		if (product != null) {
			this.PB_Name = product.getPbName();
			this.PB_HeadImg = product.getPbHeadImg();
			this.PB_Abstrct = product.getPbAbstrct();
			this.PB_Price = product.NowPrice;
			this.PB_OriginalPrice = product.Price;

			this.PB_Discount = product.Discount;
			this.PB_ProductCount = product.ProductCount;
			this.PB_BookedCount = product.BookedCount;
			this.PB_NoBookedCount = product.NoBookedCount;

			this.PB_Galleryful = product.Galleryful;
			this.PB_HasToilet = product.HasToilet;
		}
		Coupon coupon = model.getCoupon();
		if (coupon != null) {
			this.CP_Name = coupon.Name;
			this.CP_Content = coupon.Content;
			this.CP_Discount = coupon.Discount;
			this.CP_For_ID = coupon.For_ID;
			this.CP_Type_ID = coupon.Type_ID;
			this.CP_Remark = coupon.Remark;
			this.CP_Date = coupon.Date;
			this.CP_BegDate = coupon.BegDate;
			this.CP_EndDate = coupon.EndDate;
		}
	}

	public OrderEntity() {
		// TODO Auto-generated constructor stub
	}

	public Order getModel() {
		// TODO Auto-generated method stub

		Order model = new Order();
		model.setID(ID);
		model.Pb_ID = Pb_ID;
		model.Sb_ID = super.SB_ID;
		model.Cp_ID = Cp_ID;
		model.User_ID = User_ID;
		model.Date = Date;
		model.Count = Count;

		model.TotalPrice = this.OldTotalPrice;
		model.NowTotalPrice = this.NowTotalPrice;
		model.Phone = this.Phone;
		model.ArrivalTime = this.ArrivalTime;
		model.PeopleNumber = this.PeopleNumber;
		model.OrderStatus = this.OrderStatus;

		Coupon coupon = new Coupon();
		coupon.Date = this.CP_Date;
		coupon.Name = this.CP_Name;
		coupon.Content = this.CP_Content;
		coupon.Discount = this.CP_Discount;
		coupon.For_ID = this.CP_For_ID;
		coupon.Type_ID = this.CP_Type_ID;
		coupon.Remark = this.CP_Remark;
		coupon.BegDate = this.CP_BegDate;
		coupon.EndDate = this.CP_EndDate;
		model.setCoupon(coupon);

		Product product = new Product();
		product.Name = this.PB_Name;
		product.HeadImg = this.PB_HeadImg;
		product.Abstrct = this.PB_Abstrct;
		product.NowPrice = this.PB_Price;
		product.Price = this.PB_OriginalPrice;

		product.Discount = this.PB_Discount;
		product.ProductCount = this.PB_ProductCount;
		product.BookedCount = this.PB_BookedCount;
		product.NoBookedCount = this.PB_NoBookedCount;

		product.Galleryful = this.PB_Galleryful;
		product.HasToilet = this.PB_HasToilet;
		model.setProductBase(product);

		StoreBase tStoreBase = super.getStoreBase();
		model.setStoreBase(tStoreBase);

		return model;
	}

	/**
	 * List<Model> 转换 List<Entity>
	 * 
	 * @param ltEntity
	 * @return
	 */
	public static List<OrderEntity> listFormModel(List<Order> ltModel) {
		// TODO Auto-generated method stub
		List<OrderEntity> ltEntity = new ArrayList<OrderEntity>();
		for (Order model : ltModel) {
			ltEntity.add(new OrderEntity(model));
		}
		return ltEntity;
	}

	/**
	 * List<Entity> 转换 List<Model>
	 * 
	 * @param ltEntity
	 * @return
	 */
	public static List<Order> listToModel(List<OrderEntity> ltEntity) {
		// TODO Auto-generated method stub
		List<Order> ltModel = new ArrayList<Order>();
		for (OrderEntity entity : ltEntity) {
			ltModel.add(entity.getModel());
		}
		return ltModel;
	}

}

package com.suiding.model;

import java.util.Date;
import java.util.UUID;

import com.suiding.util.DateGuid;
import com.suiding.util.UUIDUtil;

public class Order {

	private UUID ID = UUIDUtil.Empty;
	
	public UUID User_ID = UUIDUtil.Empty;
	public UUID Sb_ID = UUIDUtil.Empty;
	public UUID Pb_ID = UUIDUtil.Empty;
	public UUID Cp_ID = UUIDUtil.Empty;
	public Date Date = new Date();
	public int Count;
	public int OrderStatus;

	public double TotalPrice = 0;
	public double NowTotalPrice = 0;
    public String Phone = "";
    public Date ArrivalTime = new Date();
    public int PeopleNumber = 0;
    
    public int Type = 0;
    public boolean IsNew = true;
    public String OrderNumber = DateGuid.NewID().substring(0,18);
    
	User User;
	StoreBase StoreBase;
	Product ProductBase;
	Coupon Coupon;


	public Order() {
		this.ID = UUID.randomUUID();
	}

	public Order(StoreBase storeBase, Product productBase, User user,
			Date OdeDate, int OdeCount) {

		if (OdeDate != null)
			this.Date = OdeDate;
		this.Count = OdeCount;

		this.ID = UUID.randomUUID();

		this.User = user;
		this.StoreBase = storeBase;
		this.ProductBase = productBase;
		if (user != null) {
			this.User_ID = user.getID();
		}
		if (storeBase != null) {
			this.Sb_ID = storeBase.getID();
		}
		if (productBase != null) {
			this.Pb_ID = productBase.getID();
		}
	}
	
	public Order(Coupon coupon, User user,
			Date OdeDate, int OdeCount){

		if (OdeDate != null)
			this.Date = OdeDate;
		this.Count = OdeCount;

		this.ID = UUID.randomUUID();
		
		this.setCoupon(coupon);
	}

	public Order(Order order) {
		this.ID = order.ID;
		if (order.Date != null)
			this.Date = order.Date;
		this.Count = order.Count;
		this.ArrivalTime = order.ArrivalTime;
		this.NowTotalPrice = order.NowTotalPrice;
		this.Phone = order.Phone;
		this.TotalPrice = order.TotalPrice;
		
		this.setUser(order.User);
		this.setProductBase(order.ProductBase);
		this.setStoreBase(order.StoreBase);
		this.setCoupon(order.Coupon);
		
		this.PeopleNumber = order.PeopleNumber;
		this.OrderStatus = order.OrderStatus;

		this.Type = order.Type;
		this.IsNew = order.IsNew;
		this.OrderNumber = order.OrderNumber;
	}
	
	public Coupon getCoupon() {
		return Coupon;
	}

	public void setCoupon(Coupon coupon) {
		Coupon = coupon;
		if(coupon!=null)
		{
			this.setStoreBase(coupon.StoreBase);
			this.setProductBase(coupon.Product);
			this.Cp_ID = coupon.getID();
		}
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
		if (user != null) {
			this.User_ID = user.getID();
		}
	}

	public StoreBase getStoreBase() {
		if(Coupon != null && Coupon.StoreBase != null){
			return Coupon.StoreBase;
		}
		return StoreBase;
	}

	public void setStoreBase(StoreBase storeBase) {
		StoreBase = storeBase;
		if (storeBase != null) {
			this.Sb_ID = storeBase.getID();
		}
	}

	public Product getProductBase() {
		return ProductBase;
	}

	public void setProductBase(Product productBase) {
		ProductBase = productBase;
		if (productBase != null) {
			this.Pb_ID = productBase.getID();
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
		if (this.Sb_ID == null || this.Sb_ID == UUIDUtil.Empty
				|| User_ID == null || this.User_ID == UUIDUtil.Empty
				|| Pb_ID == null || this.Pb_ID == UUIDUtil.Empty || ID == null
				|| this.ID == UUIDUtil.Empty) {
			return false;
		}

		if (this.Date == null) {
			Date = new Date();
		}

		return true;
	}
}

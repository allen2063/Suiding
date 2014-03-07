package com.suiding.entity.framework;

import java.util.Date;
import java.util.UUID;

import com.suiding.model.StoreBase;
import com.suiding.util.UUIDUtil;

public abstract class StoreBaseEntity extends AddressEntity {
	//  Ù–‘
	public UUID SB_ID = UUIDUtil.Empty;
	public String SB_HeadImg = "";
	public String SB_Telephone = "";
	public Date SB_BeginBss = new Date();
	public Date SB_EndBss = new Date();
	public Boolean SB_IsOpen;
	public String SB_StopMSG = "";
	public String SB_NetServer = "";
	public String SB_Abstract = "";
	public Boolean SB_IsRecommended;
	public String SB_Name = "";

	public int SB_Type = -1;
	public double SB_Price = 0;
	public double SB_NowPrice = 0;
	public float SB_Scores = 0;
	public Date SB_LastDate = new Date();
	public boolean SB_IsBusying = false;

	public boolean SB_HasPark = false;
	public boolean SB_HasWifi = false;
	public boolean SB_HasToilet = false;

	public String SB_Promotions = "";

	public int SB_LimitDate = 0;
	public String SB_Cellphone = "";
	
	public StoreBaseEntity() {
		// TODO Auto-generated constructor stub
	}

	public StoreBaseEntity(StoreBase model) {
		// TODO Auto-generated constructor stub
		super(model != null ? model.Address : null);
		if (model != null) {
			this.SB_ID = model.getID();
			this.SB_Name = model.Name;
			this.SB_HeadImg = model.HeadImg;
			this.SB_Telephone = model.Telephone;
			this.SB_BeginBss = model.BeginBss;
			this.SB_EndBss = model.EndBss;
			this.SB_StopMSG = model.StopMSG;
			this.SB_NetServer = model.NetServer;
			this.SB_Abstract = model.Abstract;
			this.SB_IsOpen = model.IsOpen;
			this.SB_IsRecommended = model.IsRecommended;

			this.SB_Type = model.Type;
			this.SB_Price = model.Price;
			this.SB_NowPrice = model.NowPrice;
			this.SB_Scores = model.Scores;
			this.SB_LastDate = model.LastDate;
			this.SB_IsBusying = model.IsBusying;

            this.SB_HasPark = model.HasPark;
            this.SB_HasWifi = model.HasWifi;
            this.SB_HasToilet = model.HasToilet;

            this.SB_Promotions = model.Promotions;

            this.SB_LimitDate = model.LimitDate;
            this.SB_Cellphone = model.Cellphone;
		}
	}

	public StoreBase getStoreBase() {
		// TODO Auto-generated method stub
		StoreBase model = new StoreBase();
		model.setID(SB_ID);

		model.Name = this.SB_Name;
		model.HeadImg = this.SB_HeadImg;
		model.Telephone = this.SB_Telephone;
		model.BeginBss = this.SB_BeginBss;
		model.EndBss = this.SB_EndBss;
		model.StopMSG = this.SB_StopMSG;
		model.NetServer = this.SB_NetServer;
		model.Abstract = this.SB_Abstract;
		model.IsRecommended = this.SB_IsRecommended;

		model.Type = this.SB_Type;
		model.Price = this.SB_Price;
		model.NowPrice = this.SB_NowPrice;
		model.Scores = this.SB_Scores;
		model.LastDate = this.SB_LastDate;
		model.IsBusying = this.SB_IsBusying;

		model.HasPark = this.SB_HasPark;
		model.HasWifi = this.SB_HasWifi;
		model.HasToilet = this.SB_HasToilet;

		model.Promotions = this.SB_Promotions;

		model.LimitDate = this.SB_LimitDate;
		model.Cellphone = this.SB_Cellphone;
        
		model.setAddress(getAddress());

		return model;
	}
}

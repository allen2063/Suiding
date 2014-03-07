package com.suiding.model;

import java.util.UUID;

import com.suiding.service.AdressService;
import com.suiding.util.UUIDUtil;

public class Address {

	public int Contry=-1;
	public int Province=-1;
	public int City=-1;
	public int Xian=-1;
	public int Xiang=-1;
	public int Cun=-1;
	public String Custom="";
	public float PostionX;
	public float PostionY;
	private UUID ID = UUIDUtil.Empty;

	public Address(){
		this.ID = UUID.randomUUID();
	}
	
	public Address(int AdrContry,int AdrProvince,int AdrCity,int AdrXian,int AdrXiang,int AdrCun,String AdrCustom,float AdrPostionX,float AdrPostionY){
		this.Contry = AdrContry;
		this.Province = AdrProvince;
		this.City = AdrCity;
		this.Xian = AdrXian;
		this.Xiang = AdrXiang;
		this.Cun = AdrCun;
		if(this.Custom!=null) this.Custom = AdrCustom;
		this.PostionX = AdrPostionX;
		this.PostionY = AdrPostionY;
		this.ID = UUID.randomUUID();
	}

	public UUID getID() {
		return ID;
	}
	
	public void setID(UUID iD) {
		ID = iD;
	}
	/**
	 * 检查ID字段和name字段是否为空或者为“”
	 * 通过返回true 否则返回false
	 * 其他字段检查如果为空 修复为默认值
	 */
	public boolean checkModelIsPassed()
	{
		if(this.ID == UUIDUtil.Empty || this.ID==null)
		{
			return false;
		}
		if(this.Custom!=null) this.Custom = "";
		return true;
	}

	//1、花溪区某某街某某号 toGeneralString()
	public String toGeneralString()
	{
		return AdressService.getToGeneralString(this);
	}

	//2、贵州省平塘县鼠场乡新合村 toString()
	public String toString()
	{
		return AdressService.getToString(this);
	}
	
	//3、中国贵州省平塘县鼠场乡新合村+自定义描述 toLongString()
	public String toLongString()
	{
		return AdressService.getToLongString(this);
	}
	
	public static Address fromArea(Area area)
	{ 
		Address rt = new Address();
		
		if(area.Level == 1)
		{
			rt.Province = area.getID();
		}
		else if(area.Level == 2)
		{
			rt.City = area.getID();
		}
		else if(area.Level == 3)
		{
			rt.Xian = area.getID();
		}
		else if(area.Level == 4)
		{
			rt.Xiang = area.getID();
		}
		
		return rt;
	}
}

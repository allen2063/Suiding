package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class Hotel extends StoreBase {

	public UUID Sb_ID=UUIDUtil.Empty;
	public int Level;
	private UUID Htl_ID =UUIDUtil.Empty;

	public Hotel(){
		this.Htl_ID = UUID.randomUUID();
		this.Sb_ID = super.getID();
	}

	public Hotel(StoreBase storeBase,int HtlLevel){
		super(storeBase);
		if(storeBase!=null)
		{
			this.Sb_ID = storeBase.getID();
		}
		this.Level = HtlLevel;
		this.Htl_ID = UUID.randomUUID();
	}
	
	public Hotel(StoreBase storeBase){
		super(storeBase);
		if(storeBase!=null)
		{
			this.Sb_ID = storeBase.getID();
		}
		this.Htl_ID = UUID.randomUUID();
	}

	public UUID getSb_ID(){
		return Sb_ID;
	}
	public void setSb_ID(UUID Sb_ID){
		this.Sb_ID = Sb_ID;
	}
	public int getHtlLevel(){
		return Level;
	}
	public void setHtlLevel(int HtlLevel){
		this.Level = HtlLevel;
	}
	public UUID getHtl_ID(){
		return Htl_ID;
	}
	public void setHtl_ID(UUID ID){
		this.Htl_ID = ID;
	}

	/**
	 * 检查ID字段和name字段是否为空或者为“”
	 * 通过返回true 否则返回false
	 * 其他字段检查如果为空 修复为默认值
	 */
	public boolean checkModelIsPassed()
	{
		if(this.Sb_ID == null
				|| this.Sb_ID == UUIDUtil.Empty
				|| Htl_ID == null
				|| this.Htl_ID == UUIDUtil.Empty)
		{
			return false;
		}

		return true;
	}
}

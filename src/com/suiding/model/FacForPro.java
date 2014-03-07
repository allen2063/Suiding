package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class FacForPro {

	public UUID Fac_ID=UUIDUtil.Empty;
	public UUID Pb_ID=UUIDUtil.Empty;
	private UUID ID =UUIDUtil.Empty;

	Facility Facility;
	Product ProductBase;

	public FacForPro(){
		this.ID = UUID.randomUUID();
	}

	public FacForPro(UUID Fac_ID,UUID Pb_ID){
		if(Fac_ID!=null) this.Fac_ID = Fac_ID;
		if(Fac_ID!=null) this.Pb_ID = Pb_ID;
		this.ID = UUID.randomUUID();
	}
	
	public Facility getFacility() {
		return Facility;
	}
	public void setFacility(Facility facility) {
		Facility = facility;
		if(facility!=null)
		{
			this.Fac_ID = facility.getID();
		}
	}
	public Product getProductBase() {
		return ProductBase;
	}
	public void setProductBase(Product productBase) {
		ProductBase = productBase;
		if(productBase!=null)
		{
			this.Pb_ID = productBase.getID();
		}
			 
	}

	public UUID getFac_ID(){
		return Fac_ID;
	}
	public void setFac_ID(UUID Fac_ID){
		this.Fac_ID = Fac_ID;
	}
	public UUID getPb_ID(){
		return Pb_ID;
	}
	public void setPb_ID(UUID Pb_ID){
		this.Pb_ID = Pb_ID;
	}
	public UUID getID(){
		return ID;
	}
	public void setID(UUID ID){
		this.ID = ID;
	}
	/**
	 * 检查ID字段和name字段是否为空或者为“”
	 * 通过返回true 否则返回false
	 * 其他字段检查如果为空 修复为默认值
	 */	
	public boolean checkModelIsPassed()
	{
		if(this.Fac_ID == null
				|| this.Fac_ID == UUIDUtil.Empty
				|| Pb_ID == null
				|| this.Pb_ID == UUIDUtil.Empty
				|| ID == null
				|| this.ID == UUIDUtil.Empty)
		{
			return false;
		}

		return true;
	}
}

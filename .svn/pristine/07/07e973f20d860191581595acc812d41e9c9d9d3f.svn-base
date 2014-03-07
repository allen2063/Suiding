package com.suiding.model;

import java.util.Date;
import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class BusinessPromotion {

	private UUID ID =UUIDUtil.Empty;
    
    public int Type;
    public UUID For_ID=UUIDUtil.Empty;
    public UUID User_ID=UUIDUtil.Empty;
    public UUID Sb_ID=UUIDUtil.Empty;
    public boolean IsNew;
    public Date RegDate = new Date();
	
	public BusinessPromotion(){
		this.ID = UUID.randomUUID();
	}

	public BusinessPromotion(BusinessPromotion bp){
		this.ID = bp.ID;
		this.For_ID = bp.For_ID;
		this.User_ID = bp.User_ID;
		this.Sb_ID = bp.Sb_ID;
		this.IsNew = bp.IsNew;
		this.RegDate = bp.RegDate;
	}
	
	/**
	 * 检查ID字段和name字段是否为空或者为“”
	 * 通过返回true 否则返回false
	 * 其他字段检查如果为空 修复为默认值
	 */	
	public boolean checkModelIsPassed()
	{
		if(this.ID == null
				|| this.User_ID == UUIDUtil.Empty)
		{
			return false;
		}

		return true;
	}
}

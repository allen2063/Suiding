package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class Recommendation {

	public UUID For_ID=UUIDUtil.Empty;
	public int Level;
	public String Abstract="";
	private UUID ID =UUIDUtil.Empty;

	public Recommendation(){
		this.ID = UUID.randomUUID();
	}
	public Recommendation(UUID For_ID,int RcdLevel,String RcdAbstract){
		if(For_ID!=null) this.For_ID = For_ID;
		this.Level = RcdLevel;
		if(RcdAbstract!=null) this.Abstract = RcdAbstract;
		this.ID = UUID.randomUUID();
	}

	public UUID getFor_ID(){
		return For_ID;
	}
	public void setFor_ID(UUID For_ID){
		this.For_ID = For_ID;
	}
	public int getRcdLevel(){
		return Level;
	}
	public void setRcdLevel(int RcdLevel){
		this.Level = RcdLevel;
	}
	public String getRcdAbstract(){
		return Abstract;
	}
	public void setRcdAbstract(String RcdAbstract){
		this.Abstract = RcdAbstract;
	}
	public UUID getID(){
		return ID;
	}
	public void setID(UUID ID){
		this.ID = ID;
	}


	/**
	 * 检查ID字段和name字段是否为空或者为“” 通过返回true 否则返回false 其他字段检查如果为空 修复为默认值
	 */
	public boolean checkModelIsPassed()
	{
		if(this.For_ID == null
				|| this.For_ID == UUIDUtil.Empty
				|| ID == null
				|| this.ID == UUIDUtil.Empty)
		{
			return false;
		}

		if(Abstract==null)
		{
			Abstract="";
		}
		
		return true;
	}
}

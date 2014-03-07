package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class Facility {

	public String Name="";
	public String ICO="";
	public String Link="";
	public String Remark="";
	private UUID ID=UUIDUtil.Empty;

	public Facility(){
		this.ID = UUID.randomUUID();		
	}
	
	public Facility(String FacName,String FacICO,String FacLink,String FacRemark){
		if(FacName!=null) this.Name = FacName;
		if(FacICO!=null) this.ICO = FacICO;
		if(FacLink!=null) this.Link = FacLink;
		if(FacRemark!=null) this.Remark = FacRemark;
		this.ID = UUID.randomUUID();
	}
	
	public String getFacName(){
		return Name;
	}
	public void setFacName(String FacName){
		this.Name = FacName;
	}
	public String getFacICO(){
		return ICO;
	}
	public void setFacICO(String FacICO){
		this.ICO = FacICO;
	}
	public String getFacLink(){
		return Link;
	}
	public void setFacLink(String FacLink){
		this.Link = FacLink;
	}
	public String getFacRemark(){
		return Remark;
	}
	public void setFacRemark(String FacRemark){
		this.Remark = FacRemark;
	}
	public UUID getID(){
		return ID;
	}
	public void setID(UUID ID){
		this.ID = ID;
	}
	/**
	 * ���ID�ֶκ�name�ֶ��Ƿ�Ϊ�ջ���Ϊ����
	 * ͨ������true ���򷵻�false
	 * �����ֶμ�����Ϊ�� �޸�ΪĬ��ֵ
	 */
	public boolean checkModelIsPassed()
	{
		if(this.ID == null
				|| this.ID == UUIDUtil.Empty
				|| this.Name == null
				|| this.Name.equals("")
				)
		{
			return false;
		}

		if(ICO==null) this.ICO = "";
		if(Link==null) this.Link = "";
		if(Remark==null) this.Remark = "";
		
		return true;
	}
}

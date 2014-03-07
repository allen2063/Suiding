package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

import java.util.*;

/**
 * 
 * …Ã»¶
 * 
 * @author tt
 *
 */
public class Twitter {
	private UUID ID = UUIDUtil.Empty;
	public UUID For_ID = UUIDUtil.Empty;
	public String Name = "";
	public String Content = "";
	public String Remark = "";
	public Date Date = new Date();

	public StoreBase StoreBase;
    
	public Twitter()
	{
	}


    public Twitter(StoreBase sb,String Name,String Content,String Remark,Date Date)
    {
        this.Name = Name;
        this.Content = Content;
        this.Remark = Remark;
        this.Date = Date;
        this.ID = UUID.randomUUID();

        this.setStoreBase(sb);
    }
    
	public StoreBase getStoreBase() {
		return StoreBase;
	}

	public void setStoreBase(StoreBase storeBase) {
		StoreBase = storeBase;
		if(StoreBase!=null)
		{
			this.For_ID = StoreBase.getID();
		}
	}

	public UUID getID() {
		return ID;
	}

	public void setID(UUID iD) {
		ID = iD;
	}

	public UUID getFor_ID() {
		return For_ID;
	}

	public void setFor_ID(UUID for_ID) {
		For_ID = for_ID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
	}
	
	
}

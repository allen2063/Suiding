package com.suiding.model;

import java.util.Date;
import java.util.UUID;

import com.suiding.util.UUIDUtil;

/**
 * 
 * ÉúÈÕÑûÇë
 * 
 * @author tt
 * 
 */
public class BirthInvitation {
	private UUID ID = UUIDUtil.Empty;
	public UUID User_ID = UUIDUtil.Empty;
	public UUID From_ID = UUIDUtil.Empty;
	public String Name = "";
	public String Content = "";
	public String Remark = "";
	public Date Date = new Date();

	public StoreBase StoreBase;

	public BirthInvitation() {
		this.ID = UUID.randomUUID();
	}

	public BirthInvitation(BirthInvitation bi) {
		this.Name = bi.Name;
		this.Content = bi.Content;
		this.Remark = bi.Remark;
		this.Date = bi.Date;
		this.User_ID = bi.User_ID;
		this.ID = bi.ID;

		this.setStoreBase(bi.StoreBase);
	}

	public void setStoreBase(StoreBase storeBase) {
		StoreBase = storeBase;
		if (StoreBase != null) {
			this.From_ID = StoreBase.getID();
		}
	}

	public UUID getID() {
		return ID;
	}

	public void setID(UUID iD) {
		ID = iD;
	}

}

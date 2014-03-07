package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class PdtItemType {
	private UUID ID = UUIDUtil.Empty;
	public String Name = "";
	public UUID Sb_ID = UUIDUtil.Empty;
	public String Remark = "";

	public PdtItemType() {
		this.ID = UUID.randomUUID();
	}

	public PdtItemType(PdtItemType item) {
		// TODO Auto-generated constructor stub
		if (item != null) {
			ID = item.getID();
			Name = item.Name;
			Sb_ID = item.Sb_ID;
			Remark = item.Remark;
		}
	}

	public PdtItemType(UUID SBID, String name, String ramark) {
		// TODO Auto-generated constructor stub
		ID = UUID.randomUUID();
		if (name != null)
			Name = name;
		if (SBID != null)
			Sb_ID = SBID;
		if (ramark != null)
			Remark = ramark;
	}

	public UUID getID() {
		return ID;
	}

	public void setID(UUID iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public UUID getSb_ID() {
		return Sb_ID;
	}

	public void setSb_ID(UUID sb_ID) {
		Sb_ID = sb_ID;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

}

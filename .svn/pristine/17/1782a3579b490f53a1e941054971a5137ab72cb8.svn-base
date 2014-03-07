package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

/**
 * 
 * ПаІб
 * 
 * @author tt
 * 
 */
public class Photo {
	private UUID ID = UUIDUtil.Empty;
	public String Remark = "";
	public String Name = "";
	public String Url = "";
	public String Describe = "";
	public UUID For_ID = UUIDUtil.Empty;
	public UUID Pid = UUIDUtil.Empty;

	public Photo() {
		this.ID = UUID.randomUUID();
	}

	public Photo(String Name, String Url, String Describe, String Remark,
			UUID For_ID, UUID Pid) {
		this.Name = Name;
		this.Describe = Describe;
		this.Remark = Remark;
		this.Url = Url;

		this.For_ID = For_ID;
		this.Pid = Pid;
		this.ID = UUID.randomUUID();
	}

	public UUID getID() {
		return ID;
	}

	public void setID(UUID iD) {
		ID = iD;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getDescribe() {
		return Describe;
	}

	public void setDescribe(String describe) {
		Describe = describe;
	}

	public UUID getFor_ID() {
		return For_ID;
	}

	public void setFor_ID(UUID for_ID) {
		For_ID = for_ID;
	}

	public UUID getPid() {
		return Pid;
	}

	public void setPid(UUID pid) {
		Pid = pid;
	}
}

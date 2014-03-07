package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class RsrProductTemplate {

	public String Name = "";
	private UUID ID = UUIDUtil.Empty;

	public RsrProductTemplate() {
		this.ID = UUID.randomUUID();
	}

	public RsrProductTemplate(String RptName) {
		if (RptName != null)
			this.Name = RptName;
		this.ID = UUID.randomUUID();
	}

	public String getRptName() {
		return Name;
	}

	public void setRptName(String RptName) {
		this.Name = RptName;
	}

	public UUID getID() {
		return ID;
	}

	public void setID(UUID ID) {
		this.ID = ID;
	}

	/**
	 * 检查ID字段和name字段是否为空或者为“” 通过返回true 否则返回false 其他字段检查如果为空 修复为默认值
	 */
	public boolean checkModelIsPassed() {
		if (ID == null || this.ID == UUIDUtil.Empty || this.Name == null
				|| this.Name.equals("")) {
			return false;
		}

		return true;
	}
}

package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class RsrProduct {

	public UUID Rpt_ID = UUIDUtil.Empty;
	public UUID Pb_ID = UUIDUtil.Empty;
	private UUID ID = UUIDUtil.Empty;

	public RsrProduct() {
		this.ID = UUID.randomUUID();
	}

	public RsrProduct(UUID Rpt_ID, UUID Pb_ID) {
		if (Rpt_ID != null)
			this.Rpt_ID = Rpt_ID;
		if (Pb_ID != null)
			this.Pb_ID = Pb_ID;
		this.ID = UUID.randomUUID();
	}

	public RsrProduct(RsrProductTemplate rsrProductTemplate,
			Product productBase) {
		if (productBase != null) {
			this.Pb_ID = productBase.getID();
		}
		this.RsrProductTemplate = rsrProductTemplate;
		if (rsrProductTemplate != null) {
			this.Rpt_ID = rsrProductTemplate.getID();
		}
		this.ID = UUID.randomUUID();
	}

	RsrProductTemplate RsrProductTemplate;

	public RsrProductTemplate getRsrProductTemplate() {
		return RsrProductTemplate;
	}

	public void setRsrProductTemplate(RsrProductTemplate rsrProductTemplate) {
		RsrProductTemplate = rsrProductTemplate;
		if (rsrProductTemplate != null) {
			this.Rpt_ID = rsrProductTemplate.getID();
		}
	}

	public UUID getRpt_ID() {
		return Rpt_ID;
	}

	public void setRpt_ID(UUID Rpt_ID) {
		this.Rpt_ID = Rpt_ID;
	}

	public UUID getPb_ID() {
		return Pb_ID;
	}

	public void setPb_ID(UUID Pb_ID) {
		this.Pb_ID = Pb_ID;
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
		if (Rpt_ID == null || this.Rpt_ID == UUIDUtil.Empty
				|| ID == null || this.ID == UUIDUtil.Empty) {
			return false;
		}

		return true;
	}
}

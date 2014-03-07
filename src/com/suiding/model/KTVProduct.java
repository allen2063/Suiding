package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class KTVProduct extends Product {

	public UUID Kpt_ID = UUIDUtil.Empty;
	public UUID Pb_ID = UUIDUtil.Empty;
	private UUID Ktvp_ID = UUIDUtil.Empty;

	public KTVProduct() {
		this.Ktvp_ID = UUID.randomUUID();
		this.Pb_ID = super.getID();
	}

	public KTVProduct(UUID Kpt_ID, UUID Pb_ID) {
		if (Kpt_ID != null)
			this.Kpt_ID = Kpt_ID;
		if (Pb_ID != null)
			this.Pb_ID = Pb_ID;
		this.Ktvp_ID = UUID.randomUUID();
	}

	public KTVProduct(KTVProductTemplate rsrProductTemplate,
			Product productBase) {
		super(productBase);
		if (productBase != null) {
			this.Pb_ID = productBase.getID();
		}
		this.KTVProductTemplate = rsrProductTemplate;
		if (rsrProductTemplate != null) {
			this.Kpt_ID = rsrProductTemplate.getID();
		}
		this.Ktvp_ID = UUID.randomUUID();
	}

	KTVProductTemplate KTVProductTemplate;

	public KTVProductTemplate getKTVProductTemplate() {
		return KTVProductTemplate;
	}

	public void setKTVProductTemplate(KTVProductTemplate rsrProductTemplate) {
		KTVProductTemplate = rsrProductTemplate;
		if (rsrProductTemplate != null) {
			this.Kpt_ID = rsrProductTemplate.getID();
		}
	}

	public UUID getKpt_ID() {
		return Kpt_ID;
	}

	public void setKpt_ID(UUID Kpt_ID) {
		this.Kpt_ID = Kpt_ID;
	}

	public UUID getPb_ID() {
		return Pb_ID;
	}

	public void setPb_ID(UUID Pb_ID) {
		this.Pb_ID = Pb_ID;
	}

	public UUID getKtvp_ID() {
		return Ktvp_ID;
	}

	public void setKtvp_ID(UUID ID) {
		this.Ktvp_ID = ID;
	}

	/**
	 * 检查ID字段和name字段是否为空或者为“” 通过返回true 否则返回false 其他字段检查如果为空 修复为默认值
	 */
	public boolean checkModelIsPassed() {
		if (this.Sb_ID == null || this.Sb_ID == UUIDUtil.Empty
				|| Kpt_ID == null || this.Kpt_ID == UUIDUtil.Empty
				|| Ktvp_ID == null || this.Ktvp_ID == UUIDUtil.Empty) {
			return false;
		}

		return true;
	}
}
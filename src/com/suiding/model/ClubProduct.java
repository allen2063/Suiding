package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class ClubProduct extends Product {

	public UUID Cpt_ID = UUIDUtil.Empty;
	public UUID Pb_ID = UUIDUtil.Empty;
	private UUID Clup_ID = UUIDUtil.Empty;

	public ClubProduct() {
		this.Clup_ID = UUID.randomUUID();
		this.Pb_ID = super.getID();
	}

	public ClubProduct(UUID Cpt_ID, UUID Pb_ID) {
		if (Cpt_ID != null)
			this.Cpt_ID = Cpt_ID;
		if (Pb_ID != null)
			this.Pb_ID = Pb_ID;
		this.Clup_ID = UUID.randomUUID();
	}

	public ClubProduct(ClubProductTemplate ProductTemplate,
			Product productBase) {
		super(productBase);
		if (productBase != null) {
			this.Pb_ID = productBase.getID();
		}
		this.ClubProductTemplate = ProductTemplate;
		if (ProductTemplate != null) {
			this.Cpt_ID = ProductTemplate.getID();
		}
		this.Clup_ID = UUID.randomUUID();
	}

	ClubProductTemplate ClubProductTemplate;

	public ClubProductTemplate getClubProductTemplate() {
		return ClubProductTemplate;
	}

	public void setClubProductTemplate(ClubProductTemplate ProductTemplate) {
		ClubProductTemplate = ProductTemplate;
		if (ProductTemplate != null) {
			this.Cpt_ID = ProductTemplate.getID();
		}
	}

	public UUID getClup_ID() {
		return Clup_ID;
	}

	public void setClup_ID(UUID ID) {
		this.Clup_ID = ID;
	}

	/**
	 * 检查ID字段和name字段是否为空或者为“” 通过返回true 否则返回false 其他字段检查如果为空 修复为默认值
	 */
	public boolean checkModelIsPassed() {
		if (this.Sb_ID == null || this.Sb_ID == UUIDUtil.Empty
				|| Cpt_ID == null || this.Cpt_ID == UUIDUtil.Empty
				|| Clup_ID == null || this.Clup_ID == UUIDUtil.Empty) {
			return false;
		}

		return true;
	}
}
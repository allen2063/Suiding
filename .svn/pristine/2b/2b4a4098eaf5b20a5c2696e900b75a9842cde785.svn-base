package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class StallProduct extends Product {

	public UUID Pb_ID = UUIDUtil.Empty;
	public UUID Spt_ID = UUIDUtil.Empty;
	private UUID Stlp_ID = UUIDUtil.Empty;

	StallProductTemplate StallProductTemplate;

	public StallProduct() {
		this.Stlp_ID = UUID.randomUUID();
		this.Pb_ID = super.getID();
	}

	public StallProduct(Product productBase,
			StallProductTemplate hotelProductTemplate) {
		super(productBase);
		if (productBase != null) {
			this.Pb_ID = productBase.getID();
		}
		this.StallProductTemplate = hotelProductTemplate;
		if (hotelProductTemplate != null) {
			this.Spt_ID = hotelProductTemplate.getID();
		}

		this.Stlp_ID = UUID.randomUUID();
	}

	public StallProductTemplate getStallProductTemplate() {
		return StallProductTemplate;
	}

	public void setStallProductTemplate(
			StallProductTemplate hotelProductTemplate) {
		StallProductTemplate = hotelProductTemplate;
		if (hotelProductTemplate != null) {
			this.Spt_ID = hotelProductTemplate.getID();
		}
	}

	public UUID getPb_ID() {
		return Pb_ID;
	}

	public void setPb_ID(UUID Pb_ID) {
		this.Pb_ID = Pb_ID;
	}

	public UUID getSpt_ID() {
		return Spt_ID;
	}

	public void setSpt_ID(UUID Spt_ID) {
		this.Spt_ID = Spt_ID;
	}

	public UUID getStlp_ID() {
		return Stlp_ID;
	}

	public void setStlp_ID(UUID ID) {
		this.Stlp_ID = ID;
	}

	/**
	 * 检查ID字段和name字段是否为空或者为“” 通过返回true 否则返回false 其他字段检查如果为空 修复为默认值
	 */
	public boolean checkModelIsPassed() {
		if (this.Sb_ID == null || this.Sb_ID == UUIDUtil.Empty
				|| Spt_ID == null || this.Spt_ID == UUIDUtil.Empty
				|| Stlp_ID == null || this.Stlp_ID == UUIDUtil.Empty) {
			return false;
		}

		return true;
	}
}

package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class HotelProduct {

	public UUID Pb_ID = UUIDUtil.Empty;
	public UUID Hpt_ID = UUIDUtil.Empty;
	private UUID ID = UUIDUtil.Empty;

	HotelProductTemplate HotelProductTemplate;

	public HotelProduct() {
		this.ID = UUID.randomUUID();
	}

	public HotelProduct(Product productBase,
			HotelProductTemplate hotelProductTemplate) {
		if (productBase != null) {
			this.Pb_ID = productBase.getID();
		}
		this.HotelProductTemplate = hotelProductTemplate;
		if (hotelProductTemplate != null) {
			this.Hpt_ID = hotelProductTemplate.getID();
		}

		this.ID = UUID.randomUUID();
	}

	public HotelProductTemplate getHotelProductTemplate() {
		return HotelProductTemplate;
	}

	public void setHotelProductTemplate(
			HotelProductTemplate hotelProductTemplate) {
		HotelProductTemplate = hotelProductTemplate;
		if (hotelProductTemplate != null) {
			this.Hpt_ID = hotelProductTemplate.getID();
		}
	}

	public UUID getPb_ID() {
		return Pb_ID;
	}

	public void setPb_ID(UUID Pb_ID) {
		this.Pb_ID = Pb_ID;
	}

	public UUID getHpt_ID() {
		return Hpt_ID;
	}

	public void setHpt_ID(UUID Hpt_ID) {
		this.Hpt_ID = Hpt_ID;
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
		if (Hpt_ID == null || this.Hpt_ID == UUIDUtil.Empty
				|| ID == null || this.ID == UUIDUtil.Empty) {
			return false;
		}

		return true;
	}
}

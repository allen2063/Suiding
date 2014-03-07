package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

/*
 * 小摊
 */
public class Stall extends StoreBase {

	public UUID Sb_ID = UUIDUtil.Empty;
	private UUID Sta_ID = UUIDUtil.Empty;

	public Stall() {
		this.Sta_ID = UUID.randomUUID();
		this.Sb_ID = super.getID();
	}

	public Stall(StoreBase storeBase) {
		super(storeBase);
		if (storeBase != null) {
			this.Sb_ID = storeBase.getID();
		}
		this.Sta_ID = UUID.randomUUID();
	}

	public UUID getSb_ID() {
		return Sb_ID;
	}

	public void setSb_ID(UUID Sb_ID) {
		this.Sb_ID = Sb_ID;
	}

	public UUID getSta_ID() {
		return Sta_ID;
	}

	public void setSta_ID(UUID ID) {
		this.Sta_ID = ID;
	}

	/**
	 * 检查ID字段和name字段是否为空或者为“” 通过返回true 否则返回false 其他字段检查如果为空 修复为默认值
	 */
	public boolean checkModelIsPassed() {
		if (this.Sb_ID == null || this.Sb_ID == UUIDUtil.Empty || Sta_ID == null
				|| this.Sta_ID == UUIDUtil.Empty) {
			return false;
		}

		return true;
	}
}

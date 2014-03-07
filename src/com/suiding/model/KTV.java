package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

/*
 * 小摊
 */
public class KTV extends StoreBase {

	public UUID Sb_ID = UUIDUtil.Empty;
	private UUID Ktv_ID = UUIDUtil.Empty;

	public KTV() {
		this.Ktv_ID = UUID.randomUUID();
		this.Sb_ID = super.getID();
	}

	public KTV(StoreBase storeBase) {
		super(storeBase);
		if (storeBase != null) {
			this.Sb_ID = storeBase.getID();
		}
		this.Ktv_ID = UUID.randomUUID();
	}

	public UUID getSb_ID() {
		return Sb_ID;
	}

	public void setSb_ID(UUID Sb_ID) {
		this.Sb_ID = Sb_ID;
	}

	public UUID getKtv_ID() {
		return Ktv_ID;
	}

	public void setKtv_ID(UUID ID) {
		this.Ktv_ID = ID;
	}

	/**
	 * 检查ID字段和name字段是否为空或者为“” 通过返回true 否则返回false 其他字段检查如果为空 修复为默认值
	 */
	public boolean checkModelIsPassed() {
		if (this.Sb_ID == null || this.Sb_ID == UUIDUtil.Empty || Ktv_ID == null
				|| this.Ktv_ID == UUIDUtil.Empty) {
			return false;
		}

		return true;
	}
}

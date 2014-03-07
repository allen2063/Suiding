package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

/***
 * 
 * 锟斤拷锟捷★拷锟斤拷锟斤拷
 * 
 */
public class Restaurant extends StoreBase {

	public UUID Sb_ID = UUIDUtil.Empty;
	public int Level;
	private UUID Rsr_ID = UUIDUtil.Empty;

	public Restaurant() {
		this.Rsr_ID = UUID.randomUUID();
		this.Sb_ID = super.getID();
	}

	public Restaurant(StoreBase storeBase, int TsrLevel) {
		super(storeBase);
		if (storeBase != null) {
			this.Sb_ID = storeBase.getID();
		}
		this.Level = TsrLevel;
		this.Rsr_ID = UUID.randomUUID();
	}

	public Restaurant(StoreBase storeBase) {
		super(storeBase);
		if (storeBase != null) {
			this.Sb_ID = storeBase.getID();
		}
		this.Rsr_ID = UUID.randomUUID();
	}

	public UUID getSb_ID() {
		return Sb_ID;
	}

	public void setSb_ID(UUID Sb_ID) {
		this.Sb_ID = Sb_ID;
	}

	public int getTsrLevel() {
		return Level;
	}

	public void setTsrLevel(int TsrLevel) {
		this.Level = TsrLevel;
	}

	public UUID getRsr_ID() {
		return Rsr_ID;
	}

	public void setRsr_ID(UUID ID) {
		this.Rsr_ID = ID;
	}

	/**
	 * 检查ID字段和name字段是否为空或者为“” 通过返回true 否则返回false 其他字段检查如果为空 修复为默认值
	 */
	public boolean checkModelIsPassed() {
		if (this.Sb_ID == null || this.Sb_ID == UUIDUtil.Empty || Rsr_ID == null
				|| this.Rsr_ID == UUIDUtil.Empty) {
			return false;
		}

		return true;
	}
}

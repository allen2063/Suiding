package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

/*
 * 会所 夜总会
 */
public class Club extends StoreBase {

	public UUID Sb_ID = UUIDUtil.Empty;
	private UUID Clu_ID = UUIDUtil.Empty;

	public Club() {
		this.Clu_ID = UUID.randomUUID();
		this.Sb_ID = super.getID();
	}

	public Club(StoreBase storeBase) {
		super(storeBase);
		if (storeBase != null) {
			this.Sb_ID = storeBase.getID();
		}
		this.Clu_ID = UUID.randomUUID();
	}

	public UUID getClu_ID() {
		return Clu_ID;
	}

	public void setClu_ID(UUID ID) {
		this.Clu_ID = ID;
	}

	/**
	 * 检查ID字段和name字段是否为空或者为“” 通过返回true 否则返回false 其他字段检查如果为空 修复为默认值
	 */
	public boolean checkModelIsPassed() {
		if (this.Sb_ID == null || this.Sb_ID == UUIDUtil.Empty || Clu_ID == null
				|| this.Clu_ID == UUIDUtil.Empty) {
			return false;
		}

		return true;
	}
}
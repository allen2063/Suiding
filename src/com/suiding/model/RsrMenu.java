package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class RsrMenu extends PdtItem {

	public UUID M_ID = UUIDUtil.Empty;

	public RsrMenu() {
		this.M_ID = UUID.randomUUID();
	}

	public RsrMenu(PdtItem item) {
		super(item);
		M_ID = UUID.randomUUID();
	}

	public UUID getM_ID() {
		return M_ID;
	}

	public void setM_ID(UUID m_ID) {
		M_ID = m_ID;
	}
}

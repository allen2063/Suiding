package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class RsrOrderItem {
	private UUID ID = UUIDUtil.Empty;
	public UUID Ode_ID = UUIDUtil.Empty;
	public UUID Pi_ID = UUIDUtil.Empty;
	public int Count = 0;
	public RsrMenu RsrMenu;

	public RsrOrderItem() {
		this.ID = UUID.randomUUID();
	}

	public RsrOrderItem(RsrMenu rm, UUID orderid, int count) {
		this.Count = count;
		this.Ode_ID = orderid;
		this.ID = UUID.randomUUID();

		this.setRsrMenu(rm);
	}

	public UUID getID() {
		return ID;
	}

	public void setID(UUID iD) {
		ID = iD;
	}

	public UUID getOde_ID() {
		return Ode_ID;
	}

	public void setOde_ID(UUID ode_ID) {
		Ode_ID = ode_ID;
	}

	public UUID getPi_ID() {
		return Pi_ID;
	}

	public void setPi_ID(UUID pi_ID) {
		Pi_ID = pi_ID;
	}

	public int getCount() {
		return Count;
	}

	public void setCount(int count) {
		Count = count;
	}

	public RsrMenu getRsrMenu() {
		return RsrMenu;
	}

	public void setRsrMenu(RsrMenu rsrMenu) {
		RsrMenu = rsrMenu;
		if (rsrMenu != null) {
			this.Pi_ID = rsrMenu.getID();
		}
	}
}

package com.suiding.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class RsrOrder extends Order {
	private UUID RsoID = UUIDUtil.Empty;
	public List<RsrOrderItem> RsrMenus = new ArrayList<RsrOrderItem>();
	
	public RsrOrder(){
		RsoID = UUID.randomUUID();
	}
	
	public UUID getRsoID() {
		return RsoID;
	}
	public void setRsoID(UUID rsoID) {
		RsoID = rsoID;
	}
	public List<RsrOrderItem> getRsrMenus() {
		return RsrMenus;
	}
	public void setRsrMenus(List<RsrOrderItem> rsrMenus) {
		RsrMenus = rsrMenus;
	}
}

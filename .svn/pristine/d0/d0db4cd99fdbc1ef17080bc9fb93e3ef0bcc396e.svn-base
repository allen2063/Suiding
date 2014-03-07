package com.suiding.model;

import java.util.Date;
import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class HotelOrder extends Order {
	private UUID Hod_ID = UUIDUtil.Empty;
	public String Rooms = "";
	public Date InDate = new Date();
	public Date OutDate = new Date();
	
	public HotelOrder(){
		this.Hod_ID = UUID.randomUUID();
	}
	
	public HotelOrder(Order order, String Rooms,Date InDate,Date OutDate){
		super(order);
		this.Rooms = Rooms;
		this.InDate = InDate;
		this.OutDate = OutDate;
	}
	
	public UUID getHod_ID() {
		return Hod_ID;
	}
	public void setHod_ID(UUID hod_ID) {
		Hod_ID = hod_ID;
	}
	public String getRooms() {
		return Rooms;
	}
	public void setRooms(String rooms) {
		Rooms = rooms;
	}
	public Date getInDate() {
		return InDate;
	}
	public void setInDate(Date inDate) {
		InDate = inDate;
	}
	public Date getOutDate() {
		return OutDate;
	}
	public void setOutDate(Date outDate) {
		OutDate = outDate;
	}
}

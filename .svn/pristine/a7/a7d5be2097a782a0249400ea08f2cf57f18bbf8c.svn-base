package com.suiding.domain;

import java.util.List;

import com.suiding.model.Address;
import com.suiding.model.Area;
import com.suiding.model.Comment;
import com.suiding.model.Hotel;
import com.suiding.model.Page;
import com.suiding.model.Photo;
import com.suiding.model.Product;
import com.suiding.util.LeSouException;

public interface IHotelDomain extends IDomain<Hotel> {
    public List<Hotel> GetAllByArea(Area area,Page page) throws LeSouException;
	public List<Hotel> GetAllByAddress(Address address,Page page) throws LeSouException;
	public List<Hotel> GetAllOf(float x, float y, float radius) throws LeSouException;
	public List<Hotel> GetAllByAddress(Address address) throws LeSouException;
	public List<Product> GetListBySbID(Hotel hotel,Page page) throws LeSouException;
	public List<Comment> GetAllCommentBy(Hotel hotel,Page page) throws LeSouException;
	public List<Photo> getListBySbID(Hotel hotel,Page page) throws LeSouException;
}

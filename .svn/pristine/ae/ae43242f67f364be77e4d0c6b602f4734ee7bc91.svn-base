package com.suiding.domain;

import java.util.List;

import com.suiding.model.Address;
import com.suiding.model.Area;
import com.suiding.model.Comment;
import com.suiding.model.Page;
import com.suiding.model.Photo;
import com.suiding.model.Product;
import com.suiding.model.Restaurant;
import com.suiding.util.LeSouException;

public interface IRestaurantDomain extends IDomain<Restaurant> {
    public List<Restaurant> GetAllByArea(Area area,Page page) throws LeSouException;
	public List<Restaurant> GetAllByAddress(Address address,Page page) throws LeSouException;
	public List<Restaurant> GetAllOf(float x, float y, float radius) throws LeSouException;
	public List<Restaurant> GetAllByAddress(Address address) throws LeSouException;
	public List<Product> GetListBySbID(Restaurant restaurant,Page page) throws LeSouException;
	public List<Comment> GetAllCommentBy(Restaurant restaurant,Page page) throws LeSouException;
	public List<Photo> getListBySbID(Restaurant rsr,Page page) throws LeSouException;
}

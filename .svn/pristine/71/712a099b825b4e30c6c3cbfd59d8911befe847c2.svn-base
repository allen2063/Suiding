package com.suiding.domain;

import java.util.List;

import com.suiding.model.Address;
import com.suiding.model.Area;
import com.suiding.model.Comment;
import com.suiding.model.Page;
import com.suiding.model.Photo;
import com.suiding.model.Product;
import com.suiding.model.Stall;
import com.suiding.util.LeSouException;

public interface IStallDomain extends IDomain<Stall> {
    public List<Stall> GetAllByArea(Area area,Page page) throws LeSouException;
	public List<Stall> GetAllByAddress(Address address,Page page) throws LeSouException;
	public List<Stall> GetAllOf(float x, float y, float radius) throws LeSouException;
	public List<Stall> GetAllByAddress(Address address) throws LeSouException;
	public List<Product> GetListBySbID(Stall stall,Page page) throws LeSouException;
	public List<Comment> GetAllCommentBy(Stall stall,Page page) throws LeSouException;
	public List<Photo> getListBySbID(Stall stall,Page page) throws LeSouException;
}

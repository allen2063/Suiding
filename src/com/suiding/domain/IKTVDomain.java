package com.suiding.domain;

import java.util.List;

import com.suiding.model.Address;
import com.suiding.model.Area;
import com.suiding.model.Comment;
import com.suiding.model.KTV;
import com.suiding.model.Page;
import com.suiding.model.Photo;
import com.suiding.model.Product;
import com.suiding.util.LeSouException;

public interface IKTVDomain extends IDomain<KTV> {
    public List<KTV> GetAllByArea(Area area,Page page) throws LeSouException;
	public List<KTV> GetAllByAddress(Address address,Page page) throws LeSouException;
	public List<KTV> GetAllOf(float x, float y, float radius) throws LeSouException;
	public List<KTV> GetAllByAddress(Address address) throws LeSouException;
	public List<Product> GetListBySbID(KTV ktv,Page page) throws LeSouException;
	public List<Comment> GetAllCommentBy(KTV ktv,Page page) throws LeSouException;
	public List<Photo> getListBySbID(KTV ktv,Page page) throws LeSouException;
}

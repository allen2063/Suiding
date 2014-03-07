package com.suiding.domain;

import java.util.List;

import com.suiding.model.Address;
import com.suiding.model.Area;
import com.suiding.model.Club;
import com.suiding.model.Comment;
import com.suiding.model.Page;
import com.suiding.model.Photo;
import com.suiding.model.Product;
import com.suiding.util.LeSouException;

public interface IClubDomain extends IDomain<Club> {
    public List<Club> GetAllByArea(Area area,Page page) throws LeSouException;
	public List<Club> GetAllByAddress(Address address,Page page) throws LeSouException;
	public List<Club> GetAllOf(float x, float y, float radius) throws LeSouException;
	public List<Club> GetAllByAddress(Address address) throws LeSouException;
	public List<Comment> GetAllCommentBy(Club club,Page page) throws LeSouException;
	public List<Product> GetListBySbID(Club club,Page page) throws LeSouException;
	public List<Photo> getListBySbID(Club club,Page page) throws LeSouException;
}
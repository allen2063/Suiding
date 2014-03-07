package com.suiding.domain;

import java.util.List;

import com.suiding.model.ClubProduct;
import com.suiding.model.Comment;
import com.suiding.model.Page;
import com.suiding.model.Photo;
import com.suiding.util.LeSouException;

public interface IClubProductDomain extends IDomain<ClubProduct> {
	public List<Comment> GetAllCommentBy(ClubProduct product,Page page) throws LeSouException;
	List<Photo> getListBySbID(ClubProduct cp,Page page) throws LeSouException;
}
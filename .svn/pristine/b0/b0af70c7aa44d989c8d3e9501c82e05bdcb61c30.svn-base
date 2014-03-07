package com.suiding.domain;

import java.util.List;
import java.util.UUID;

import com.suiding.model.Page;
import com.suiding.model.Photo;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.model.User;
import com.suiding.util.LeSouException;

public interface IPhotoDomain extends IDomain<Photo> {
	List<Photo> getListBySbID(StoreBase sb,Page page)throws LeSouException;
	List<Photo> getListByPbID(Product pb,Page page)throws LeSouException;
	List<Photo> getListByUserID(User user,Page page)throws LeSouException;
	List<Photo> getListByForID(UUID forID,Page page)throws LeSouException;
}
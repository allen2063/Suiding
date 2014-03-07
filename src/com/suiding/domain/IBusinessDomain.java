package com.suiding.domain;

import java.util.List;
import java.util.UUID;

import com.suiding.model.Business;
import com.suiding.model.Page;
import com.suiding.model.Photo;
import com.suiding.util.LeSouException;

public interface IBusinessDomain extends IDomain<Business> {
	Business getByUserID(UUID userID) throws LeSouException;
	List<Photo> getListByUserID(Business business,Page page)throws LeSouException;
}

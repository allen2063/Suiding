package com.suiding.domain;

import java.util.List;
import java.util.UUID;

import com.suiding.model.Favorite;
import com.suiding.model.Page;
import com.suiding.util.LeSouException;

public interface IFavoriteDomain extends IDomain<Favorite> {
	List<Favorite> getListByUserID(UUID userid,Page page)throws LeSouException;
	boolean QuerySql(String sql) throws LeSouException;
}
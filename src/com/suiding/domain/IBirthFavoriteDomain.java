package com.suiding.domain;

import java.util.List;
import java.util.UUID;

import com.suiding.model.BirthFavorite;
import com.suiding.model.Page;
import com.suiding.util.LeSouException;

public interface IBirthFavoriteDomain extends IDomain<BirthFavorite> {
	List<BirthFavorite> getListByUserID(UUID id,Page page)  throws LeSouException;
	String uploadHeadImg(BirthFavorite bf, String filepath) throws Exception;
}
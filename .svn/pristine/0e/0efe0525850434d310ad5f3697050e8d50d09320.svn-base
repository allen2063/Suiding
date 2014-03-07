package com.suiding.domain;

import java.util.List;
import java.util.UUID;

import com.suiding.model.Address;
import com.suiding.model.Page;
import com.suiding.model.StoreBase;
import com.suiding.util.LeSouException;

public interface IStoreBaseDomain extends IDomain<StoreBase> {
	boolean getIsFavoriteByUser(UUID userid, UUID storeid) throws LeSouException;
	List<StoreBase> getFavorateStoreByUserID(UUID userid) throws LeSouException;
	List<StoreBase> getFavorateStoreByUserID(UUID userid,Page page) throws LeSouException;
	List<StoreBase> GetListBy(String key, Address address, int type, Page page) throws LeSouException;
	List<StoreBase> getNearby(int type, double x, double y, double rx,double ry)throws LeSouException;
	List<StoreBase> getNearby(int type, double x, double y, double rx,double ry, Page page)throws LeSouException;
}

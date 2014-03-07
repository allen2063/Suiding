package com.suiding.domain;

import java.util.List;
import java.util.UUID;

import com.suiding.model.Page;
import com.suiding.model.StoreBase;
import com.suiding.model.User;
import com.suiding.util.LeSouException;

public interface IUserDomain extends IDomain<User>{
	User Verify(String Name, String Password) throws LeSouException;
	boolean Exists(String Name) throws LeSouException;
	boolean FavoriteStoreBase(UUID userid,StoreBase sb) throws LeSouException;
	boolean UnFavoriteStoreBase(UUID userid,UUID sbid) throws LeSouException;
	boolean UnFavoriteStoreBase(UUID userid,List<UUID> sbids) throws LeSouException;	
	List<StoreBase> getFavorateStoreByUserID(UUID userid,Page page) throws LeSouException;
	List<StoreBase> getFavorateStoreByUserID(UUID userid) throws LeSouException;
	long getFavoriteStoreCount(UUID userid) throws LeSouException;
}

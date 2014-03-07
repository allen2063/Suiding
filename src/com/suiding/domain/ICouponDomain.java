package com.suiding.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.suiding.model.Address;
import com.suiding.model.Coupon;
import com.suiding.model.Page;
import com.suiding.util.LeSouException;

public interface ICouponDomain extends IDomain<Coupon> {

	long getCountBySbID(UUID id)throws LeSouException;
	
	List<Coupon> getPromotionByUserID(UUID userid) throws LeSouException;
	List<Coupon> getPromotionByUserID(UUID userid,Page page) throws LeSouException;
	List<Coupon> getPromotionByUserIDAfter(UUID userid,Date date) throws LeSouException;
	List<Coupon> getPromotionByUserIDAfter(UUID userid,Page page,Date date) throws LeSouException;
	List<Coupon> getPromotionByUserIDBefore(UUID userid,Date date) throws LeSouException;
	List<Coupon> getPromotionByUserIDBefore(UUID userid,Page page,Date date) throws LeSouException;
	List<Coupon> getPromotionByUserIDBettwen(UUID userid,Date begin,Date end) throws LeSouException;
	List<Coupon> getPromotionByUserIDBettwen(UUID userid,Page page,Date begin,Date end) throws LeSouException;

	
	List<Coupon> getListByPbID(UUID pbid,Page page) throws LeSouException;

	List<Coupon> getListBySbID(UUID sbid,Page page) throws LeSouException;
	List<Coupon> getListBySbIDAfter(UUID sbid,Date date) throws LeSouException;
	List<Coupon> getListBySbIDBefore(UUID sbid,Date date) throws LeSouException;
	List<Coupon> getListBySbIDBettwen(UUID sbid,Date begin,Date end) throws LeSouException;
	List<Coupon> getListByUserID(UUID userid,Page page) throws LeSouException;
	List<Coupon> getListByUserID(UUID userid) throws LeSouException;
	List<Coupon> getListByUserIDAfter(UUID userid,Date date) throws LeSouException;
	List<Coupon> getListByUserIDAfter(UUID userid,Page page,Date date) throws LeSouException;
	List<Coupon> getListByUserIDBefore(UUID userid,Date date) throws LeSouException;
	List<Coupon> getListByUserIDBefore(UUID userid,Page page,Date date) throws LeSouException;
	List<Coupon> getListByUserIDBettwen(UUID userid,Date begin,Date end) throws LeSouException;
	List<Coupon> getListByUserIDBettwen(UUID userid,Page page,Date begin,Date end) throws LeSouException;

	List<Coupon> getListByAddress(Address address,Page page) throws LeSouException;
	List<Coupon> getListByAddressAfter(Address address,Page page,Date date) throws LeSouException;
	List<Coupon> getListByAddressBefore(Address address,Page page,Date date) throws LeSouException;
	List<Coupon> getListByAddressBettwen(Address address,Page page,Date begin,Date end) throws LeSouException;

	List<Coupon> getListByAddress(Address address) throws LeSouException;
	List<Coupon> getListByAddressAfter(Address address,Date date) throws LeSouException;
	List<Coupon> getListByAddressBefore(Address address,Date date) throws LeSouException;
	List<Coupon> getListByAddressBettwen(Address address,Date begin,Date end) throws LeSouException;
	
	List<Coupon> getListByStoreType(int type,Page page) throws LeSouException;
	List<Coupon> getListByStoreTypeAfter(int type,Page page,Date date) throws LeSouException;
	List<Coupon> getListByStoreTypeBefore(int type,Page page,Date date) throws LeSouException;
	List<Coupon> getListByStoreTypeBettwen(int type,Page page,Date begin,Date end) throws LeSouException;
	
	List<Coupon> getListByStoreType(int type) throws LeSouException;
	List<Coupon> getListByStoreTypeAfter(int type,Date date) throws LeSouException;
	List<Coupon> getListByStoreTypeBefore(int type,Date date) throws LeSouException;
	List<Coupon> getListByStoreTypeBettwen(int type,Date begin,Date end) throws LeSouException;
	
	List<Coupon> getListByAddressStoreType(Address address,int type,Page page) throws LeSouException;
	List<Coupon> getListByAddressStoreTypeAfter(Address address,int type,Page page,Date date) throws LeSouException;
	List<Coupon> getListByAddressStoreTypeBefore(Address address,int type,Page page,Date date) throws LeSouException;
	List<Coupon> getListByAddressStoreTypeBettwen(Address address,int type,Page page,Date begin,Date end) throws LeSouException;
	
	List<Coupon> getListByAddressStoreType(Address address,int type) throws LeSouException;
	List<Coupon> getListByAddressStoreTypeAfter(Address address,int type,Date date) throws LeSouException;
	List<Coupon> getListByAddressStoreTypeBefore(Address address,int type,Date date) throws LeSouException;
	List<Coupon> getListByAddressStoreTypeBettwen(Address address,int type,Date begin,Date end) throws LeSouException;
	
	List<Coupon> GetListOf(float x, float y, float radius, int type) throws LeSouException;
	List<Coupon> GetListOfAfter(float x, float y, float radius, int type,Date date) throws LeSouException;
	List<Coupon> GetListOfBefore(float x, float y, float radius, int type,Date date) throws LeSouException;
	List<Coupon> GetListOfBettwen(float x, float y, float radius, int type,Date begin,Date end) throws LeSouException;


}
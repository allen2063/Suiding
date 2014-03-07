package com.suiding.domain.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.ksoap2.serialization.SoapObject;

import com.google.gson.Gson;
import com.suiding.domain.IStoreBaseDomain;
import com.suiding.model.Address;
import com.suiding.model.Page;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.LeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;
import com.suiding.util.LeSouException.LeSouExceptionType;
import com.suiding.util.UUIDUtil;

public class StoreBaseDomainImpl implements IStoreBaseDomain {

	ILeSouShopService<StoreBase> service = ServiceFactory.getStoreBaseDAO();
	
	public boolean Insert(StoreBase model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(StoreBase model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(StoreBase model) throws LeSouException{
		return service.Delete(model);
	}

	public StoreBase GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<StoreBase> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<StoreBase> models) throws LeSouException{
		return service.DeleteList(models);
	}

	public boolean DeleteListByID(List<UUID> ids) throws LeSouException{
		return service.DeleteListByID(ids);
	}

	public boolean DeleteByID(UUID id) throws LeSouException{
		return service.DeleteByID(id);
	}

	public long GetRecordCount(String strWhere) throws LeSouException{
		return service.GetRecordCount(strWhere);
	}

	public boolean Exists(UUID id) throws LeSouException{
		return service.Exists(id);
	}

	public List<StoreBase> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<StoreBase> GetListWhere(String strWhere, String order,
			String asc) throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

	public List<StoreBase> getFavorateStoreByUserID(UUID userid, Page page)
			throws LeSouException {
		try {
			Gson gson = LeSouShopService.getGson();
			SoapObject request = service.getRequest("GetFavorateStoreByUserIDPage");
			request.addProperty("userid", userid.toString());
			request.addProperty("page", gson.toJson(page));
			SoapObject response = (SoapObject) service.getResponse("GetFavorateStoreByUserIDPage",
					request);
			int length = response.getPropertyCount();
			List<StoreBase> entities = new ArrayList<StoreBase>();
			for (int i = 0; i < length; i++) {
				StoreBase entity = (StoreBase) gson.fromJson(
						response.getProperty(i).toString(), StoreBase.class);
				entities.add(entity);
			}
			return entities;
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new LeSouException(LeSouExceptionType.EXCEPTION_UNKNOW,
					ex.getMessage(), ":\n" + ex.getMessage(), ex);
		} catch (Error er) {
			er.printStackTrace();
			throw new LeSouException(LeSouExceptionType.EXCEPTION_SERVICE, " "
					+ er.getMessage(), " \n" + er.getMessage() + "\n :"
					+ er.getStackTrace(), er);
		}
	}

	public List<StoreBase> getFavorateStoreByUserID(UUID userid)
			throws LeSouException {
		try {
			Gson gson = LeSouShopService.getGson();
			SoapObject request = service.getRequest("GetFavorateStoreByUserID");
			request.addProperty("userid", userid.toString());
			SoapObject response = (SoapObject) service.getResponse("GetFavorateStoreByUserID",
					request);
			int length = response.getPropertyCount();
			List<StoreBase> entities = new ArrayList<StoreBase>();
			for (int i = 0; i < length; i++) {
				StoreBase entity = (StoreBase) gson.fromJson(
						response.getProperty(i).toString(), StoreBase.class);
				entities.add(entity);
			}
			return entities;
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new LeSouException(LeSouExceptionType.EXCEPTION_UNKNOW,
					ex.getMessage(), ":\n" + ex.getMessage(), ex);
		} catch (Error er) {
			er.printStackTrace();
			throw new LeSouException(LeSouExceptionType.EXCEPTION_SERVICE, " "
					+ er.getMessage(), " \n" + er.getMessage() + "\n :"
					+ er.getStackTrace(), er);
		}
	}

	@Override
	public List<StoreBase> GetListBy(String key, Address address, int type,
			Page page) throws LeSouException {
		Gson gson = LeSouShopService.getGson();
		String[][] properties = new String[][]{
				new String[]{"key",key},
				new String[]{"address",gson.toJson(address)},
				new String[]{"type",""+type},
				new String[]{"page",gson.toJson(page)}			
			};
		
		return service.getList("GetListBy", properties);
	}

	@Override
	public List<StoreBase> getNearby(int type, double x, double y, double rx,
			double ry) throws LeSouException {
		// TODO Auto-generated method stub
		SoapObject request = service.getRequest("GetNearby");
		request.addProperty("type", type);
		request.addProperty("x", String.valueOf(x));
		request.addProperty("y", String.valueOf(y));
		request.addProperty("rx", String.valueOf(rx));
		request.addProperty("ry", String.valueOf(ry));
		SoapObject response = (SoapObject) service.getResponse("GetNearby",
				request);
		Gson gson = LeSouShopService.getGson();
		List<StoreBase> models = new ArrayList<StoreBase>();
		int length = response.getPropertyCount();
		for (int i = 0; i < length; i++) {
			String str = response.getProperty(i).toString();
			StoreBase entity = gson.fromJson(str, StoreBase.class);
			models.add(entity);
		}
		return models;
	}

	@Override
	public List<StoreBase> getNearby(int type, double x, double y, double rx,
			double ry, Page page) throws LeSouException {
		// TODO Auto-generated method stub
		Gson gson = LeSouShopService.getGson();
		SoapObject request = service.getRequest("GetNearbyPage");
		request.addProperty("type", type);
		request.addProperty("x", String.valueOf(x));
		request.addProperty("y", String.valueOf(y));
		request.addProperty("rx", String.valueOf(rx));
		request.addProperty("ry", String.valueOf(ry));
		request.addProperty("page", gson.toJson(page));
		SoapObject response = (SoapObject) service.getResponse("GetNearbyPage",
				request);
		List<StoreBase> models = new ArrayList<StoreBase>();
		int length = response.getPropertyCount();
		for (int i = 0; i < length; i++) {
			String str = response.getProperty(i).toString();
			StoreBase entity = gson.fromJson(str, StoreBase.class);
			models.add(entity);
		}
		return models;
	}

	@Override
	public boolean getIsFavoriteByUser(UUID userid, UUID storeid)
			throws LeSouException {
		// TODO Auto-generated method stub
		UUID empid = UUIDUtil.Empty;
		if(!empid.equals(userid) && !empid.equals(storeid)){
			String where = " WHERE For_ID='"+storeid;
			where += "' AND User_ID='" + userid;
			where += "' ";
			return DomainFactory.getFavoriteDomain().GetRecordCount(where) > 0;
		}
		return false;
	}
}
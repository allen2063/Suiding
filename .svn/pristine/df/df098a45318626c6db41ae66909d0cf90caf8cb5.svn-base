package com.suiding.domain.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.ksoap2.serialization.SoapObject;

import com.google.gson.Gson;
import com.suiding.domain.IKTVDomain;
import com.suiding.model.Address;
import com.suiding.model.Area;
import com.suiding.model.Comment;
import com.suiding.model.KTV;
import com.suiding.model.Page;
import com.suiding.model.Photo;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.LeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class KTVDomainImpl implements IKTVDomain {

	ILeSouShopService<KTV> service = ServiceFactory.getKTVDAO();
	
	public boolean Insert(KTV model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(KTV model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(KTV model) throws LeSouException{
		return service.Delete(model);
	}

	public KTV GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<KTV> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<KTV> models) throws LeSouException{
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

	public List<KTV> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<KTV> GetAllOf(float x, float y, float radius)
			throws LeSouException {
		LeSouShopService<KTV> service = new LeSouShopService<KTV>(KTV.class);
		SoapObject request = service.getRequest("GetAllOf");
		request.addProperty("x", String.valueOf(x));
		request.addProperty("y", String.valueOf(y));
		request.addProperty("radius", String.valueOf(radius));
		SoapObject response = (SoapObject) service.getResponse("GetAllOf",
				request);
		Gson gson = LeSouShopService.getGson();
		List<KTV> models = new ArrayList<KTV>();
		int length = response.getPropertyCount();
		for (int i = 0; i < length; i++) {
			String str = response.getProperty(i).toString();
			KTV entity = gson.fromJson(str, KTV.class);
			models.add(entity);
		}
		return models;
	}

	public List<KTV> GetAllByAddress(Address address) throws LeSouException {
		Gson gson = LeSouShopService.getGson();

		String strAdr = gson.toJson(address);
		SoapObject request = service.getRequest("GetAllByAddress");
		request.addProperty("addresses", strAdr);
		SoapObject response = (SoapObject) service.getResponse(
				"GetAllByAddress", request);
		List<KTV> models = new ArrayList<KTV>();
		int length = response.getPropertyCount();
		for (int i = 0; i < length; i++) {
			KTV entity = gson.fromJson(response.getProperty(i).toString(), KTV.class);
			models.add(entity);
		}
		return models;
	}

	public List<Comment> GetAllCommentBy(KTV club, Page page) throws LeSouException {
		return DomainFactory.getCommentDomain().GetListByPage(" where For_ID = '"+club.Sb_ID+"'", page);
	}
	public List<Product> GetListBySbID(KTV club, Page page)
			throws LeSouException {
		return DomainFactory.getProductDomain().GetListByPage(" where Sb_ID = '"+club.Sb_ID+"' ", page);
	}

	public List<KTV> GetListWhere(String strWhere, String order, String asc)
			throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

	public List<Photo> getListBySbID(KTV ktv, Page page) throws LeSouException {
		return DomainFactory.getPhotoDomain().getListBySbID((StoreBase)ktv, page);
	}

	@Override
	public List<KTV> GetAllByAddress(Address address, Page page)
			throws LeSouException {
		Gson gson = LeSouShopService.getGson();

		String strAdr = gson.toJson(address);
		String strPage = gson.toJson(page);
		SoapObject request = service.getRequest("GetAllByAddressPage");
		request.addProperty("addresses", strAdr);
		request.addProperty("page", strPage);
		SoapObject response = (SoapObject) service.getResponse(
				"GetAllByAddressPage", request);
		List<KTV> models = new ArrayList<KTV>();
		int length = response.getPropertyCount();
		for (int i = 0; i < length; i++) {
			KTV entity = gson.fromJson(response.getProperty(i).toString(), KTV.class);
			models.add(entity);
		}
		return models;
	}

	@Override
	public List<KTV> GetAllByArea(Area area, Page page) throws LeSouException {
		// TODO Auto-generated method stub
		return GetAllByAddress(Address.fromArea(area),page);
	}
}
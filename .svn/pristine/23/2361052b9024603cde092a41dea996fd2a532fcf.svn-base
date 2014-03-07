package com.suiding.domain.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.ksoap2.serialization.SoapObject;

import com.google.gson.Gson;
import com.suiding.domain.IHotelDomain;
import com.suiding.model.Address;
import com.suiding.model.Area;
import com.suiding.model.Comment;
import com.suiding.model.Hotel;
import com.suiding.model.Page;
import com.suiding.model.Photo;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.LeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class HotelDomainImpl implements IHotelDomain {

	ILeSouShopService<Hotel> service = ServiceFactory.getHotelDAO();
	
	public boolean Insert(Hotel model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(Hotel model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(Hotel model) throws LeSouException{
		return service.Delete(model);
	}

	public Hotel GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<Hotel> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<Hotel> models) throws LeSouException{
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

	public List<Hotel> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<Hotel> GetAllOf(float x, float y, float radius)
			throws LeSouException {
		LeSouShopService<Hotel> service = new LeSouShopService<Hotel>(Hotel.class);
		SoapObject request = service.getRequest("GetAllOf");
		request.addProperty("x", String.valueOf(x));
		request.addProperty("y", String.valueOf(y));
		request.addProperty("radius", String.valueOf(radius));
		SoapObject response = (SoapObject) service.getResponse("GetAllOf",
				request);
		Gson gson = LeSouShopService.getGson();
		List<Hotel> models = new ArrayList<Hotel>();
		int length = response.getPropertyCount();
		for (int i = 0; i < length; i++) {
			String str = response.getProperty(i).toString();
			Hotel entity = gson.fromJson(str, Hotel.class);
			models.add(entity);
		}
		return models;
	}

	public List<Hotel> GetAllByAddress(Address address) throws LeSouException {
		Gson gson = LeSouShopService.getGson();

		String strAdr = gson.toJson(address);
		SoapObject request = service.getRequest("GetAllByAddress");
		request.addProperty("addresses", strAdr);
		SoapObject response = (SoapObject) service.getResponse(
				"GetAllByAddress", request);
		List<Hotel> models = new ArrayList<Hotel>();
		int length = response.getPropertyCount();
		for (int i = 0; i < length; i++) {
			Hotel entity = gson.fromJson(response.getProperty(i).toString(), Hotel.class);
			models.add(entity);
		}
		return models;
	}

	public List<Comment> GetAllCommentBy(Hotel club, Page page) throws LeSouException {
		return DomainFactory.getCommentDomain().GetListByPage(" where For_ID = '"+club.Sb_ID+"'", page);
	}
	public List<Product> GetListBySbID(Hotel club, Page page)
			throws LeSouException {
		return DomainFactory.getProductDomain().GetListByPage(" where Sb_ID = '"+club.Sb_ID+"' ", page);
	}

	public List<Hotel> GetListWhere(String strWhere, String order, String asc)
			throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

	@Override
	public List<Photo> getListBySbID(Hotel hotel, Page page)
			throws LeSouException {
		return DomainFactory.getPhotoDomain().getListBySbID((StoreBase)hotel, page);
	}

	@Override
	public List<Hotel> GetAllByAddress(Address address, Page page)
			throws LeSouException {
		Gson gson = LeSouShopService.getGson();

		String strAdr = gson.toJson(address);
		String strPage = gson.toJson(page);
		SoapObject request = service.getRequest("GetAllByAddressPage");
		request.addProperty("addresses", strAdr);
		request.addProperty("page", strPage);
		SoapObject response = (SoapObject) service.getResponse(
				"GetAllByAddressPage", request);
		List<Hotel> models = new ArrayList<Hotel>();
		int length = response.getPropertyCount();
		for (int i = 0; i < length; i++) {
			Hotel entity = gson.fromJson(response.getProperty(i).toString(), Hotel.class);
			models.add(entity);
		}
		return models;
	}


	public List<Hotel> GetAllByArea(Area area, Page page) throws LeSouException {
		// TODO Auto-generated method stub
		return GetAllByAddress(Address.fromArea(area),page);
	}
}
package com.suiding.domain.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.ksoap2.serialization.SoapObject;

import com.google.gson.Gson;
import com.suiding.domain.IClubDomain;
import com.suiding.model.Address;
import com.suiding.model.Area;
import com.suiding.model.Club;
import com.suiding.model.Comment;
import com.suiding.model.Page;
import com.suiding.model.Photo;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.LeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class ClubDomainImpl implements IClubDomain {

	ILeSouShopService<Club> service = ServiceFactory.getClubDAO();
	
	public boolean Insert(Club model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(Club model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(Club model) throws LeSouException{
		return service.Delete(model);
	}

	public Club GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<Club> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<Club> models) throws LeSouException{
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

	public List<Club> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<Club> GetAllOf(float x, float y, float radius)
			throws LeSouException {
		LeSouShopService<Club> service = new LeSouShopService<Club>(Club.class);
		SoapObject request = service.getRequest("GetAllOf");
		request.addProperty("x", String.valueOf(x));
		request.addProperty("y", String.valueOf(y));
		request.addProperty("radius", String.valueOf(radius));
		SoapObject response = (SoapObject) service.getResponse("GetAllOf",
				request);
		Gson gson = LeSouShopService.getGson();
		List<Club> models = new ArrayList<Club>();
		int length = response.getPropertyCount();
		for (int i = 0; i < length; i++) {
			String str = response.getProperty(i).toString();
			Club entity = gson.fromJson(str, Club.class);
			models.add(entity);
		}
		return models;
	}

	public List<Club> GetAllByAddress(Address address) throws LeSouException {
		Gson gson = LeSouShopService.getGson();

		String strAdr = gson.toJson(address);
		SoapObject request = service.getRequest("GetAllByAddress");
		request.addProperty("addresses", strAdr);
		SoapObject response = (SoapObject) service.getResponse(
				"GetAllByAddress", request);
		List<Club> models = new ArrayList<Club>();
		int length = response.getPropertyCount();
		for (int i = 0; i < length; i++) {
			Club entity = gson.fromJson(response.getProperty(i).toString(), Club.class);
			models.add(entity);
		}
		return models;
	}

	public List<Comment> GetAllCommentBy(Club club, Page page) throws LeSouException {
		return DomainFactory.getCommentDomain().GetListByPage(" where For_ID = '"+club.Sb_ID+"'", page);
	}
	public List<Product> GetListBySbID(Club club, Page page)
			throws LeSouException {
		return DomainFactory.getProductDomain().GetListByPage(" where Sb_ID = '"+club.Sb_ID+"' ", page);
	}

	public List<Club> GetListWhere(String strWhere, String order, String asc)
			throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

	public List<Photo> getListBySbID(Club club, Page page)
			throws LeSouException {
		return DomainFactory.getPhotoDomain().getListBySbID((StoreBase)club, page);
	}

	public List<Club> GetAllByAddress(Address address, Page page)
			throws LeSouException {
		Gson gson = LeSouShopService.getGson();

		String strAdr = gson.toJson(address);
		String strPage = gson.toJson(page);
		SoapObject request = service.getRequest("GetAllByAddressPage");
		request.addProperty("addresses", strAdr);
		request.addProperty("page", strPage);
		SoapObject response = (SoapObject) service.getResponse(
				"GetAllByAddressPage", request);
		List<Club> models = new ArrayList<Club>();
		int length = response.getPropertyCount();
		for (int i = 0; i < length; i++) {
			Club entity = gson.fromJson(response.getProperty(i).toString(), Club.class);
			models.add(entity);
		}
		return models;
	}

	@Override
	public List<Club> GetAllByArea(Area area, Page page) throws LeSouException {
		// TODO Auto-generated method stub
		return GetAllByAddress(Address.fromArea(area),page);
	}
}
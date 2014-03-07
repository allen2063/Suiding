package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import org.ksoap2.serialization.SoapObject;

import com.suiding.domain.IFavoriteDomain;
import com.suiding.model.Favorite;
import com.suiding.model.Page;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class FavoriteDomainImpl implements IFavoriteDomain {

	ILeSouShopService<Favorite> service = ServiceFactory.getFavoriteDAO();
	
	public boolean Insert(Favorite model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(Favorite model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(Favorite model) throws LeSouException{
		return service.Delete(model);
	}

	public Favorite GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<Favorite> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<Favorite> models) throws LeSouException{
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

	public List<Favorite> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<Favorite> GetListWhere(String strWhere, String order, String asc)
			throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

	public List<Favorite> getListByUserID(UUID userid, Page page)
			throws LeSouException {
		return service.GetListByPage(" where User_ID = '"+userid+"' ", page);
	}

	public boolean QuerySql(String sql) throws LeSouException {
		SoapObject request = service.getRequest("QuerySql");
		request.addProperty("sql", sql);
		Object response = service.getResponse("QuerySql",
				request);		
		return Boolean.parseBoolean(response+"");
	}

}

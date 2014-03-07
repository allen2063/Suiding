package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import com.suiding.domain.IPhotoDomain;
import com.suiding.model.Page;
import com.suiding.model.Photo;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.model.User;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class PhotoDomainImpl implements IPhotoDomain {

	ILeSouShopService<Photo> service = ServiceFactory.getPhotoDAO();
	
	public boolean Insert(Photo model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(Photo model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(Photo model) throws LeSouException{
		return service.Delete(model);
	}

	public Photo GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<Photo> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<Photo> models) throws LeSouException{
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

	public List<Photo> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<Photo> GetListWhere(String strWhere, String order, String asc)
			throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

	@Override
	public List<Photo> getListBySbID(StoreBase sb, Page page)
			throws LeSouException {
		return this.GetListByPage(" where For_ID = '"+sb.getID()+"'", page);
	}

	@Override
	public List<Photo> getListByPbID(Product pb, Page page)
			throws LeSouException {
		return this.GetListByPage(" where For_ID = '"+pb.getID()+"'", page);
	}

	@Override
	public List<Photo> getListByUserID(User user, Page page)
			throws LeSouException {
		return this.GetListByPage(" where For_ID = '"+user.getID()+"'", page);
	}

	@Override
	public List<Photo> getListByForID(UUID forID, Page page)
			throws LeSouException {
		return this.GetListByPage(" where For_ID = '"+forID+"'", page);
	}

}
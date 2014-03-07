package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import com.suiding.domain.IClubProductDomain;
import com.suiding.model.ClubProduct;
import com.suiding.model.Comment;
import com.suiding.model.Page;
import com.suiding.model.Photo;
import com.suiding.service.DomainFactory;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class ClubProductDomainImpl implements IClubProductDomain {

	ILeSouShopService<ClubProduct> service = ServiceFactory.getClubProductDAO();
	
	public boolean Insert(ClubProduct model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(ClubProduct model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(ClubProduct model) throws LeSouException{
		return service.Delete(model);
	}

	public ClubProduct GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<ClubProduct> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<ClubProduct> models) throws LeSouException{
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

	public List<ClubProduct> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<Comment> GetAllCommentBy(ClubProduct product, Page page) throws LeSouException {
		return DomainFactory.getCommentDomain().GetListByPage(" where For_ID = '"+product.Pb_ID+"'", page);
	}

	public List<ClubProduct> GetListWhere(String strWhere, String order,
			String asc) throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

	@Override
	public List<Photo> getListBySbID(ClubProduct cp, Page page)
			throws LeSouException {
		return DomainFactory.getPhotoDomain().getListByForID(cp.getID(), page);
	}
}
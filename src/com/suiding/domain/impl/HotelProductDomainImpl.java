package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import com.suiding.domain.IHotelProductDomain;
import com.suiding.model.Comment;
import com.suiding.model.HotelProduct;
import com.suiding.model.Page;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class HotelProductDomainImpl implements IHotelProductDomain {

	ILeSouShopService<HotelProduct> service = ServiceFactory.getHotelProductDAO();
	
	public boolean Insert(HotelProduct model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(HotelProduct model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(HotelProduct model) throws LeSouException{
		return service.Delete(model);
	}

	public HotelProduct GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<HotelProduct> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<HotelProduct> models) throws LeSouException{
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

	public List<HotelProduct> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<Comment> GetAllCommentBy(HotelProduct product, Page page) throws LeSouException {
		return DomainFactory.getCommentDomain().GetListByPage(" where For_ID = '"+product.Pb_ID+"'", page);
	}

	public List<HotelProduct> GetListByPbID(Product product, Page page)
			throws LeSouException {
		return this.GetListByPage(" where Pb_ID = '"+product.getID()+"'", page);
	}

	public List<HotelProduct> GetListBySbID(StoreBase sb, Page page)
			throws LeSouException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<HotelProduct> GetListWhere(String strWhere, String order,
			String asc) throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}
}
package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import com.suiding.domain.IStallProductDomain;
import com.suiding.model.Comment;
import com.suiding.model.Page;
import com.suiding.model.Product;
import com.suiding.model.StallProduct;
import com.suiding.service.DomainFactory;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class StallProductDomainImpl implements IStallProductDomain {

	ILeSouShopService<StallProduct> service = ServiceFactory.getStallProductDAO();
	
	public boolean Insert(StallProduct model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(StallProduct model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(StallProduct model) throws LeSouException{
		return service.Delete(model);
	}

	public StallProduct GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<StallProduct> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<StallProduct> models) throws LeSouException{
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

	public List<StallProduct> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<Comment> GetAllCommentBy(StallProduct product, Page page) throws LeSouException {
		return DomainFactory.getCommentDomain().GetListByPage(" where For_ID = '"+product.Pb_ID+"'", page);
	}
	public List<Product> GetListBySbID(StallProduct product, Page page)
			throws LeSouException {
		return DomainFactory.getProductDomain().GetListByPage(" where Sb_ID = '"+product.Sb_ID+"' ", page);
	}

	public List<StallProduct> GetListWhere(String strWhere, String order,
			String asc) throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}
}
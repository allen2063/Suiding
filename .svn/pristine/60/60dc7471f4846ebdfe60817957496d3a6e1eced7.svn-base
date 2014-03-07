package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import com.suiding.domain.IRsrProductDomain;
import com.suiding.model.Comment;
import com.suiding.model.Page;
import com.suiding.model.Product;
import com.suiding.model.RsrProduct;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class RsrProductDomainImpl implements IRsrProductDomain {

	ILeSouShopService<RsrProduct> service = ServiceFactory.getRsrProductDAO();
	
	public boolean Insert(RsrProduct model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(RsrProduct model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(RsrProduct model) throws LeSouException{
		return service.Delete(model);
	}

	public RsrProduct GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<RsrProduct> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<RsrProduct> models) throws LeSouException{
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

	public List<RsrProduct> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<Comment> GetAllCommentBy(RsrProduct product, Page page) throws LeSouException {
		return DomainFactory.getCommentDomain().GetListByPage(" where For_ID = '"+product.Pb_ID+"'", page);
	}

	public List<RsrProduct> GetListByPbID(Product product, Page page)
			throws LeSouException {
		return this.GetListByPage(" where Pb_ID = '"+product.getID()+"'", page);
	}

	public List<RsrProduct> GetListBySbID(StoreBase sb, Page page)
			throws LeSouException {
		return null;
	}

	public List<RsrProduct> GetListWhere(String strWhere, String order,
			String asc) throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}
}
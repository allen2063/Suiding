package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import com.suiding.domain.IKTVProductDomain;
import com.suiding.model.Comment;
import com.suiding.model.KTVProduct;
import com.suiding.model.Page;
import com.suiding.model.Product;
import com.suiding.service.DomainFactory;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class KTVProductDomainImpl implements IKTVProductDomain {

	ILeSouShopService<KTVProduct> service = ServiceFactory.getKTVProductDAO();
	
	public boolean Insert(KTVProduct model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(KTVProduct model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(KTVProduct model) throws LeSouException{
		return service.Delete(model);
	}

	public KTVProduct GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<KTVProduct> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<KTVProduct> models) throws LeSouException{
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

	public List<KTVProduct> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<Comment> GetAllCommentBy(KTVProduct product, Page page) throws LeSouException {
		return DomainFactory.getCommentDomain().GetListByPage(" where For_ID = '"+product.Pb_ID+"'", page);
	}
	public List<Product> GetListBySbID(KTVProduct product, Page page)
			throws LeSouException {
		return DomainFactory.getProductDomain().GetListByPage(" where Sb_ID = '"+product.Sb_ID+"' ", page);
	}

	public List<KTVProduct> GetListWhere(String strWhere, String order,
			String asc) throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}
}
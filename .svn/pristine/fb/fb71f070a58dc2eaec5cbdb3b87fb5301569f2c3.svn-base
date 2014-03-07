package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import com.suiding.domain.IProductDomain;
import com.suiding.model.Page;
import com.suiding.model.Photo;
import com.suiding.model.Product;
import com.suiding.service.DomainFactory;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class ProductDomainImpl implements IProductDomain {

	ILeSouShopService<Product> service = ServiceFactory.getProductDAO();
	
	public boolean Insert(Product model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(Product model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(Product model) throws LeSouException{
		return service.Delete(model);
	}

	public Product GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<Product> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<Product> models) throws LeSouException{
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

	public List<Product> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<Product> GetListBySbID(UUID sbID, Page page)
			throws LeSouException {
		// TODO Auto-generated method stub
		return this.GetListByPage(" where Sb_ID = '"+sbID+"' ", page);
	}

	public List<Product> GetListWhere(String strWhere, String order, String asc)
			throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

	@Override
	public List<Photo> getListByPbID(Product pb, Page page)
			throws LeSouException {
		return DomainFactory.getPhotoDomain().getListByPbID(pb, page);
	}

}

package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import com.suiding.domain.IProductAccessoriesDomain;
import com.suiding.model.Page;
import com.suiding.model.ProductAccessories;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class ProductAccessoriesDomainImpl implements IProductAccessoriesDomain {

	ILeSouShopService<ProductAccessories> service = ServiceFactory.getProductAccessoriesDAO();
	
	public boolean Insert(ProductAccessories model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(ProductAccessories model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(ProductAccessories model) throws LeSouException{
		return service.Delete(model);
	}

	public ProductAccessories GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<ProductAccessories> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<ProductAccessories> models) throws LeSouException{
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

	public List<ProductAccessories> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<ProductAccessories> GetListWhere(String strWhere, String order,
			String asc) throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

}
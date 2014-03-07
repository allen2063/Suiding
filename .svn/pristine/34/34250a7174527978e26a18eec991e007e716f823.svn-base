package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import com.suiding.domain.IPageDomain;
import com.suiding.model.Page;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class PageDomainImpl implements IPageDomain {

	ILeSouShopService<Page> service = ServiceFactory.getPageDAO();
	
	public boolean Insert(Page model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(Page model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(Page model) throws LeSouException{
		return service.Delete(model);
	}

	public Page GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<Page> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<Page> models) throws LeSouException{
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

	public List<Page> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

}
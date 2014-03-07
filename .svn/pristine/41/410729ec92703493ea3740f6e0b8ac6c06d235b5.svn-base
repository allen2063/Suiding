package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import com.suiding.domain.IMenuDomain;
import com.suiding.model.Page;
import com.suiding.model.RsrMenu;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class MenuDomainImpl implements IMenuDomain {

	ILeSouShopService<RsrMenu> service = ServiceFactory.getMenuDAO();
	
	public boolean Insert(RsrMenu model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(RsrMenu model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(RsrMenu model) throws LeSouException{
		return service.Delete(model);
	}

	public RsrMenu GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<RsrMenu> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<RsrMenu> models) throws LeSouException{
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

	public List<RsrMenu> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<RsrMenu> GetListWhere(String strWhere, String order,
			String asc) throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

}
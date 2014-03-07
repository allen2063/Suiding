package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import com.suiding.domain.IRsrOrderDomain;
import com.suiding.model.Page;
import com.suiding.model.RsrOrder;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class RsrOrderDomainImpl implements IRsrOrderDomain {

	ILeSouShopService<RsrOrder> service = ServiceFactory.getRsrOrderDAO();
	
	public boolean Insert(RsrOrder model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(RsrOrder model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(RsrOrder model) throws LeSouException{
		return service.Delete(model);
	}

	public RsrOrder GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<RsrOrder> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<RsrOrder> models) throws LeSouException{
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

	public List<RsrOrder> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<RsrOrder> GetListWhere(String strWhere, String order,
			String asc) throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

}
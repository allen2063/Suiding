package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import com.suiding.domain.IFacilityDomain;
import com.suiding.model.Facility;
import com.suiding.model.Page;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class FacilityDomainImpl implements IFacilityDomain {

	ILeSouShopService<Facility> service = ServiceFactory.getFacilityDAO();
	
	public boolean Insert(Facility model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(Facility model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(Facility model) throws LeSouException{
		return service.Delete(model);
	}

	public Facility GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<Facility> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<Facility> models) throws LeSouException{
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

	public List<Facility> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<Facility> GetListWhere(String strWhere, String order, String asc)
			throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

}
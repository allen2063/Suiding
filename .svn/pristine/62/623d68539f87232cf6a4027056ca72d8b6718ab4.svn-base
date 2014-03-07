package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import com.suiding.domain.IFacForProDomain;
import com.suiding.model.FacForPro;
import com.suiding.model.Page;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class FacForProDomainImpl implements IFacForProDomain {

	ILeSouShopService<FacForPro> service = ServiceFactory.getFacForProDAO();
	
	public boolean Insert(FacForPro model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(FacForPro model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(FacForPro model) throws LeSouException{
		return service.Delete(model);
	}

	public FacForPro GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<FacForPro> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<FacForPro> models) throws LeSouException{
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

	public List<FacForPro> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<FacForPro> GetListWhere(String strWhere, String order,
			String asc) throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

}
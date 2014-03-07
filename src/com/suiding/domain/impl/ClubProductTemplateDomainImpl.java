package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import com.suiding.domain.IClubProductTemplateDomain;
import com.suiding.model.ClubProductTemplate;
import com.suiding.model.Page;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class ClubProductTemplateDomainImpl implements IClubProductTemplateDomain {

	ILeSouShopService<ClubProductTemplate> service = ServiceFactory.getClubProductTemplateDAO();
	
	public boolean Insert(ClubProductTemplate model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(ClubProductTemplate model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(ClubProductTemplate model) throws LeSouException{
		return service.Delete(model);
	}

	public ClubProductTemplate GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<ClubProductTemplate> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<ClubProductTemplate> models) throws LeSouException{
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

	public List<ClubProductTemplate> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<ClubProductTemplate> GetListWhere(String strWhere,
			String order, String asc) throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

}
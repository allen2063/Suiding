package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import com.suiding.domain.IBirthInvitationDomain;
import com.suiding.model.BirthInvitation;
import com.suiding.model.Page;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class BirthInvitationDomainImpl implements IBirthInvitationDomain {

	ILeSouShopService<BirthInvitation> service = ServiceFactory.getBirthInvitationDAO();
	
	public boolean Insert(BirthInvitation model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(BirthInvitation model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(BirthInvitation model) throws LeSouException{
		return service.Delete(model);
	}

	public BirthInvitation GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<BirthInvitation> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<BirthInvitation> models) throws LeSouException{
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

	public List<BirthInvitation> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<BirthInvitation> getListByUserID(UUID userid, Page page) throws LeSouException {
		return this.GetListByPage(" where User_ID = '"+userid+"'", page);
	}

	public List<BirthInvitation> getListBySbID(UUID sbid, Page page)
			throws LeSouException {
		return this.GetListByPage(" where From_ID = '"+sbid+"'", page);
	}

	public List<BirthInvitation> GetListWhere(String strWhere, String order,
			String asc) throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

}
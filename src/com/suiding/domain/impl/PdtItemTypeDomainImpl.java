package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import com.suiding.domain.IPdtItemTypeDomain;
import com.suiding.model.Page;
import com.suiding.model.PdtItemType;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class PdtItemTypeDomainImpl implements IPdtItemTypeDomain {

	ILeSouShopService<PdtItemType> service = ServiceFactory.getPdtItemTypeDAO();
	
	public boolean Insert(PdtItemType model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(PdtItemType model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(PdtItemType model) throws LeSouException{
		return service.Delete(model);
	}

	public PdtItemType GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<PdtItemType> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<PdtItemType> models) throws LeSouException{
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

	public List<PdtItemType> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<PdtItemType> GetListWhere(String strWhere, String order,
			String asc) throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

	@Override
	public List<PdtItemType> GetListBySbID(UUID id, Page page) throws LeSouException {
		// TODO Auto-generated method stub
		return service.GetListByPage(" where Sb_ID='"+id+"'", page);
	}
}
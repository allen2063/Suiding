package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import com.suiding.domain.IPdtItemDomain;
import com.suiding.model.Page;
import com.suiding.model.PdtItem;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class PdtItemDomainImpl implements IPdtItemDomain {

	ILeSouShopService<PdtItem> service = ServiceFactory.getPdtItemDAO();
	
	public boolean Insert(PdtItem model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(PdtItem model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(PdtItem model) throws LeSouException{
		return service.Delete(model);
	}

	public PdtItem GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<PdtItem> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<PdtItem> models) throws LeSouException{
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

	public List<PdtItem> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<PdtItem> GetListWhere(String strWhere, String order,
			String asc) throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

	public List<PdtItem> GetListBySbID(UUID sbid, Page page) throws LeSouException
    {
        String where = " where Pi_ID in (select Pi_ID from PdtItem where Sb_ID = '"+sbid+"')";
        return service.GetListByPage(where, page);
    }

    public List<PdtItem> GetListByPbID(UUID pbid, Page page) throws LeSouException
    {
    	String where = " where Pi_ID in (select Pi_ID from ProductAccessories where Pb_ID = '"+pbid+"')";
        return service.GetListByPage(where, page);
    }
}
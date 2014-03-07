package com.suiding.domain.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.ksoap2.serialization.SoapObject;

import com.suiding.domain.IAddressDomain;
import com.suiding.model.Address;
import com.suiding.model.Page;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class AddressDomainImpl implements IAddressDomain {

	ILeSouShopService<Address> service = ServiceFactory.getAddressesDAO();

	public boolean Insert(Address model) throws LeSouException {
		return service.Insert(model);
	}

	public boolean Update(Address model) throws LeSouException {
		return service.Update(model);
	}

	public boolean Delete(Address model) throws LeSouException {
		return service.Delete(model);
	}

	public Address GetByID(UUID id) throws LeSouException {
		return service.GetByID(id);
	}

	public List<Address> GetAll() throws LeSouException {
		return service.GetAll();
	}

	public boolean DeleteList(List<Address> models) throws LeSouException {
		return service.DeleteList(models);
	}

	public boolean DeleteListByID(List<UUID> ids) throws LeSouException {
		return service.DeleteListByID(ids);
	}

	public boolean DeleteByID(UUID id) throws LeSouException {
		return service.DeleteByID(id);
	}

	public long GetRecordCount(String strWhere) throws LeSouException {
		return service.GetRecordCount(strWhere);
	}

	public boolean Exists(UUID id) throws LeSouException {
		return service.Exists(id);
	}

	public List<Address> GetListByPage(String strWhere, Page page)
			throws LeSouException {
		return service.GetListByPage(strWhere, page);
	}

	public List<String> GetAddressDB() throws LeSouException {
		//try {
			SoapObject request = service.getRequest("GetAddressDB");
			SoapObject response = (SoapObject) service.getResponse(
					"GetAddressDB", request);
			List<String> strList = new ArrayList<String>();
			int lenght = response.getPropertyCount();
			for (int i = 0; i < lenght; i++) {
				strList.add(response.getProperty(i).toString());
			}
			return strList;
		//} catch (Exception ex) {
		//	throw new LeSouException(LeSouExceptionType.EXCEPTION_UNKNOW,
		//			"Exception on DomainImpl!", "", ex);
		//}
	}

	public List<Address> GetListWhere(String strWhere, String order, String asc)
			throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

}

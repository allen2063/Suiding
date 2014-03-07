package com.suiding.domain.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.ksoap2.serialization.SoapObject;

import com.google.gson.Gson;
import com.suiding.domain.ITwitterDomain;
import com.suiding.model.Page;
import com.suiding.model.Twitter;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.LeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;
import com.suiding.util.LeSouException.LeSouExceptionType;

public class TwitterDomainImpl implements ITwitterDomain {

	ILeSouShopService<Twitter> service = ServiceFactory.getBssADDAO();
	
	public boolean Insert(Twitter model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(Twitter model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(Twitter model) throws LeSouException{
		return service.Delete(model);
	}

	public Twitter GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<Twitter> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<Twitter> models) throws LeSouException{
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

	public List<Twitter> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<Twitter> getListBySbID(UUID sbid, Page page)
			throws LeSouException {
		return this.GetListByPage(" where For_ID = '"+sbid+"'", page);
	}

	public List<Twitter> getListByUserID(UUID userid, Page page)
			throws LeSouException {
		Gson gson = LeSouShopService.getGson();
		try {
				String strpage = gson.toJson(page);
				SoapObject request = service.getRequest("GetListByUserID");
				request.addProperty("userid", userid.toString());
				request.addProperty("page", strpage);
				SoapObject response = (SoapObject) service.getResponse(
						"GetListByUserID", request);
				int length = response.getPropertyCount();
				List<Twitter> entities = new ArrayList<Twitter>();
				for (int i = 0; i < length; i++) {
					Twitter entity = (Twitter) gson.fromJson(
							response.getProperty(i).toString(), Twitter.class);
					entities.add(entity);
				}
				return entities;
			} catch (LeSouException ex) {
				throw ex;
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new LeSouException(LeSouExceptionType.EXCEPTION_UNKNOW,
						ex.getMessage(), ":\n" + ex.getMessage(), ex);
			} catch (Error er) {
				er.printStackTrace();
				throw new LeSouException(LeSouExceptionType.EXCEPTION_SERVICE, " "
						+ er.getMessage(), " \n" + er.getMessage() + "\n :"
						+ er.getStackTrace(), er);
			}
	}

	public List<Twitter> getListBySbIDAfter(UUID sbid, Date date)
			throws LeSouException {
		SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		return this.GetListWhere(" where For_ID = '"+sbid+"' and [Date] >= '"+strdate+"'", "[Date]", "desc");
	}

	public List<Twitter> getListBySbIDBefore(UUID sbid, Date date)
			throws LeSouException {
		SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		return this.GetListWhere(" where For_ID = '"+sbid+"' and [Date] <= '"+strdate+"'", "[Date]", "desc");
	}

	public List<Twitter> getListBySbIDBettwen(UUID sbid, Date begin, Date end)
			throws LeSouException {
		SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strbegin = sdf.format(begin);
		String strend = sdf.format(end);
		return this.GetListWhere(" where For_ID = '"+sbid+"' and ([Date] between '"+strbegin+"' and '"+strend+"') ", "[Date]", "desc");
	}

	public List<Twitter> GetListWhere(String strWhere, String order, String asc)
			throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

}
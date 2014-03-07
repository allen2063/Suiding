package com.suiding.domain.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.ksoap2.serialization.SoapObject;

import com.google.gson.Gson;
import com.suiding.constant.OrderStatus;
import com.suiding.domain.IOrderDomain;
import com.suiding.model.Order;
import com.suiding.model.Page;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.LeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;
import com.suiding.util.LeSouException.LeSouExceptionType;

public class OrderDomainImpl implements IOrderDomain {

	ILeSouShopService<Order> service = ServiceFactory.getOrderDAO();
	
	public boolean Insert(Order model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(Order model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(Order model) throws LeSouException{
		return service.Delete(model);
	}

	public Order GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<Order> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<Order> models) throws LeSouException{
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

	public List<Order> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public List<Order> GetListWhere(String strWhere, String order, String asc)
			throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

	@Override
	public long GetCountByStatus(UUID id, int status) 
			throws LeSouException {
		// TODO Auto-generated method stub
		if(status == OrderStatus.NONE){
			return service.GetRecordCount(" where User_ID='"+ id + "' ");
		}
		return service.GetRecordCount(" where User_ID='"+ id + "' and OrderStatus="+status);
	}

	@Override
	public List<Order> GetListByStatus(UUID id, int status, Page page)
			throws LeSouException {
		// TODO Auto-generated method stub
		if(status == OrderStatus.NONE){
			return service.GetListByPage(" where User_ID='"+ id + "' ", page);
		}
		return service.GetListByPage(" where User_ID='"+ id + "' and OrderStatus="+status, page);
	}
	
	@Override
	public long GetCountByFilters(UUID userid, int[] mFilters)
			throws LeSouException {
		// TODO Auto-generated method stub
		if(mFilters == null || mFilters.length == 0){
			return service.GetRecordCount(" where User_ID='"+ userid + "' ");
		}
        StringBuilder builder = new StringBuilder(" where User_ID='");   
        builder.append(userid.toString()); 
        builder.append("' and OrderStatus in (");
		for (int i = 0; i < mFilters.length; i++) {
			if(i > 0){
		        builder.append(',');
			}
	        builder.append(mFilters[i]);
		}
        builder.append(')');
		return service.GetRecordCount(builder.toString());
	}
	
	@Override
	public List<Order> GetListByFilters(UUID userid, int[] mFilters, Page page)
			throws LeSouException {
		// TODO Auto-generated method stub
		if(mFilters == null || mFilters.length == 0){
			return service.GetListByPage(" where User_ID='"+ userid + "' ", page);
		}
        StringBuilder builder = new StringBuilder(" where User_ID='");   
        builder.append(userid.toString()); 
        builder.append("' and OrderStatus in (");
		for (int i = 0; i < mFilters.length; i++) {
			if(i > 0){
		        builder.append(',');
			}
	        builder.append(mFilters[i]);
		}
        builder.append(')');
		return service.GetListByPage(builder.toString(), page);
	}
	
	@Override
	public long CountWaitOrdersByUser(UUID userid) throws LeSouException{
		// TODO Auto-generated method stub
		try {
			SoapObject request = service.getRequest("CountWaitOrdersByUser");
			request.addProperty("userid", userid.toString());
			Object response = service.getResponse("CountWaitOrdersByUser", request);
			Gson gson = LeSouShopService.getGson();
			return gson.fromJson(response.toString(), long.class);
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
	
	@Override
	public List<Order> GetWaitOrdersByUser(UUID userid) throws LeSouException{
		// TODO Auto-generated method stub
		try {
			SoapObject request = service.getRequest("GetWaitOrdersByUser");
			request.addProperty("userid", userid.toString());
			SoapObject response = (SoapObject) service.getResponse("GetWaitOrdersByUser",
					request);
			int length = response.getPropertyCount();
			List<Order> entities = new ArrayList<Order>();
			Gson gson = LeSouShopService.getGson();
			for (int i = 0; i < length; i++) {
				Order entity = (Order) gson.fromJson(
						response.getProperty(i).toString(), Order.class);
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
	
	@Override
	public List<Order> GetOrdersStatus(List<Order> ltorder) throws LeSouException{
		// TODO Auto-generated method stub
		if(ltorder == null || ltorder.size() == 0){
			return ltorder;
		}
        StringBuilder builder = new StringBuilder(" where ");   
        builder.append("Ode_ID in (");
		for (int i = 0; i < ltorder.size(); i++) {
			if(i > 0){
		        builder.append(',');
			}
	        builder.append("'");
	        builder.append(ltorder.get(i).getID());
	        builder.append("'");
		}
        builder.append(')');
		return service.GetListByPage(builder.toString(), new Page(ltorder.size(), 0));
	}
}












package com.suiding.domain.impl;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.google.gson.Gson;
import com.suiding.domain.IAreaDomain;
import com.suiding.model.Area;
import com.suiding.model.Page;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.LeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;
import com.suiding.util.LeSouException.LeSouExceptionType;

public class AreaDomainImpl implements IAreaDomain {

	ILeSouShopService<Area> service = ServiceFactory.getAreaDAO();

	public List<Area> getAllCity() throws LeSouException {
		Gson gson = LeSouShopService.getGson();
		try
		{
			SoapObject request = service.getRequest("GetAllCity");
			SoapObject response = (SoapObject) service.getResponse(
					"GetAllCity", request);
			int length = response.getPropertyCount();
			List<Area> models = new ArrayList<Area>();
			for (int i = 0; i < length; i++) {
				Area entity = (Area) gson.fromJson(
						response.getProperty(i).toString(), Area.class);
				models.add(entity);
			}
			return models;
		}
		catch(Exception ex)
		{
			return null;
		}
	}

	/**
	 * 获取指定城市名称的Model 其中父亲和子项已经赋值 注意：此方法为模糊查询
	 */
	public Area getCityByNameLike(String name) throws LeSouException {
		Gson gson = LeSouShopService.getGson();
		try {
			SoapObject request = service.getRequest("GetCityByNameLike");
			request.addProperty("name", name);
			Object response = service.getResponse("GetCityByNameLike", request);

			Area entity = (Area) gson.fromJson(response.toString(), Area.class);
			return entity;
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

	/**
	 * 获取指定城市名称的Model 其中父亲和子项已经赋值
	 */
	public Area getCityByName(String name) throws LeSouException {
		Gson gson = LeSouShopService.getGson();
		try {
			SoapObject request = service.getRequest("GetCityByName");
			request.addProperty("name", name);
			Object response = service.getResponse("GetCityByName", request);

			Area entity = (Area) gson.fromJson(response.toString(), Area.class);
			return entity;
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

	/**
	 * 直辖市 0 : 北京市 1 : 天津市 2 : 上海市 3 : 重庆市
	 */
	public static final List<Area> Municipalities = new ArrayList<Area>();

	static {
		Municipalities.add(new Area(1, "北京市", 1, 0));
		Municipalities.add(new Area(2, "天津市", 1, 0));
		Municipalities.add(new Area(9, "上海市", 1, 0));
		Municipalities.add(new Area(22, "重庆市", 1, 0));
	}

	public List<Area> getChildreByPid(int pid) throws LeSouException {

		Gson gson = LeSouShopService.getGson();
		try
		{
			SoapObject request = service.getRequest("GetChildreByPid");
			SoapObject response = (SoapObject) service.getResponse(
					"GetChildreByPid", request);
			int length = response.getPropertyCount();
			List<Area> models = new ArrayList<Area>();
			for (int i = 0; i < length; i++) {
				Area entity = (Area) gson.fromJson(
						response.getProperty(i).toString(), Area.class);
				models.add(entity);
			}
			return models;
		}
		catch(Exception ex)
		{
			return null;
		}
	}

	public Area getAreaByNameLike(String name) throws LeSouException {
		Gson gson = LeSouShopService.getGson();
		try {
			SoapObject request = service.getRequest("GetAreaByNameLike");
			request.addProperty("name", name);
			Object response = service.getResponse("GetAreaByNameLike", request);

			Area entity = (Area) gson.fromJson(response.toString(), Area.class);
			return entity;
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

	public Area getAreaByName(String name) throws LeSouException {
		Gson gson = LeSouShopService.getGson();
		try {
			SoapObject request = service.getRequest("GetAreaByName");
			request.addProperty("name", name);
			Object response = service.getResponse("GetAreaByName", request);

			Area entity = (Area) gson.fromJson(response.toString(), Area.class);
			return entity;
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
	public Area getAreaByID(int id) throws LeSouException {
		// TODO Auto-generated method stub
		Gson gson = LeSouShopService.getGson();
		try {
			SoapObject request = service.getRequest("GetAreaByID");
			request.addProperty("id", id);
			Object response = service.getResponse("GetAreaByID", request);

			Area entity = (Area) gson.fromJson(response.toString(), Area.class);
			return entity;
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
	public List<Area> getAllAreaOderByID() throws LeSouException {
		// TODO Auto-generated method stub
		return service.GetListWhere("", " id ", "asc");
	}

	@Override
	public List<Area> getAllAreaOderByID(Page page) throws LeSouException {
		return service.GetListByPage("", page);
	}
}

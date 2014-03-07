package com.suiding.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suiding.model.Page;
import com.suiding.util.LeSouException;
import com.suiding.util.TimestampTypeAdapter;
import com.suiding.util.LeSouException.LeSouExceptionType;

public class LeSouShopService<T> implements ILeSouShopService<T> {

	private Class<?> clazz = null;
	private static Gson gson = null;
	/**
	 * 日期显示格式 "yyyy-MM-dd HH:mm:ss"
	 */
	public static final String DateFormat = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 枚举DEBUG模式
	 */
	public static final int DEBUG_USER = 0;	//用户使用 调试方式：邮件发送错误信息
	public static final int DEBUG_JAVA = 1;	//电脑调试 调试方式：使用java连接 localhost
	public static final int DEBUG_PHONE = 2;	//手机调试 调试方式：使用simulator连接 10.0.0.2

	private static final String NAMESPACE = "http://tempuri.org/";// WebService
	
	private static final String WSDLURL = "http://www.suiding.net:8088/Service.svc";
	private static final String SOAP_ACTION = "http://tempuri.org/ILeSouShopService/";

	private static final String LOCAL_WSDLURL = "http://localhost:59176/LeSouShop.Service/Service.svc";
	private static final String LOCAL_SOAP_ACTION = "http://tempuri.org/ILeSouShopService/";

	private static final String PHONE_WSDLURL = "http://10.0.2.2:21754/LeSouShop.Service/Service.svc";
	private static final String PHONE_SOAP_ACTION = "http://tempuri.org/ILeSouShopService/";

	private static final int debugmode = DEBUG_USER;

	private static HttpTransportSE Transport = null;
	private static SoapSerializationEnvelope envelope = null;

	private static String[] MODE_WSDLURL = new String[]{
		WSDLURL,LOCAL_WSDLURL,PHONE_WSDLURL
	};
	private static String[] MODE_ACTION = new String[]{
		SOAP_ACTION,LOCAL_SOAP_ACTION,PHONE_SOAP_ACTION
	};

	static {
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		Transport = new HttpTransportSE(MODE_WSDLURL[debugmode]);
		//if (!debugmode) {
		//	Transport = new HttpTransportSE(WSDLURL);
		//} else {
		//	Transport = new HttpTransportSE(LOCAL_WSDLURL);
		//}
	}

	static {
		gson = new GsonBuilder()
				.registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter

				()).setDateFormat(DateFormat).create();
	}

	public static Gson getGson() {
		if (gson == null) {
			gson = new GsonBuilder()
					.registerTypeAdapter(Timestamp.class,
							new TimestampTypeAdapter())
					.setDateFormat(DateFormat).create();
		}
		return gson;
	}

	@SuppressWarnings("unused")
	private LeSouShopService() {
		gson = new GsonBuilder()
				.registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter

				()).setDateFormat(DateFormat).create();
	}

	public LeSouShopService(Class<?> clazz) {
		this.clazz = clazz;
		gson = new GsonBuilder()
				.registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter

				()).setDateFormat(DateFormat).create();
	}


	public String getMothedName(String shortmothedname) {
		String str = clazz.getName();
		str = str.substring(str.lastIndexOf(".") + 1);
		return str + shortmothedname;
	}

	public SoapObject getRequest(String shortmothedname) {
		return new SoapObject(NAMESPACE, getMothedName(shortmothedname));
	}

	public Object getResponse(String shortmothedname, SoapObject request)
			throws LeSouException {
		// envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.bodyOut = request;

		try {
			// Transport = new HttpTransportSE(WSDLUrl);
			// Transport.debug = true;

			String soapaction = MODE_ACTION[debugmode];
			
			soapaction += this.getMothedName(shortmothedname);
			//if (!debugmode) {
			//	soapaction = SOAP_ACTION + this.getMothedName(shortmothedname);
			//} else {
			//	soapaction = LOCAL_SOAP_ACTION
			//			+ this.getMothedName(shortmothedname);
			//}

			Transport.call(soapaction, envelope);
			return envelope.getResponse();
		} catch (IOException e) {
			e.printStackTrace();
			throw new LeSouException(LeSouExceptionType.EXCEPTION_IO,
					"网络连接出现异常！", e.getMessage(), e);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			throw new LeSouException(LeSouExceptionType.EXCEPTION_SERVICE,
					e.getMessage(), ":\n" + e.getMessage(), e);
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

	public boolean Insert(T entity) throws LeSouException {
		try {
			SoapObject request = this.getRequest("Insert");
			String s = gson.toJson(entity);
			request.addProperty("entity", s);

			Object response = this.getResponse("Insert", request);

			if (response.toString().toLowerCase(Locale.ENGLISH).equals("false")) {
				throw new LeSouException("一个内部的错误，攻城师正在努力修复中。。。");
			}

			return true;
		} catch (LeSouException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
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

	public boolean Update(T entity) throws LeSouException {
		try {

			SoapObject request = this.getRequest("Update");
			String s = gson.toJson(entity);
			request.addProperty("entity", s);

			Object response = this.getResponse("Update", request);

			return gson.fromJson(response.toString(), boolean.class);
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

	public boolean Delete(T entity) throws LeSouException {
		try {

			SoapObject request = this.getRequest("Delete");
			String s = gson.toJson(entity);
			request.addProperty("entity", s);

			Object response = this.getResponse("Delete", request);
			return gson.fromJson(response.toString(), boolean.class);
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

	@SuppressWarnings("unchecked")
	public T GetByID(UUID id) throws LeSouException {
		try {

			SoapObject request = this.getRequest("GetByID");
			request.addProperty("id", id.toString());
			Object response = this.getResponse("GetByID", request);

			T entity = (T) gson.fromJson(response.toString(), clazz);
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

	public List<T> GetAll() throws LeSouException {
		try {
			SoapObject response = (SoapObject) this.getResponse("GetAll",
					this.getRequest("GetAll"));
			int length = response.getPropertyCount();
			List<T> entities = new ArrayList<T>();
			for (int i = 0; i < length; i++) {
				@SuppressWarnings("unchecked")
				T entity = (T) gson.fromJson(
						response.getProperty(i).toString(), clazz);
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

	public boolean DeleteList(List<T> entities) throws LeSouException {
		try {

			SoapObject request = this.getRequest("DeleteList");
			String s = gson.toJson(entities);
			request.addProperty("entities", s);
			Object response = this.getResponse("DeleteList", request);

			return gson.fromJson(response.toString(), boolean.class);

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

	public boolean DeleteListByID(List<UUID> ids) throws LeSouException {
		try {
			SoapObject request = this.getRequest("DeleteListByID");
			String s = gson.toJson(ids);
			request.addProperty("ids", s);
			Object response = this.getResponse("DeleteListByID", request);

			return gson.fromJson(response.toString(), boolean.class);

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

	public boolean DeleteByID(UUID id) throws LeSouException {
		try {

			SoapObject request = this.getRequest("DeleteByID");
			String s = gson.toJson(id);
			request.addProperty("id", s);
			Object response = this.getResponse("DeleteByID", request);

			return gson.fromJson(response.toString(), boolean.class);

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

	public boolean Exists(UUID id) throws LeSouException {
		try {
			SoapObject request = this.getRequest("Exists");
			request.addProperty("id", gson.toJson(id));
			Object response = this.getResponse("Exists", request);

			return gson.fromJson(response.toString(), boolean.class);

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

	public List<T> GetListByPage(String strWhere, Page page)
			throws LeSouException {
		try {
			String strpage = gson.toJson(page);
			SoapObject request = this.getRequest("GetListByPage");
			request.addProperty("strWhere", strWhere);
			request.addProperty("page", strpage);
			SoapObject response = (SoapObject) this.getResponse(
					"GetListByPage", request);
			int length = response.getPropertyCount();
			List<T> entities = new ArrayList<T>();
			for (int i = 0; i < length; i++) {
				@SuppressWarnings("unchecked")
				T entity = (T) gson.fromJson(
						response.getProperty(i).toString(), clazz);
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

	public long GetRecordCount(String strWhere) throws LeSouException {
		try {

			SoapObject request = this.getRequest("GetRecordCount");
			request.addProperty("strWhere", strWhere);
			Object response = this.getResponse("GetRecordCount", request);

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

	public List<T> GetListWhere(String strWhere, String order, String asc)
			throws LeSouException {
		try {
			SoapObject request = this.getRequest("GetListWhere");
			request.addProperty("strWhere", strWhere);
			request.addProperty("order", order);
			request.addProperty("asc", asc);
			SoapObject response = (SoapObject) this.getResponse("GetListWhere",
					request);
			int length = response.getPropertyCount();
			List<T> entities = new ArrayList<T>();
			for (int i = 0; i < length; i++) {
				@SuppressWarnings("unchecked")
				T entity = (T) gson.fromJson(
						response.getProperty(i).toString(), clazz);
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

	public List<T> getList(String mothedname,String[][] properties) throws LeSouException
	{
		try {
			Gson gson = LeSouShopService.getGson();
			SoapObject request = this.getRequest(mothedname);
			if(properties!=null)
			{
				for(int i = 0 ;i < properties.length;i++)
				{
					request.addProperty(properties[i][0], properties[i][1]);
				}
			}
			SoapObject response = (SoapObject) this.getResponse(mothedname,request);
			int length = response.getPropertyCount();
			List<T> entities = new ArrayList<T>();
			for (int i = 0; i < length; i++) {
				@SuppressWarnings("unchecked")
				T entity = (T) gson.fromJson(
						response.getProperty(i).toString(), clazz);
				entities.add(entity);
			}
			return entities;
		}catch (Exception ex) {
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
	
}

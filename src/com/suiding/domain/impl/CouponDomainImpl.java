package com.suiding.domain.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.ksoap2.serialization.SoapObject;

import com.google.gson.Gson;
import com.suiding.domain.ICouponDomain;
import com.suiding.model.Address;
import com.suiding.model.Coupon;
import com.suiding.model.Page;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.LeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;
import com.suiding.util.LeSouException.LeSouExceptionType;

public class CouponDomainImpl implements ICouponDomain {

	ILeSouShopService<Coupon> service = ServiceFactory.getCouponDAO();
	SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat,Locale.ENGLISH);
	
	public boolean Insert(Coupon model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(Coupon model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(Coupon model) throws LeSouException{
		return service.Delete(model);
	}

	public Coupon GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<Coupon> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<Coupon> models) throws LeSouException{
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
	
	@Override
	public long getCountBySbID(UUID id) throws LeSouException {
		// TODO Auto-generated method stub
		return GetRecordCount(" where For_ID = '"+id+"'");
	}

	public List<Coupon> GetListByPage(String strWhere, Page page) throws LeSouException{
//		if("".equals(page.Order))
//		{
//			page.IsASC = false;
//			page.Order = " [date] ";
//		}
		return service.GetListByPage(strWhere, page);
	}

	public List<Coupon> GetListWhere(String strWhere, String order, String asc)
			throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

	public List<Coupon> getListByPbID(UUID pbid, Page page)
			throws LeSouException {
//		if("".equals(page.Order))
//		{
//			page.IsASC = false;
//			page.Order = " [date] ";
//		}
		return this.GetListByPage(" where Pb_ID = '"+pbid+"'", page);
	}

	public List<Coupon> getListBySbID(UUID sbid, Page page)
			throws LeSouException {
//		if("".equals(page.Order))
//		{
//			page.IsASC = false;
//			page.Order = " [date] ";
//		}
		return this.GetListByPage(" where For_ID = '"+sbid+"'", page);
	}

	public List<Coupon> getListBySbIDAfter(UUID sbid, Date date)
			throws LeSouException {
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		return this.GetListWhere(" where For_ID = '"+sbid+"' and [Date] >= '"+strdate+"'", "[Date]", "desc");
	}

	public List<Coupon> getListBySbIDBefore(UUID sbid, Date date)
			throws LeSouException {
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		return this.GetListWhere(" where For_ID = '"+sbid+"' and [Date] <= '"+strdate+"'", "[Date]", "desc");
	}

	public List<Coupon> getListBySbIDBettwen(UUID sbid, Date begin, Date end)
			throws LeSouException {
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strbegin = sdf.format(begin);
		String strend = sdf.format(end);
		return this.GetListWhere(" where For_ID = '"+sbid+"' and ([Date] between '"+strbegin+"' and '"+strend+"') ", "[Date]", "desc");
	}

	public List<Coupon> getListByUserID(UUID userid, Page page)
			throws LeSouException {
		try {
//			if("".equals(page.Order))
//			{
//				page.IsASC = false;
//				page.Order = " [date] ";
//			}
			Gson gson = LeSouShopService.getGson();
			SoapObject request = service.getRequest("GetListByUserIDPage");
			request.addProperty("userid", userid.toString());
			request.addProperty("page", gson.toJson(page));
			SoapObject response = (SoapObject) service.getResponse("GetListByUserIDPage",
					request);
			int length = response.getPropertyCount();
			List<Coupon> entities = new ArrayList<Coupon>();
			for (int i = 0; i < length; i++) {
				Coupon entity = (Coupon) gson.fromJson(
						response.getProperty(i).toString(), Coupon.class);
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

	public List<Coupon> getListByUserID(UUID userid) throws LeSouException {
		try {
			Gson gson = LeSouShopService.getGson();
			SoapObject request = service.getRequest("GetListByUserID");
			request.addProperty("userid", userid.toString());
			SoapObject response = (SoapObject) service.getResponse("GetListByUserID",
					request);
			int length = response.getPropertyCount();
			List<Coupon> entities = new ArrayList<Coupon>();
			for (int i = 0; i < length; i++) {
				Coupon entity = (Coupon) gson.fromJson(
						response.getProperty(i).toString(), Coupon.class);
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


	public List<Coupon> getListByUserIDAfter(UUID userid, Date date)
			throws LeSouException {
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		String[][] properties = new String[][]{
			new String[]{"userid",userid.toString()},
			new String[]{"page",null},
			new String[]{"andwhere"," and [Date] >= '"+strdate+"'"}			
		};
		return service.getList("GetListByUserIDPageWhere", properties);
	}

	public List<Coupon> getListByUserIDAfter(UUID userid, Page page, Date date)
			throws LeSouException {
//		if("".equals(page.Order))
//		{
//			page.IsASC = false;
//			page.Order = " [date] ";
//		}
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		String[][] properties = new String[][]{
			new String[]{"userid",userid.toString()},
			new String[]{"page",LeSouShopService.getGson().toJson(page)},
			new String[]{"andwhere"," and [Date] >= '"+strdate+"'"}			
		};
		return service.getList("GetListByUserIDPageWhere", properties);
	}

	public List<Coupon> getListByUserIDBefore(UUID userid, Date date)
			throws LeSouException {
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		String[][] properties = new String[][]{
			new String[]{"userid",userid.toString()},
			new String[]{"page",null},
			new String[]{"andwhere"," and [Date] <= '"+strdate+"'"}			
		};
		return service.getList("GetListByUserIDPageWhere", properties);
	}

	public List<Coupon> getListByUserIDBefore(UUID userid, Page page, Date date)
			throws LeSouException {
//		if("".equals(page.Order))
//		{
//			page.IsASC = false;
//			page.Order = " [date] ";
//		}
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		String[][] properties = new String[][]{
			new String[]{"userid",userid.toString()},
			new String[]{"page",LeSouShopService.getGson().toJson(page)},
			new String[]{"andwhere"," and [Date] <= '"+strdate+"'"}			
		};
		return service.getList("GetListByUserIDPageWhere", properties);
	}

	public List<Coupon> getListByUserIDBettwen(UUID userid, Date begin, Date end)
			throws LeSouException {
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strbegin = sdf.format(begin);
		String strend = sdf.format(end);
		String[][] properties = new String[][]{
			new String[]{"userid",userid.toString()},
			new String[]{"page",null},
			new String[]{"andwhere"," and ([Date] between '"+strbegin+"' and '"+strend+"') "}			
		};
		return service.getList("GetListByUserIDPageWhere", properties);
	}

	public List<Coupon> getListByUserIDBettwen(UUID userid, Page page, Date begin, Date end)
			throws LeSouException {
//		if("".equals(page.Order))
//		{
//			page.IsASC = false;
//			page.Order = " [date] ";
//		}
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strbegin = sdf.format(begin);
		String strend = sdf.format(end);
		String[][] properties = new String[][]{
			new String[]{"userid",userid.toString()},
			new String[]{"page",LeSouShopService.getGson().toJson(page)},
			new String[]{"andwhere"," and ([Date] between '"+strbegin+"' and '"+strend+"') "}			
		};
		return service.getList("GetListByUserIDPageWhere", properties);
	}

	public List<Coupon> getListByAddress(Address address, Page page)
			throws LeSouException {
//		if("".equals(page.Order))
//		{
//			page.IsASC = false;
//			page.Order = " [date] ";
//		}
		return this.getListByAddress(address, page, "");
	}

	public List<Coupon> getListByAddress(Address address) throws LeSouException {
		return this.getListByAddress(address, null, "");
	}

	public List<Coupon> getListByStoreType(int type, Page page)
			throws LeSouException {
//		if("".equals(page.Order))
//		{
//			page.IsASC = false;
//			page.Order = " [date] ";
//		}
		return this.getListByStoreType(type, page, "");
	}

	public List<Coupon> getListByStoreType(int type) throws LeSouException {
		return this.getListByStoreType(type, null, "");
	}

	public List<Coupon> getListByAddressStoreType(Address address, int type,
			Page page) throws LeSouException {
//		if("".equals(page.Order))
//		{
//			page.IsASC = false;
//			page.Order = " [date] ";
//		}
		return this.getListByAddressStoreType(address, type, page, "");
	}

	public List<Coupon> getListByAddressStoreType(Address address, int type)
			throws LeSouException {
		return this.getListByAddressStoreType(address, type, null, "");
	}

	public List<Coupon> GetListOf(float x, float y, float radius, int type)
			throws LeSouException {
		return this.GetListOf(x, y, radius, type, "");
	}


	private List<Coupon> getListByAddress(Address address, Page page,String andwhere)
			throws LeSouException {
//		if("".equals(page.Order))
//		{
//			page.IsASC = false;
//			page.Order = " [date] ";
//		}
		String[][] properties = new String[][]{
				new String[]{"address",LeSouShopService.getGson().toJson(address)},
				new String[]{"page",LeSouShopService.getGson().toJson(page)},
				new String[]{"andwhere",andwhere}};
		return service.getList("GetListByAddress", properties);
	}
	
	private List<Coupon> getListByStoreType(int type, Page page,String andwhere)
			throws LeSouException {
//		if("".equals(page.Order))
//		{
//			page.IsASC = false;
//			page.Order = " [date] ";
//		}
		String[][] properties = new String[][]{
				new String[]{"type",type+""},
				new String[]{"page",LeSouShopService.getGson().toJson(page)},
				new String[]{"andwhere",andwhere}};
		return service.getList("GetListByStoreType", properties);
	}
	
	private List<Coupon> getListByAddressStoreType(Address address, int type,
			Page page,String andwhere) throws LeSouException {
		String[][] properties = new String[][]{
				new String[]{"address",LeSouShopService.getGson().toJson(address)},
				new String[]{"type",type+""},
				new String[]{"page",LeSouShopService.getGson().toJson(page)},
				new String[]{"andwhere",andwhere}};
		return service.getList("GetListByAddressStoreType", properties);
	}
	
	private List<Coupon> GetListOf(float x, float y, float radius, int type,String andwhere)
			throws LeSouException {
		String[][] properties = new String[][]{
				new String[]{"x",x+""},
				new String[]{"y",y+""},
				new String[]{"radius",radius+""},
				new String[]{"type",type+""},
				new String[]{"andwhere",andwhere}};
		return service.getList("GetListByAddressStoreType", properties);
	}


	public List<Coupon> getListByAddressAfter(Address address, Page page,
			Date date) throws LeSouException {
//		if("".equals(page.Order))
//		{
//			page.IsASC = false;
//			page.Order = " [date] ";
//		}
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		String andwhere = " and [Date] >= '"+strdate+"'";
		return this.getListByAddress(address, page, andwhere);
	}

	public List<Coupon> getListByAddressBefore(Address address, Page page,
			Date date) throws LeSouException {
//		if("".equals(page.Order))
//		{
//			page.IsASC = false;
//			page.Order = " [date] ";
//		}
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		String andwhere = " and [Date] <= '"+strdate+"'";
		return this.getListByAddress(address, page, andwhere);
	}

	public List<Coupon> getListByAddressBettwen(Address address, Page page,
			Date begin, Date end) throws LeSouException {
//		if("".equals(page.Order))
//		{
//			page.IsASC = false;
//			page.Order = " [date] ";
//		}
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strbegin = sdf.format(begin);
		String strend = sdf.format(end);
		String andwhere = " and ([Date] between '"+strbegin+"' and '"+strend+"') ";
		return this.getListByAddress(address, page, andwhere);
	}

	public List<Coupon> getListByAddressAfter(Address address, Date date)
			throws LeSouException {
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		String andwhere = " and [Date] >= '"+strdate+"'";
		return this.getListByAddress(address, null, andwhere);
	}

	public List<Coupon> getListByAddressBefore(Address address, Date date)
			throws LeSouException {
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		String andwhere = " and [Date] <= '"+strdate+"'";
		return this.getListByAddress(address, null, andwhere);
	}

	public List<Coupon> getListByAddressBettwen(Address address, Date begin,
			Date end) throws LeSouException {
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strbegin = sdf.format(begin);
		String strend = sdf.format(end);
		String andwhere = " and ([Date] between '"+strbegin+"' and '"+strend+"') ";
		return this.getListByAddress(address, null, andwhere);
	}

	public List<Coupon> getListByStoreTypeAfter(int type, Page page, Date date)
			throws LeSouException {
//		if("".equals(page.Order))
//		{
//			page.IsASC = false;
//			page.Order = " [date] ";
//		}
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		String andwhere = " and [Date] >= '"+strdate+"'";
		return this.getListByStoreType(type, page, andwhere);
	}

	public List<Coupon> getListByStoreTypeBefore(int type,
			Page page, Date date) throws LeSouException {
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		String andwhere = " and [Date] <= '"+strdate+"'";
		return this.getListByStoreType(type, page, andwhere);
	}

	public List<Coupon> getListByStoreTypeBettwen(int type, Page page,
			Date begin, Date end) throws LeSouException {
//		if("".equals(page.Order))
//		{
//			page.IsASC = false;
//			page.Order = " [date] ";
//		}
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strbegin = sdf.format(begin);
		String strend = sdf.format(end);
		String andwhere = " and ([Date] between '"+strbegin+"' and '"+strend+"') ";
		return this.getListByStoreType(type, page, andwhere);
	}

	public List<Coupon> getListByStoreTypeAfter(int type, Date date)
			throws LeSouException {
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		String andwhere = " and [Date] >= '"+strdate+"'";
		return this.getListByStoreType(type, null, andwhere);
	}

	public List<Coupon> getListByStoreTypeBefore(int type, Date date)
			throws LeSouException {
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		String andwhere = " and [Date] <= '"+strdate+"'";
		return this.getListByStoreType(type,null, andwhere);
	}

	public List<Coupon> getListByStoreTypeBettwen(int type, Date begin, Date end)
			throws LeSouException {
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strbegin = sdf.format(begin);
		String strend = sdf.format(end);
		String andwhere = " and ([Date] between '"+strbegin+"' and '"+strend+"') ";
		return this.getListByStoreType(type,null, andwhere);
	}

	public List<Coupon> getListByAddressStoreTypeAfter(Address address,
			int type, Page page, Date date) throws LeSouException {
//		if("".equals(page.Order))
//		{
//			page.IsASC = false;
//			page.Order = " [date] ";
//		}
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		String andwhere = " and [Date] >= '"+strdate+"'";
		return this.getListByAddressStoreType(address,type, page, andwhere);
	}

	public List<Coupon> getListByAddressStoreTypeBefore(Address address,
			int type, Page page, Date date) throws LeSouException {
//		if("".equals(page.Order))
//		{
//			page.IsASC = false;
//			page.Order = " [date] ";
//		}
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		String andwhere = " and [Date] <= '"+strdate+"'";
		return this.getListByAddressStoreType(address,type,page, andwhere);
	}

	public List<Coupon> getListByAddressStoreTypeBettwen(Address address,
			int type, Page page, Date begin, Date end) throws LeSouException {
//		if("".equals(page.Order))
//		{
//			page.IsASC = false;
//			page.Order = " [date] ";
//		}
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strbegin = sdf.format(begin);
		String strend = sdf.format(end);
		String andwhere = " and ([Date] between '"+strbegin+"' and '"+strend+"') ";
		return this.getListByAddressStoreType(address,type,page, andwhere);
	}

	public List<Coupon> getListByAddressStoreTypeAfter(Address address,
			int type, Date date) throws LeSouException {
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		String andwhere = " and [Date] >= '"+strdate+"'";
		return this.getListByAddressStoreType(address,type, null, andwhere);
	}

	public List<Coupon> getListByAddressStoreTypeBefore(Address address,
			int type, Date date) throws LeSouException {
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		String andwhere = " and [Date] <= '"+strdate+"'";
		return this.getListByAddressStoreType(address,type,null, andwhere);
	}

	public List<Coupon> getListByAddressStoreTypeBettwen(Address address,
			int type, Date begin, Date end) throws LeSouException {
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strbegin = sdf.format(begin);
		String strend = sdf.format(end);
		String andwhere = " and ([Date] between '"+strbegin+"' and '"+strend+"') ";
		return this.getListByAddressStoreType(address,type,null, andwhere);
	}

	public List<Coupon> GetListOfAfter(float x, float y, float radius,
			int type, Date date) throws LeSouException {
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		String andwhere = " and [Date] >= '"+strdate+"'";
		return this.GetListOf(x,y, radius,type, andwhere);
	}

	public List<Coupon> GetListOfBefore(float x, float y, float radius,
			int type, Date date) throws LeSouException {
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strdate = sdf.format(date);
		String andwhere = " and [Date] <= '"+strdate+"'";
		return this.GetListOf(x,y,radius,type, andwhere);
	}

	public List<Coupon> GetListOfBettwen(float x, float y, float radius,
			int type, Date begin, Date end) throws LeSouException {
		//SimpleDateFormat sdf = new SimpleDateFormat(LeSouShopService.DateFormat);
		String strbegin = sdf.format(begin);
		String strend = sdf.format(end);
		String andwhere = " and ([Date] between '"+strbegin+"' and '"+strend+"') ";
		return this.GetListOf(x,y,radius,type, andwhere);
	}

	@Override
	public List<Coupon> getPromotionByUserID(UUID userid) throws LeSouException {
		// TODO Auto-generated method stub
		String promotion = "select p.For_ID from BusinessPromotion as p where p.User_ID = '" + userid + "'";
		return this.GetListWhere(" where Cou_ID in("+promotion+") ", "[Date]", "desc");
	}

	@Override
	public List<Coupon> getPromotionByUserID(UUID userid, Page page)
			throws LeSouException {
		// TODO Auto-generated method stub
		String promotion = "select p.For_ID from BusinessPromotion as p where p.User_ID = '" + userid + "'";
		return service.GetListByPage(" where Cou_ID in("+promotion+") ", page);
	}

	@Override
	public List<Coupon> getPromotionByUserIDAfter(UUID userid, Date date)
			throws LeSouException {
		// TODO Auto-generated method stub
		String after = sdf.format(date);
		String promotion = "select p.For_ID from BusinessPromotion as p where p.User_ID = '" + userid + "'";
		return this.GetListWhere(" where Cou_ID in("+promotion+") and ([Date] >= '"+after+"') ", "[Date]", "desc");
	}

	@Override
	public List<Coupon> getPromotionByUserIDAfter(UUID userid, Page page,
			Date date) throws LeSouException {
		// TODO Auto-generated method stub
		String after = sdf.format(date);
		String promotion = "select p.For_ID from BusinessPromotion as p where p.User_ID = '" + userid + "'";
		return service.GetListByPage(" where Cou_ID in("+promotion+") and ([Date] >= '"+after+"') ",page);
	}

	@Override
	public List<Coupon> getPromotionByUserIDBefore(UUID userid, Date date)
			throws LeSouException {
		// TODO Auto-generated method stub
		String before = sdf.format(date);
		String promotion = "select p.For_ID from BusinessPromotion as p where p.User_ID = '" + userid + "'";
		return this.GetListWhere(" where Cou_ID in("+promotion+") ([Date] <= '"+before+"') ", "[Date]", "desc");
	}

	@Override
	public List<Coupon> getPromotionByUserIDBefore(UUID userid, Page page,
			Date date) throws LeSouException {
		// TODO Auto-generated method stub
		String before = sdf.format(date);
		String promotion = "select p.For_ID from BusinessPromotion as p where p.User_ID = '" + userid + "'";
		return service.GetListByPage(" where Cou_ID in("+promotion+") and ([Date] <= '"+before+"') ",page);
	}

	@Override
	public List<Coupon> getPromotionByUserIDBettwen(UUID userid, Date begin,
			Date end) throws LeSouException {
		// TODO Auto-generated method stub
		String strbegin = sdf.format(begin);
		String strend = sdf.format(end);
		String promotion = "select p.For_ID from BusinessPromotion as p where p.User_ID = '" + userid + "'";
		return this.GetListWhere(" where Cou_ID in("+promotion+") and ([Date] between '"+strbegin+"' and '"+strend+"') ", "[Date]", "desc");
	}

	@Override
	public List<Coupon> getPromotionByUserIDBettwen(UUID userid, Page page,
			Date begin, Date end) throws LeSouException {
		// TODO Auto-generated method stub
		String strbegin = sdf.format(begin);
		String strend = sdf.format(end);
		String promotion = "select p.For_ID from BusinessPromotion as p where p.User_ID = '" + userid + "'";
		return service.GetListByPage(" where Cou_ID in("+promotion+") and ([Date] between '"+strbegin+"' and '"+strend+"') ", page);
	}
}
package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import org.ksoap2.serialization.SoapObject;

import com.google.gson.Gson;
import com.suiding.constant.FavoriteType;
import com.suiding.domain.IFavoriteDomain;
import com.suiding.domain.IUserDomain;
import com.suiding.model.Favorite;
import com.suiding.model.Page;
import com.suiding.model.StoreBase;
import com.suiding.model.User;
import com.suiding.service.DomainFactory;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.LeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class UserDomainImpl implements IUserDomain {
	ILeSouShopService<User> service = ServiceFactory.getUserDAO();
	
	public boolean Insert(User model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(User model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(User model) throws LeSouException{
		return service.Delete(model);
	}

	public User GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<User> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<User> models) throws LeSouException{
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

	public List<User> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}

	public User Verify(String Name, String Password) throws LeSouException{
		//try {
			SoapObject request = service.getRequest("Verify");
			request.addProperty("Name", Name);
			request.addProperty("Password", Password);
			Object response = service.getResponse("Verify", request);
			Gson gson = LeSouShopService.getGson();
			return gson.fromJson(response.toString(), User.class);
		//} catch (Exception ex) {
		//	throw new LeSouException(LeSouExceptionType.EXCEPTION_UNKNOW,
		//			"Exception on DomainImpl!", "", ex);
		//}
	}
	
	public boolean Exists(String Name) throws LeSouException{
		//try {
			SoapObject request = service.getRequest("ExistsByName");
			request.addProperty("name", Name);
			Object response = service.getResponse("ExistsByName", request);
			Gson gson = LeSouShopService.getGson();
			return gson.fromJson(response.toString(), boolean.class);
		//} catch (Exception ex) {
		//	throw new LeSouException(LeSouExceptionType.EXCEPTION_UNKNOW,
		//			"Exception on DomainImpl!", "", ex);
		//}
	}

	public List<User> GetListWhere(String strWhere, String order, String asc)
			throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

	@Override
	public List<StoreBase> getFavorateStoreByUserID(UUID userid, Page page)
			throws LeSouException {
		// TODO Auto-generated method stub
		return DomainFactory.getStoreBaseDomain().getFavorateStoreByUserID(userid, page);
	}

	@Override
	public List<StoreBase> getFavorateStoreByUserID(UUID userid)
			throws LeSouException {
		// TODO Auto-generated method stub
		return DomainFactory.getStoreBaseDomain().getFavorateStoreByUserID(userid);
	}

	@Override
	public boolean FavoriteStoreBase(UUID userid, StoreBase sb)
			throws LeSouException {
		Favorite f = new Favorite(sb.getID(), userid, "", FavoriteType.STORE);
		return DomainFactory.getFavoriteDomain().Insert(f);
	}

	@Override
	public boolean UnFavoriteStoreBase(UUID userid, UUID sbid)
			throws LeSouException {
		String sql = "delete from T_Favorites where USER_ID = '"+userid+"' and For_ID='"+sbid+"' and type='"+FavoriteType.STORE+"'";
		return DomainFactory.getFavoriteDomain().QuerySql(sql);
	}

	@Override
	public boolean UnFavoriteStoreBase(UUID userid, List<UUID> sbids)
			throws LeSouException {
		String ids = "";
		for(UUID id:sbids){
			ids += "'"+id+"',";
		}
		if(!"".equals(ids)){
			ids = ids.substring(0,ids.length()-1);
			String sql = "delete from T_Favorites where USER_ID = '"+userid+"' and For_ID in ("+ids+") and type='"+FavoriteType.STORE+"'";
			return DomainFactory.getFavoriteDomain().QuerySql(sql);
		}
		return false;
	}

	@Override
	public long getFavoriteStoreCount(UUID userid) throws LeSouException {
		// TODO Auto-generated method stub
		IFavoriteDomain domain = DomainFactory.getFavoriteDomain();
		return domain.GetRecordCount(" where USER_ID = '"+userid+"' ");
	}

}

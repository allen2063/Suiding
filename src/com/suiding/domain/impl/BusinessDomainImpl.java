package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import org.ksoap2.serialization.SoapObject;

import com.google.gson.Gson;
import com.suiding.constant.ImageFolderEnum;
import com.suiding.domain.IBusinessDomain;
import com.suiding.domain.IUploadImage;
import com.suiding.model.Business;
import com.suiding.model.Page;
import com.suiding.model.Photo;
import com.suiding.service.DomainFactory;
import com.suiding.service.FileService;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.LeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class BusinessDomainImpl implements IBusinessDomain, IUploadImage {

	ILeSouShopService<Business> service = ServiceFactory
			.getBusinessesDAO();

	public boolean Insert(Business model) throws LeSouException {
		return service.Insert(model);
	}

	public boolean Update(Business model) throws LeSouException {
		return service.Update(model);
	}

	public boolean Delete(Business model) throws LeSouException {
		return service.Delete(model);
	}

	public Business GetByID(UUID id) throws LeSouException {
		return service.GetByID(id);
	}

	public List<Business> GetAll() throws LeSouException {
		return service.GetAll();
	}

	public boolean DeleteList(List<Business> models) throws LeSouException {
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

	public List<Business> GetListByPage(String strWhere, Page page)
			throws LeSouException {
		return service.GetListByPage(strWhere,page);
	}

	public Business getByUserID(UUID userID) throws LeSouException {
		Gson gson = LeSouShopService.getGson();
		try
		{
			SoapObject request = service.getRequest("GetByUserID");
			request.addProperty("userID", gson.toJson(userID));
			Object response = service.getResponse("GetByUserID", request);
			return gson.fromJson(response.toString(), Business.class);
		}
		catch(Exception ex)
		{
			return null;
		}
	}

	public String uploadHeadImg(String path) throws LeSouException {
		try {
			return FileService.uploadImage(path, ImageFolderEnum.HEAD_USER);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new LeSouException("上传头像时出错，该错误可能由于网络导致，请检查您的网络后重试。");
		}
	}

	public String uploadPhoto(String path) throws LeSouException {
		try {
			return FileService.uploadImage(path, ImageFolderEnum.PHOTO_USER);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new LeSouException("上传图片出错，该错误可能由于网络导致，请检查您的网络后重试。");
		}
	}
	
	public List<Business> GetListWhere(String strWhere, String order, String asc)
			throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

	public List<Photo> getListByUserID(Business business, Page page)
			throws LeSouException {
		return DomainFactory.getPhotoDomain().getListByUserID(business, page);
	}
}

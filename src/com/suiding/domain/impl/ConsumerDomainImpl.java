package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import org.ksoap2.serialization.SoapObject;

import com.google.gson.Gson;
import com.suiding.constant.ImageFolderEnum;
import com.suiding.domain.IConsumerDomain;
import com.suiding.model.Consumer;
import com.suiding.model.Page;
import com.suiding.model.Photo;
import com.suiding.service.DomainFactory;
import com.suiding.service.FileService;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.LeSouShopService;
import com.suiding.util.LeSouException;

public class ConsumerDomainImpl implements IConsumerDomain {

	ILeSouShopService<Consumer> Dao = new LeSouShopService<Consumer>(
			Consumer.class);

	public boolean Insert(Consumer model) throws LeSouException {

		return Dao.Insert(model);
	}

	public String uploadHeadImg(String path) throws LeSouException {
		try {
			return FileService.uploadImage(path, ImageFolderEnum.HEAD_USER);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new LeSouException(ex.getMessage());
		}
	}

	public String uploadPhoto(String path) throws LeSouException {
		try {
			return FileService.uploadImage(path, ImageFolderEnum.PHOTO_USER);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new LeSouException(ex.getMessage());
		}
	}

	@Override
	public boolean Update(Consumer model) throws LeSouException {
		// TODO Auto-generated method stub
		return Dao.Update(model);
	}

	@Override
	public boolean Delete(Consumer model) throws LeSouException {
		// TODO Auto-generated method stub
		return Dao.Delete(model);
	}

	@Override
	public Consumer GetByID(UUID id) throws LeSouException {
		// TODO Auto-generated method stub
		return Dao.GetByID(id);
	}

	@Override
	public List<Consumer> GetAll() throws LeSouException {
		// TODO Auto-generated method stub
		return Dao.GetAll();
	}

	@Override
	public boolean DeleteList(List<Consumer> models) throws LeSouException {
		// TODO Auto-generated method stub
		return Dao.DeleteList(models);
	}

	@Override
	public boolean DeleteListByID(List<UUID> ids) throws LeSouException {
		// TODO Auto-generated method stub
		return Dao.DeleteListByID(ids);
	}

	@Override
	public boolean DeleteByID(UUID id) throws LeSouException {
		// TODO Auto-generated method stub
		return Dao.DeleteByID(id);
	}

	@Override
	public long GetRecordCount(String strWhere) throws LeSouException {
		// TODO Auto-generated method stub
		return Dao.GetRecordCount(strWhere);
	}

	@Override
	public boolean Exists(UUID id) throws LeSouException {
		// TODO Auto-generated method stub
		return Dao.Exists(id);
	}

	@Override
	public List<Consumer> GetListByPage(String strWhere, Page page)
			throws LeSouException {
		// TODO Auto-generated method stub
		return Dao.GetListByPage(strWhere, page);
	}

	@Override
	public Consumer getByUserID(UUID userID) throws LeSouException {
		Gson gson = LeSouShopService.getGson();
		SoapObject request = Dao.getRequest("GetByUserID");
		request.addProperty("userID", gson.toJson(userID));
		Object response = Dao.getResponse("GetByUserID", request);
		return gson.fromJson(response.toString(), Consumer.class);
	}

	public List<Consumer> GetListWhere(String strWhere, String order, String asc)
			throws LeSouException {
		return Dao.GetListWhere(strWhere, order, asc);
	}

	public List<Photo> getListByUserID(Consumer consumer, Page page)
			throws LeSouException {
		return DomainFactory.getPhotoDomain().getListByUserID(consumer, page);
	}

	public String uploadHeadImg(Consumer consumer, String filepath) throws Exception {
		String newurl = FileService.uploadHeadImage(consumer.HeadImg, filepath, ImageFolderEnum.HEAD_USER);
		if(newurl!=null)
		{
			consumer.HeadImg = newurl;
			if(Dao.Update(consumer))
			{
				return newurl;
			}
		}
		throw new Exception("upload headimage fail,please try it again!");
	}

	public boolean registeredConsumer(Consumer consumer) throws LeSouException {
		// TODO Auto-generated method stub
		return this.Insert(consumer);
	}
}

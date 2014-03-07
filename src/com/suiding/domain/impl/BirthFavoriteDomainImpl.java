package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import com.suiding.constant.ImageFolderEnum;
import com.suiding.domain.IBirthFavoriteDomain;
import com.suiding.model.BirthFavorite;
import com.suiding.model.Page;
import com.suiding.service.FileService;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class BirthFavoriteDomainImpl implements IBirthFavoriteDomain {

	ILeSouShopService<BirthFavorite> service = ServiceFactory.getBirthFavoriteDAO();
	
	public boolean Insert(BirthFavorite model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(BirthFavorite model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(BirthFavorite model) throws LeSouException{
		return service.Delete(model);
	}

	public BirthFavorite GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<BirthFavorite> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<BirthFavorite> models) throws LeSouException{
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

	public List<BirthFavorite> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}
	
	public List<BirthFavorite> getListByUserID(UUID id, Page page)
			throws LeSouException {
		return this.GetListByPage(" where User_ID = '"+id+"' ", page);
	}

	public List<BirthFavorite> GetListWhere(String strWhere, String order,
			String asc) throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}
	
	public String uploadHeadImg(BirthFavorite bf, String filepath)
			throws Exception {
		String newurl = FileService.uploadHeadImage(bf.HeadImg, filepath, ImageFolderEnum.HEAD_USER);
		if(newurl!=null)
		{
			bf.HeadImg = newurl;
			if(service.Update(bf))
			{
				return newurl;
			}
		}
		throw new Exception("upload headimage fail,please try it again!");
	}

}
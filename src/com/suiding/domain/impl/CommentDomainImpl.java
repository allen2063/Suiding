package com.suiding.domain.impl;

import java.util.List;
import java.util.UUID;

import com.suiding.domain.ICommentDomain;
import com.suiding.model.Comment;
import com.suiding.model.Page;
import com.suiding.service.ILeSouShopService;
import com.suiding.service.ServiceFactory;
import com.suiding.util.LeSouException;

public class CommentDomainImpl implements ICommentDomain 
{
	ILeSouShopService<Comment> service = ServiceFactory.getCommentDAO();
	
	public boolean Insert(Comment model) throws LeSouException{
		return service.Insert(model);
	}

	public boolean Update(Comment model) throws LeSouException{
		return service.Update(model);
	}

	public boolean Delete(Comment model) throws LeSouException{
		return service.Delete(model);
	}

	public Comment GetByID(UUID id) throws LeSouException{
		return service.GetByID(id);
	}
	
	public List<Comment> GetAll() throws LeSouException{
		return service.GetAll();
	}

	public boolean DeleteList(List<Comment> models) throws LeSouException{
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

	public List<Comment> GetListByPage(String strWhere, Page page) throws LeSouException{
		return service.GetListByPage(strWhere, page);
	}
	
	public List<Comment> GetListByForID(UUID forID, Page page) throws LeSouException{
		return this.GetListByPage(" where For_ID = '"+forID+"'", page) ;
	}

	public List<Comment> GetListWhere(String strWhere, String order, String asc)
			throws LeSouException {
		return service.GetListWhere(strWhere, order, asc);
	}

}

package com.suiding.domain;

import java.util.List;

import com.suiding.model.Comment;
import com.suiding.model.Page;
import com.suiding.model.Product;
import com.suiding.model.RsrProduct;
import com.suiding.model.StoreBase;
import com.suiding.util.LeSouException;

public interface IRsrProductDomain extends IDomain<RsrProduct> {
	public List<Comment> GetAllCommentBy(RsrProduct product,Page page) throws LeSouException;
	List<com.suiding.model.RsrProduct> GetListByPbID(Product product,Page page) throws LeSouException;
	List<com.suiding.model.RsrProduct> GetListBySbID(StoreBase sb,Page page) throws LeSouException;
}

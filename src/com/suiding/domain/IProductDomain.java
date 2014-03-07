package com.suiding.domain;

import java.util.List;
import java.util.UUID;

import com.suiding.model.Page;
import com.suiding.model.Photo;
import com.suiding.model.Product;
import com.suiding.util.LeSouException;

public interface IProductDomain extends IDomain<Product> {
	List<com.suiding.model.Product> GetListBySbID(UUID sbID,Page page) throws LeSouException;
	List<Photo> getListByPbID(Product pb,Page page)throws LeSouException;
}

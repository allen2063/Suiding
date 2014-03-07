/**
 * 
 */
package com.suiding.domain;

import java.util.List;

import com.suiding.model.Comment;
import com.suiding.model.HotelProduct;
import com.suiding.model.Page;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.util.LeSouException;

/**
 * @author Administrator
 *
 */
public interface IHotelProductDomain extends IDomain<HotelProduct> {
	public List<Comment> GetAllCommentBy(HotelProduct product,Page page) throws LeSouException;
	List<com.suiding.model.HotelProduct> GetListByPbID(Product product,Page page) throws LeSouException;
	List<com.suiding.model.HotelProduct> GetListBySbID(StoreBase sb,Page page) throws LeSouException;
}

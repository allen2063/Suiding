/**
 * 
 */
package com.suiding.domain;

import java.util.List;

import com.suiding.model.Comment;
import com.suiding.model.Page;
import com.suiding.model.StallProduct;
import com.suiding.util.LeSouException;

/**
 * @author Administrator
 *
 */
public interface IStallProductDomain extends IDomain<StallProduct> {
	public List<Comment> GetAllCommentBy(StallProduct product,Page page) throws LeSouException;
	List<com.suiding.model.Product> GetListBySbID(StallProduct product,Page page) throws LeSouException;
}

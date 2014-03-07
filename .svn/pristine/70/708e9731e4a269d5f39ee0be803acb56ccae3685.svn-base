package com.suiding.domain;

import java.util.List;
import java.util.UUID;

import com.suiding.model.Consumer;
import com.suiding.model.Page;
import com.suiding.model.Photo;
import com.suiding.util.LeSouException;

public interface IConsumerDomain extends IDomain<Consumer>,IUploadImage {
	Consumer getByUserID(UUID userID) throws LeSouException;
	List<Photo> getListByUserID(Consumer consumer,Page page)throws LeSouException;
	String uploadHeadImg(Consumer consumer,String filepath)  throws Exception ;
	boolean registeredConsumer(Consumer consumer)throws LeSouException;
}

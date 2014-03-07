package com.suiding.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.suiding.model.Page;
import com.suiding.model.Twitter;
import com.suiding.util.LeSouException;

public interface ITwitterDomain extends IDomain<Twitter> {
	List<Twitter> getListBySbID(UUID sbid,Page page) throws LeSouException;
	List<Twitter> getListBySbIDAfter(UUID sbid,Date date) throws LeSouException;
	List<Twitter> getListBySbIDBefore(UUID sbid,Date date) throws LeSouException;
	List<Twitter> getListBySbIDBettwen(UUID sbid,Date begin,Date end) throws LeSouException;
	List<Twitter> getListByUserID(UUID userid,Page page) throws LeSouException;
}
package com.suiding.domain;

import java.util.List;

import com.suiding.model.Area;
import com.suiding.model.Page;
import com.suiding.util.LeSouException;

public interface IAreaDomain {
	List<Area> getAllCity()  throws LeSouException;
	Area getCityByNameLike(String name)  throws LeSouException;
	Area getAreaByNameLike(String name)  throws LeSouException;
	Area getCityByName(String name)  throws LeSouException;
	Area getAreaByName(String name)  throws LeSouException;
	Area getAreaByID(int id)  throws LeSouException;
	List<Area> getChildreByPid(int pid) throws LeSouException ;
	List<Area> getAllAreaOderByID() throws LeSouException ;
	List<Area> getAllAreaOderByID(Page page) throws LeSouException ;
}

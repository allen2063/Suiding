package com.suiding.service;

import java.util.List;
import java.util.UUID;

import org.ksoap2.serialization.SoapObject;

import com.suiding.model.Page;
import com.suiding.util.LeSouException;
 
public interface ILeSouShopService<T> {
    boolean Insert(T entity) throws LeSouException;
    boolean Update(T entity) throws LeSouException;
    boolean Delete(T entity) throws LeSouException;
    T GetByID(UUID uuid) throws LeSouException;
    List<T> GetAll() throws LeSouException;
    boolean DeleteList(List<T> entities) throws LeSouException;
    boolean DeleteListByID(List<UUID> ids) throws LeSouException;
    boolean DeleteByID(UUID id) throws LeSouException;
    long GetRecordCount(String strWhere) throws LeSouException;
    boolean Exists(UUID id) throws LeSouException;
    List<T> GetListByPage(String strWhere ,Page page) throws LeSouException;
    List<T> GetListWhere(String strWhere, String order, String asc) throws LeSouException;
    SoapObject getRequest(String shortmothedname);
    Object getResponse(String shortmothedname, SoapObject request) throws LeSouException;
    List<T> getList(String mothedname,String[][] properties)throws LeSouException;
}	

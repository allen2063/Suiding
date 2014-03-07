package com.suiding.domain;

import java.util.List;
import java.util.UUID;

import com.suiding.model.Page;
import com.suiding.util.LeSouException;

public interface IDomain<T> {
    boolean Insert(T model) throws LeSouException;
    boolean Update(T model) throws LeSouException;
    boolean Delete(T model)throws LeSouException;
    T GetByID(UUID id)throws LeSouException;
    List<T> GetAll()throws LeSouException;
    boolean DeleteList(List<T> models)throws LeSouException;
    boolean DeleteListByID(List<UUID> ids)throws LeSouException;
    boolean DeleteByID(UUID id) throws LeSouException;
    long GetRecordCount(String strWhere)throws LeSouException;
    boolean Exists(UUID id) throws LeSouException;
    List<T> GetListByPage(String strWhere ,Page page) throws LeSouException;
    List<T> GetListWhere(String strWhere, String order, String asc) throws LeSouException;
}

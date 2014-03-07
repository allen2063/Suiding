package com.suiding.domain;

import java.util.List;
import java.util.UUID;

import com.suiding.model.Order;
import com.suiding.model.Page;
import com.suiding.util.LeSouException;

public interface IOrderDomain {
    boolean Update(Order model) throws LeSouException;
    boolean Delete(Order model)throws LeSouException;
    Order GetByID(UUID id)throws LeSouException;
    List<Order> GetAll()throws LeSouException;
    boolean DeleteList(List<Order> models)throws LeSouException;
    boolean DeleteListByID(List<UUID> ids)throws LeSouException;
    boolean DeleteByID(UUID id) throws LeSouException;
    long GetRecordCount(String strWhere)throws LeSouException;
    boolean Exists(UUID id) throws LeSouException;
    List<Order> GetListByPage(String strWhere ,Page page) throws LeSouException;
    List<Order> GetListWhere(String strWhere, String order, String asc) throws LeSouException;
	
    /// <summary>
    /// ��ȡĳ���û����ڵȴ��Ķ�������
	/// �ڶ�ȡ����ʱ������� �й��ڵ� ���� ���Զ�תΪ����
    /// </summary>
	long CountWaitOrdersByUser(UUID userid)throws LeSouException;
    /// <summary>
    /// ��ȡĳ���û����ڵȴ��Ķ����б� 
    /// �ڶ�ȡ����ʱ������� ���� ���Զ�תΪ����
    /// </summary>
    List<Order> GetWaitOrdersByUser(UUID userid)throws LeSouException;
    /// <summary>
    /// �ӷ��������� ltorder ��״̬������
    /// </summary>
    List<Order> GetOrdersStatus(List<Order> ltorder)throws LeSouException;
    
	long GetCountByStatus(UUID userid, int status)throws LeSouException;
	long GetCountByFilters(UUID userid, int[] mFilters)throws LeSouException;
	List<Order> GetListByStatus(UUID userid, int status, Page page)throws LeSouException;
	List<Order> GetListByFilters(UUID id, int[] mFilters, Page page)throws LeSouException;
}


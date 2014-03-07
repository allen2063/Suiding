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
    /// 获取某个用户正在等待的订单个数
	/// 在读取数据时如果发现 有过期的 订单 将自动转为过期
    /// </summary>
	long CountWaitOrdersByUser(UUID userid)throws LeSouException;
    /// <summary>
    /// 获取某个用户正在等待的订单列表 
    /// 在读取数据时如果发现 订单 将自动转为过期
    /// </summary>
    List<Order> GetWaitOrdersByUser(UUID userid)throws LeSouException;
    /// <summary>
    /// 从服务器更新 ltorder 的状态和内容
    /// </summary>
    List<Order> GetOrdersStatus(List<Order> ltorder)throws LeSouException;
    
	long GetCountByStatus(UUID userid, int status)throws LeSouException;
	long GetCountByFilters(UUID userid, int[] mFilters)throws LeSouException;
	List<Order> GetListByStatus(UUID userid, int status, Page page)throws LeSouException;
	List<Order> GetListByFilters(UUID id, int[] mFilters, Page page)throws LeSouException;
}


package com.suiding.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.suiding.dao.framework.Cacheable;
import com.suiding.dao.framework.StoreBaseDao;
import com.suiding.entity.FavoriteEntity;
import com.suiding.entity.OrderEntity;
import com.suiding.model.Consumer;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;

public class OrderEntityDao extends StoreBaseDao implements
		Cacheable<List<OrderEntity>> {

	public OrderEntityDao(Context context) {
		// TODO Auto-generated method stub
		super(context, OrderEntity.class);
	}

	public void add(OrderEntity obj) {
		// TODO Auto-generated method stub
		super.add(obj);
	}

	public boolean hasOrderFinishFor(Consumer user, StoreBase store) {
		// TODO Auto-generated method stub
		if(user == null || store == null){
			return false;
		}
		StringBuilder builder = new StringBuilder("User_ID='");
		builder.append(user.getID());
		builder.append("' and SB_ID='");
		builder.append(store.getID());
		builder.append("'");
		Cursor cur = super.getWhere("ID",builder.toString(), 1, 0);
		return cur.moveToNext();
	}

	public boolean hasOrderFinishFor(Consumer user, Product product) {
		// TODO Auto-generated method stub
		if(user == null || product == null){
			return false;
		}
		StringBuilder builder = new StringBuilder("User_ID='");
		builder.append(user.getID());
		builder.append("' and Pb_ID='");
		builder.append(product.getID());
		builder.append("'");
		Cursor cur = super.getWhere("ID",builder.toString(), 1, 0);
		return cur.moveToNext();
	}
	/**
	 * 检测订单
	 */
	public boolean exist(OrderEntity entity) {
		// TODO Auto-generated method stub
		Cursor cur = super.getWhere("ID", "ID='" + entity.ID + "'", 1, 0);
		return cur.moveToNext();
	}

	/**
	 * 删除订单
	 */
	public void remove(FavoriteEntity entity) {
		// TODO Auto-generated method stub
		super.delWhere("ID='" + entity.ID + "'");
	}

	/**
	 * 获取全部
	 * 
	 * @return
	 */
	public List<OrderEntity> getAll() {
		// TODO Auto-generated method stub
		Cursor cur = getAll("*");
		List<OrderEntity> ltEntity = new ArrayList<OrderEntity>();
		while (cur.moveToNext()) {
			ltEntity.add(getEntityFromCursor(cur));
		}
		return ltEntity;
	}

	/**
	 * 获取全部
	 * @param status
	 * @return
	 */
	public List<OrderEntity> getAll(int status) {
		// TODO Auto-generated method stub
		Cursor cur = getWhere("*","OrderStatus="+status);
		List<OrderEntity> ltEntity = new ArrayList<OrderEntity>();
		while (cur.moveToNext()) {
			ltEntity.add(getEntityFromCursor(cur));
		}
		return ltEntity;
	}
	/**
	 * 从Cursor中构造所有字段 返回 Entity
	 * 
	 * @param cur
	 * @return
	 */
	private OrderEntity getEntityFromCursor(Cursor cur) {
		// TODO Auto-generated method stub
		OrderEntity tEntity = new OrderEntity();
		tEntity.ID = mHelper.getUUID(cur, "ID");
		tEntity.Pb_ID = mHelper.getUUID(cur, "Pb_ID");
		tEntity.Cp_ID = mHelper.getUUID(cur, "Cp_ID");
		tEntity.User_ID = mHelper.getUUID(cur, "User_ID");

		tEntity.OrderStatus = mHelper.getInt(cur, "OrderStatus");

		tEntity.Date = mHelper.getDate(cur, "Date");
		tEntity.Count = mHelper.getInt(cur, "Count");
		tEntity.Phone = mHelper.getString(cur, "Phone");

		tEntity.NowTotalPrice = mHelper.getDouble(cur,"NowTotalPrice");
		tEntity.OldTotalPrice = mHelper.getDouble(cur,"OldTotalPrice");
		tEntity.ArrivalTime = mHelper.getDate(cur, "ArrivalTime");
		tEntity.PeopleNumber = mHelper.getInt(cur, "PeopleNumber");

		tEntity.PB_Name = mHelper.getString(cur, "PB_Name");
		tEntity.PB_HeadImg = mHelper.getString(cur, "PB_HeadImg");
		tEntity.PB_Abstrct = mHelper.getString(cur, "PB_Abstrct");
		tEntity.PB_Price = mHelper.getInt(cur, "PB_Price");
		tEntity.PB_OriginalPrice = mHelper.getInt(cur, "PB_OriginalPrice");

		tEntity.PB_Discount = mHelper.getInt(cur, "CP_Discount");
		tEntity.PB_ProductCount = mHelper.getInt(cur, "CP_ProductCount");
		tEntity.PB_BookedCount = mHelper.getInt(cur, "CP_BookedCount");
		tEntity.PB_NoBookedCount = mHelper.getInt(cur, "CP_NoBookedCount");

		tEntity.PB_Galleryful = mHelper.getString(cur, "CP_Galleryful");
		tEntity.PB_HasToilet = mHelper.getBoolean(cur, "CP_HasToilet");
		
		tEntity.CP_Name = mHelper.getString(cur, "CP_Name");
		tEntity.CP_Content = mHelper.getString(cur, "CP_Content");
		tEntity.CP_Discount = mHelper.getInt(cur, "CP_Discount");
		tEntity.CP_For_ID = mHelper.getUUID(cur, "CP_For_ID");
		tEntity.CP_Type_ID = mHelper.getUUID(cur, "CP_Type_ID");
		tEntity.CP_Remark = mHelper.getString(cur, "CP_Remark");
		tEntity.CP_Date = mHelper.getDate(cur, "CP_Date");
		tEntity.CP_BegDate = mHelper.getDate(cur, "CP_BegDate");
		tEntity.CP_EndDate = mHelper.getDate(cur, "CP_EndDate");

		super.getEntityFromCursor(cur, tEntity);
		return tEntity;
	}

	@Override
	public void updateCache(List<OrderEntity> ltEntity) {
		// TODO Auto-generated method stub
		super.delAll();
		this.appendCache(ltEntity);
	}

	public void updateCache(List<OrderEntity> ltEntity,int status) {
		// TODO Auto-generated method stub
		super.delWhere("OrderStatus="+status);
		this.appendCache(ltEntity);
	}

	public void updateCache(List<OrderEntity> ltEntity,int[] status) {
		// TODO Auto-generated method stub
		if(status == null || status.length == 0){
			super.delAll();
		}else{
	        StringBuilder builder = new StringBuilder("OrderStatus in (");
			for (int i = 0; i < status.length; i++) {
				if(i > 0){
			        builder.append(',');
				}
		        builder.append(status[i]);
			}
	        builder.append(')');
			super.delWhere(builder.toString());
		}
		this.appendCache(ltEntity);
	}

	@Override
	public void appendCache(List<OrderEntity> ltEntity) {
		// TODO Auto-generated method stub
		for (OrderEntity entity : ltEntity) {
			super.add(entity);
		}
	}

	public void updateOrderStatus(List<OrderEntity> ltEntity) {
		// TODO Auto-generated method stub
		for (OrderEntity entity : ltEntity) {
			super.update(entity, "ID='"+entity.ID+"'");
		}
	}

	public void updateOrderStatus(OrderEntity entity) {
		// TODO Auto-generated method stub
		super.update(entity, "ID='"+entity.ID+"'");
	}

}

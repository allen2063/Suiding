package com.suiding.dao.framework;

import java.lang.reflect.Field;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.suiding.application.AppExceptionHandler;
import com.suiding.database.DatabaseOpenHelper;

public abstract class DaoBase {
	protected Class<?> mTable;
	protected SQLiteDatabase mDatabase = null;
	protected DatabaseOpenHelper mHelper = null;
	protected String mTableName;
	protected String mTableColumns;
	protected String mTableValues;

	public DaoBase(Context context, Class<?> table) {
		this.mTable = table;
		this.mTableName = 't' + table.getSimpleName();
		this.mHelper = DatabaseOpenHelper.getInstance(context);
		this.Initialized(table);
	}

	private void Initialized(Class<?> table) {
		boolean isfrist = true;
		StringBuilder tValues = new StringBuilder();
		StringBuilder tColumns = new StringBuilder();
		for (Field field : mHelper.getFields(table)) {
			if (isfrist == false) {
				tValues.append(',');
				tColumns.append(',');
			} else {
				isfrist = false;
			}
			tValues.append('?');
			tColumns.append(field.getName());
		}
		mTableValues = tValues.toString();
		mTableColumns = tColumns.toString();
	}

	private String update(Object obj) throws Exception {
		boolean isfrist = true;
		StringBuilder tColumns = new StringBuilder();
		for (Field field : mHelper.getFields(mTable)) {
			if (isfrist == false) {
				tColumns.append(',');
			} else {
				isfrist = false;
			}
			tColumns.append(setValue(obj, field));
		}
		mTableColumns = tColumns.toString();
		return tColumns.toString();
	}

	private String setValue(Object obj, Field field) throws Exception {
		// TODO Auto-generated method stub
		Object data = field.get(obj);
		Class<?> tType = field.getType();
		if (tType.equals(Date.class)) {
			Date tDate = (Date) data;
			return field.getName() + "=" + tDate.getTime();
		} else if (tType.equals(Integer.class) || tType.equals(int.class)
				|| tType.equals(Short.class) || tType.equals(short.class)
				|| tType.equals(Long.class) || tType.equals(long.class)
				|| tType.equals(Float.class) || tType.equals(float.class)
				|| tType.equals(Double.class) || tType.equals(double.class)) {
			return field.getName() + "=" + data + "";
		} else if (tType.equals(Boolean.class) || tType.equals(boolean.class)) {
			Boolean tBoolean = (Boolean) data;
			if (tBoolean == null) {
				tBoolean = false;
			}
			return field.getName() + "='" + (tBoolean ? 1 : 0) + "'";
		} else {
			return field.getName() + "='" + data + "'";
		}
	}

	public final void close() {
		// TODO Auto-generated method stub
		synchronized(mHelper){
			mHelper.close();
		}
	}

	/**
	 * 添加一条记录
	 * 
	 * @param obj
	 * @return
	 */
	public final boolean add(Object obj) {
		synchronized(mHelper){
		if (obj.getClass().equals(mTable)) {
			StringBuilder tStringBuilder = new StringBuilder("insert into ");
			tStringBuilder.append(mTableName);
			tStringBuilder.append(" (");
			tStringBuilder.append(mTableColumns);
			tStringBuilder.append(") values(");
			tStringBuilder.append(mTableValues);
			tStringBuilder.append(")");

			Object[] tObjects = mHelper.getObjectFromFields(mTable, obj);
			mDatabase = mHelper.getWritableDatabase();
			mDatabase.execSQL(tStringBuilder.toString(), tObjects);
			return true;
		}
		return false;
		}
	}

	/**
	 * 更新一条记录
	 * 
	 * @param obj
	 * @return
	 */
	public final boolean update(Object obj, String where) {
		synchronized(mHelper){
		try {
			if (obj.getClass().equals(mTable)) {
				StringBuilder tStringBuilder = new StringBuilder("update ");
				tStringBuilder.append(mTableName);
				tStringBuilder.append(" set ");
				tStringBuilder.append(update(obj));
				tStringBuilder.append(" where ");
				tStringBuilder.append(where);
				// UPDATE Person SET Address = 'Zhongshan 23', City = 'Nanjing'
				// WHERE LastName = 'Wilson'
				mDatabase = mHelper.getWritableDatabase();
				mDatabase.execSQL(tStringBuilder.toString());
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();//handled
			AppExceptionHandler.handler(e, "DaoBase，update("+obj+","+where+") 出现异常");
		}
		return false;
		}
	}

	/**
	 * 统计 全部 数据条数
	 * 
	 * @param column
	 * @return
	 */
	public final int getCount() {
		synchronized(mHelper){
		mDatabase = mHelper.getReadableDatabase();
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) from ");
		sql.append(mTableName);
		Cursor cur = mDatabase.rawQuery(sql.toString(), null);
		if (cur.moveToNext()) {
			return cur.getInt(0);
		}
		return 0;
		}
	}

	/**
	 * 统计条件符合where 的 数据条数
	 * 
	 * @param column
	 * @return
	 */
	protected final int getCount(String where) {
		synchronized(mHelper){
		mDatabase = mHelper.getReadableDatabase();
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) from ");
		sql.append(mTableName);
		sql.append(" where ");
		sql.append(where);
		Cursor cur = mDatabase.rawQuery(sql.toString(), null);
		if (cur.moveToNext()) {
			return cur.getInt(0);
		}
		return 0;
		}
	}

	/**
	 * 删除所有
	 */
	public final void delAll() {
		// TODO Auto-generated method stub
		synchronized(mHelper){
		mDatabase = mHelper.getWritableDatabase();
		mDatabase.execSQL("delete from " + mTableName);
		}
	}

	/**
	 * 删除符合Where条件的数据
	 * 
	 * @param where
	 */
	protected final void delWhere(String where) {
		// TODO Auto-generated method stub
		synchronized(mHelper){
		mDatabase = mHelper.getWritableDatabase();
		mDatabase.execSQL("delete from " + mTableName + " where " + where);
		}
	}

	/**
	 * 分页查询
	 * 
	 * @param column
	 * @param num
	 * @param offset
	 * @return
	 */
	protected final Cursor getLimit(String column, int num, int offset) {
		synchronized(mHelper){
		mDatabase = mHelper.getReadableDatabase();
		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append(column);
		sql.append(" from ");
		sql.append(mTableName);
		sql.append(" limit ");
		sql.append(num);
		sql.append(" offset ");
		sql.append(offset);
		return mDatabase.rawQuery(sql.toString(), null);
		}
	}

	/**
	 * 分页查询 带排序
	 * 
	 * @param column
	 * @param order
	 * @param num
	 * @param offset
	 * @return
	 */
	protected final Cursor getLimit(String column, String order, int num,
			int offset) {
		synchronized(mHelper){
		mDatabase = mHelper.getReadableDatabase();
		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append(column);
		sql.append(" from ");
		sql.append(mTableName);
		sql.append(" order by ");
		sql.append(order);
		sql.append(" limit ");
		sql.append(num);
		sql.append(" offset ");
		sql.append(offset);
		return mDatabase.rawQuery(sql.toString(), null);
		}
	}

	/**
	 * 分页查询 带排序 条件
	 * 
	 * @param column
	 * @param where
	 * @param order
	 * @param num
	 * @param offset
	 * @return
	 */
	protected final Cursor getLimit(String column, String where, String order,
			int num, int offset) {
		synchronized(mHelper){
		mDatabase = mHelper.getReadableDatabase();
		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append(column);
		sql.append(" from ");
		sql.append(mTableName);
		sql.append(" where ");
		sql.append(where);
		sql.append(" order by ");
		sql.append(order);
		sql.append(" limit ");
		sql.append(num);
		sql.append(" offset ");
		sql.append(offset);
		return mDatabase.rawQuery(sql.toString(), null);
		}
	}

	/**
	 * 条件查询 带分页
	 * 
	 * @param column
	 * @param where
	 * @param num
	 * @param offset
	 * @return
	 */
	protected final Cursor getWhere(String column, String where, int num,
			int offset) {
		synchronized(mHelper){
		mDatabase = mHelper.getReadableDatabase();
		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append(column);
		sql.append(" from ");
		sql.append(mTableName);
		sql.append(" where ");
		sql.append(where);
		sql.append(" limit ");
		sql.append(num);
		sql.append(" offset ");
		sql.append(offset);
		return mDatabase.rawQuery(sql.toString(), null);
		}
	}

	/**
	 * 条件查询
	 * 
	 * @param column
	 * @param where
	 * @param num
	 * @param offset
	 * @return
	 */
	protected final Cursor getWhere(String column, String where) {
		synchronized(mHelper){
		mDatabase = mHelper.getReadableDatabase();
		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append(column);
		sql.append(" from ");
		sql.append(mTableName);
		sql.append(" where ");
		sql.append(where);
		return mDatabase.rawQuery(sql.toString(), null);
		}
	}

	/**
	 * 获取全部
	 * 
	 * @param column
	 * @return
	 */
	protected final Cursor getAll(String column) {
		synchronized(mHelper){
		mDatabase = mHelper.getReadableDatabase();
		return mDatabase.rawQuery("select " + column + " from " + mTableName,
				null);
		}
	}

	/**
	 * 获取全部
	 * 
	 * @param column
	 * @return
	 */
	protected final Cursor getAll(String column, String order) {
		synchronized(mHelper){
		mDatabase = mHelper.getReadableDatabase();
		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append(column);
		sql.append(" from ");
		sql.append(mTableName);
		sql.append(" order by ");
		sql.append(order);
		return mDatabase.rawQuery(sql.toString(), null);
		}
	}
}

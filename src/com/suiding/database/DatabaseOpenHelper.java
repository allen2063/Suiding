package com.suiding.database;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.UUID;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.suiding.application.AppExceptionHandler;
import com.suiding.entity.AddressEntity;
import com.suiding.entity.AreaEntity;
import com.suiding.entity.BirthdayEntity;
import com.suiding.entity.IndustryEntity;
import com.suiding.entity.InvitationEntity;
import com.suiding.entity.ClubEntity;
import com.suiding.entity.CouponEntity;
import com.suiding.entity.FavoriteEntity;
import com.suiding.entity.HotelEntity;
import com.suiding.entity.ImageCache;
import com.suiding.entity.KTVEntity;
import com.suiding.entity.LocationEntity;
import com.suiding.entity.NearbyEntity;
import com.suiding.entity.NewsEntity;
import com.suiding.entity.OrderEntity;
import com.suiding.entity.PhotoEntity;
import com.suiding.entity.RestaurantEntity;
import com.suiding.entity.StallEntity;
import com.suiding.entity.StoreTypeEntity;
import com.suiding.entity.TwitterEntity;
import com.suiding.entity.framework.VersionEntity;
import com.suiding.util.UUIDUtil;


public class DatabaseOpenHelper extends SQLiteOpenHelper{

    //系统SQLite版本
    public static final int VERSION = 3;
    //自定义数据库关系版本
    public static final int DATABASE_VERSION = 7;
    //数据库文件名
    public static final String DBNAME = "data.db";
    
    private static DatabaseOpenHelper mHelper = null;
    
    public static final Class<?>[] Tables = new  Class<?>[]{
        VersionEntity.class,
        ImageCache.class,
        RestaurantEntity.class,
        HotelEntity.class,
        NewsEntity.class,
        StallEntity.class,
        AddressEntity.class,
        KTVEntity.class,
        ClubEntity.class,
        AreaEntity.class,
        LocationEntity.class,
        PhotoEntity.class,
        InvitationEntity.class,
        NearbyEntity.class,
        FavoriteEntity.class,
        TwitterEntity.class,
        CouponEntity.class,
        BirthdayEntity.class,
        OrderEntity.class,
        IndustryEntity.class,
        StoreTypeEntity.class
    };
    
    public Context mContext = null;
    
    private DatabaseOpenHelper(Context context) {
        super(context, DBNAME, null, VERSION);
        // TODO Auto-generated constructor stub
        this.mContext = context;
    }
    

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        for(Class<?> tclass : Tables)
        {
            createTable(tclass,db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        
    }

    private void createTable(Class<?> tclass,SQLiteDatabase db)
    {
        boolean isfrist = true;
        StringBuilder tStringBuilder = new StringBuilder("create table t");     
        tStringBuilder.append(tclass.getSimpleName());     
        tStringBuilder.append(" (");
        for(Field field : tclass.getFields())
        {
            if(isfrist == false)
            {
                tStringBuilder.append(',');
            }
            isfrist = false;
            Class<?> type = field.getType();
            String name = field.getName();
            tStringBuilder.append(name);
            tStringBuilder.append(' ');
            if(type.equals(Date.class)
                    || type.equals(int.class)
                    || type.equals(Long.class)
                    || type.equals(long.class)
                    || type.equals(Boolean.class)
                    || type.equals(boolean.class)
                    || type.equals(Integer.class))
            {
                tStringBuilder.append("integer");
            }
            else if(type.equals(float.class)
                    || type.equals(Float.class))
            {
                tStringBuilder.append("float");
            }
            else if(type.equals(double.class)
                    || type.equals(Double.class))
            {
                tStringBuilder.append("double");
            }
            else if(type.equals(UUID.class))
            {
                tStringBuilder.append("text");
            }
            else{
                tStringBuilder.append("text");
            }
            if(name.equals("ID"))
            {
                tStringBuilder.append(" NOT NULL PRIMARY KEY");
            }
        }     
        tStringBuilder.append(')');
        try {
        	db.execSQL(tStringBuilder.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();//handled
			AppExceptionHandler.handler(e, "DatabaseOpenHelper，createTable 出现异常");
		}
    }


    public Object[] getObjectFromFields(Class<?> tclass, Object obj)
    {
        // TODO Auto-generated method stub
        Field[] tFields = tclass.getFields();
        Object[] tObjects = new Object[tFields.length];
        try
        {
            for(int i = 0 ; i < tFields.length ; i++)
            {
                Field tField = tFields[i];
                Class<?> tType = tField.getType();
                if(tType.equals(Date.class))
                {
                    Date tDate = (Date)tField.get(obj);
                    if(tDate == null){
                        tDate = new Date();
                    }
                    tObjects[i] = tDate.getTime();
                }
                else if(tType.equals(UUID.class))
                {
                    UUID tUUID = (UUID)tField.get(obj);
                    if(tUUID == null){
                        tUUID = UUIDUtil.Empty;
                    }
                    tObjects[i] = tUUID.toString();
                }
                else if(tType.equals(Boolean.class)
                        ||tType.equals(boolean.class))
                {
                    Boolean tBoolean = (Boolean)tField.get(obj);
                    if(tBoolean == null){
                        tBoolean = false;
                    }
                    tObjects[i] = tBoolean?1:0;
                }
                else
                {
                    tObjects[i] = tField.get(obj);
                }
            }
        }
        catch (IllegalArgumentException e)
        {
            // TODO Auto-generated catch block
			e.printStackTrace();//handled
			AppExceptionHandler.handler(e, "DatabaseOpenHelper，getObjectFromFields 出现异常");
        }
        catch (IllegalAccessException e)
        {
            // TODO Auto-generated catch block
			e.printStackTrace();//handled
			AppExceptionHandler.handler(e, "DatabaseOpenHelper，getObjectFromFields 出现异常");
        }
        return tObjects;
    }

    public boolean getBoolean(Cursor cur, String column)
    {
        // TODO Auto-generated method stub
        int index = cur.getColumnIndex(column);
        if(index < 0){
            return false;
        }
        return cur.getLong(index) == 1;
    }

    public int getInt(Cursor cur, String column)
    {
        // TODO Auto-generated method stub
        int index = cur.getColumnIndex(column);
        if(index < 0){
            return 0;
        }
        return cur.getInt(index);
    }

    public long getLong(Cursor cur, String column)
    {
        // TODO Auto-generated method stub
        int index = cur.getColumnIndex(column);
        if(index < 0){
            return 0;
        }
        return cur.getLong(index);
    }

    public float getFloat(Cursor cur, String column)
    {
        // TODO Auto-generated method stub
        int index = cur.getColumnIndex(column);
        if(index < 0){
            return 0;
        }
        return cur.getFloat(index);
    }

    public double getDouble(Cursor cur, String column)
    {
        // TODO Auto-generated method stub
        int index = cur.getColumnIndex(column);
        if(index < 0){
            return 0;
        }
        return cur.getDouble(index);
    }

    public String getString(Cursor cur, String column)
    {
        // TODO Auto-generated method stub
        int index = cur.getColumnIndex(column);
        if(index < 0){
            return "";
        }
        return cur.getString(index);
    }
    
    public UUID getUUID(Cursor cur, String column)
    {
        // TODO Auto-generated method stub
        int index = cur.getColumnIndex(column);
        if(index < 0){
            return UUID.fromString("00000000-0000-0000-0000-000000000000");
        }
        return UUID.fromString(cur.getString(index));
    }


    public Date getDate(Cursor cur, String column)
    {
        // TODO Auto-generated method stub
        int index = cur.getColumnIndex(column);
        if(index < 0){
            return new Date(0);
        }
        return new Date(cur.getLong(index));
    }

    @Override
    public synchronized void close()
    {
        // TODO Auto-generated method stub
        if(mHelper == this){
            mHelper = null;
        }
        super.close();
    }

    public Field[] getFields(Class<?> table)
    {
        // TODO Auto-generated method stub
        return table.getFields();
    }

    public static DatabaseOpenHelper getInstance(Context context)
    {
        // TODO Auto-generated method stub
        if(mHelper == null){
            mHelper = new DatabaseOpenHelper(context);
        }
        return mHelper;
    }



    
}


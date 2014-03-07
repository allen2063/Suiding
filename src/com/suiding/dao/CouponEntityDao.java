package com.suiding.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.suiding.dao.framework.Cacheable;
import com.suiding.dao.framework.StoreEntityDao;
import com.suiding.entity.CouponEntity;


public class CouponEntityDao extends StoreEntityDao implements
        Cacheable<List<CouponEntity>>
{

    public CouponEntityDao(Context context)
    {
        // TODO Auto-generated method stub
        super(context, CouponEntity.class);
    }

    public void add(CouponEntity obj)
    {
        // TODO Auto-generated method stub
        super.add(obj);
    }

    @Override
    public void updateCache(List<CouponEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        super.delAll();
        this.appendCache(ltEntity);
    }

    @Override
    public void appendCache(List<CouponEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        for (CouponEntity entity : ltEntity)
        {
            super.add(entity);
        }
    }
    /**
     * 获取全部
     * @return
     */
    public List<CouponEntity> getAll()
    {
        // TODO Auto-generated method stub
        Cursor cur = getAll("*");
        List<CouponEntity> ltEntity = new ArrayList<CouponEntity>();
        while (cur.moveToNext())
        {
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
    private CouponEntity getEntityFromCursor(Cursor cur)
    {
        // TODO Auto-generated method stub
        CouponEntity tEntity = new CouponEntity();

        //Coupon 属性
        tEntity.ID = mHelper.getUUID(cur,"ID");
        tEntity.Coupon_Discount = mHelper.getInt(cur,"Coupon_Discount");
        tEntity.Coupon_For_ID = mHelper.getUUID(cur,"Coupon_For_ID");
        tEntity.Coupon_Pb_ID = mHelper.getUUID(cur,"Coupon_Pb_ID");
        tEntity.Coupon_Type_ID = mHelper.getUUID(cur,"Coupon_Type_ID");
        tEntity.Coupon_Name = mHelper.getString(cur,"Coupon_Name");
        tEntity.Coupon_Content = mHelper.getString(cur,"Coupon_Content");
        tEntity.Coupon_Remark = mHelper.getString(cur,"Coupon_Remark");
        tEntity.Coupon_Date = mHelper.getDate(cur,"Coupon_Date");
        tEntity.Coupon_BegDate = mHelper.getDate(cur,"Coupon_BegDate");
        tEntity.Coupon_EndDate = mHelper.getDate(cur,"Coupon_EndDate");
        tEntity.OrderNumber = mHelper.getInt(cur,"OrderNumber");
        tEntity.JsonPhotos = mHelper.getString(cur,"JsonPhotos");

        //Product 属性
        tEntity.Product_ID = mHelper.getUUID(cur,"Product_ID");
        tEntity.Product_Discount = mHelper.getInt(cur,"Product_Discount");
        tEntity.Product_Name = mHelper.getString(cur,"Product_Name");
        tEntity.Product_HeadImg = mHelper.getString(cur,"Product_HeadImg");
        tEntity.Product_Abstrct = mHelper.getString(cur,"Product_Abstrct");
        tEntity.Product_Price = mHelper.getDouble(cur,"Product_Price");

        tEntity.Product_NowPrice = mHelper.getDouble(cur,"Product_NowPrice");
        tEntity.Product_ProductCount = mHelper.getInt(cur,"Product_ProductCount");
        tEntity.Product_BookedCount = mHelper.getInt(cur,"Product_BookedCount");
        tEntity.Product_NoBookedCount = mHelper.getInt(cur,"Product_NoBookedCount");

        tEntity.Product_Galleryful = mHelper.getString(cur,"Product_Galleryful");
        tEntity.Product_HasToilet = mHelper.getBoolean(cur,"Product_HasToilet");
        
        super.getEntityFromCursor(cur, tEntity);
        return tEntity;
    }

}

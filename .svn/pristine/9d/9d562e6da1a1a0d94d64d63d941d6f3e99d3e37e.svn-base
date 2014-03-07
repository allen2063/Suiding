package com.suiding.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.suiding.application.AppExceptionHandler;
import com.suiding.entity.framework.EntityBase;
import com.suiding.entity.framework.StoreBaseEntity;
import com.suiding.model.Coupon;
import com.suiding.model.Photo;
import com.suiding.model.Product;
import com.suiding.util.UUIDUtil;

public class CouponEntity extends StoreBaseEntity implements EntityBase<Coupon>
{
    //Coupon 属性
    public UUID ID = UUID.randomUUID();
    public int Coupon_Discount ;
    public UUID Coupon_For_ID = UUIDUtil.Empty;
    public UUID Coupon_Pb_ID = UUIDUtil.Empty;
    public UUID Coupon_Type_ID = UUIDUtil.Empty;
    public String Coupon_Name = "";
    public String Coupon_Content = "";
    public String Coupon_Remark = "";
    public Date Coupon_Date = new Date();
    public Date Coupon_BegDate = new Date();
    public Date Coupon_EndDate = new Date();
	public int OrderNumber = 0 ;

    //Product 属性
    public UUID Product_ID = UUIDUtil.Empty;
    public int Product_Discount;
    public String Product_Name = "";
    public String Product_HeadImg = "";
    public String Product_Abstrct = "";
    public double Product_Price = 0;

	public double Product_NowPrice = 0;
	public int Product_ProductCount = 0;
	public int Product_BookedCount = 0;
	public int Product_NoBookedCount = 0;
	
	public String Product_Galleryful = "";

	public boolean Product_HasToilet = false;
	
	//相册
	public String JsonPhotos = "";
	
	private static class JsonPhoto{
		public List<Photo> Photos = null;
		public JsonPhoto(List<Photo> photos) {
			super();
			Photos = photos;
		}
	}
    
    public CouponEntity()
    {
        // TODO Auto-generated constructor stub
    }
    

    public CouponEntity(Coupon model)
    {
        // TODO Auto-generated constructor stub
        super(model!= null?model.StoreBase:null);
        if(model != null)
        {
            this.ID= model.getID(); 
            this.Coupon_BegDate = model.BegDate;
            this.Coupon_BegDate = model.EndDate;
            this.Coupon_Date     = model.Date;
            this.Coupon_Remark   = model.Remark;
            this.Coupon_Content  = model.Content;
            this.Coupon_Name     = model.Name;
            this.Coupon_Type_ID  = model.Type_ID;
            this.Coupon_Pb_ID    = model.Pb_ID;
            this.Coupon_For_ID   = model.For_ID;
            this.Coupon_Discount = model.Discount;
            this.OrderNumber = model.OrderNumber;
            if(model.Product != null)
            {
                this.Product_ID = model.Product.getID();
                this.Product_Discount = model.Product.Discount;
                this.Product_Name = model.Product.Name;
                this.Product_HeadImg = model.Product.HeadImg;
                this.Product_Abstrct = model.Product.Abstrct;
                this.Product_Price = model.Product.Price;
                
                this.Product_NowPrice = model.Product.NowPrice;
                this.Product_ProductCount = model.Product.ProductCount;
                this.Product_BookedCount = model.Product.BookedCount;
                this.Product_NoBookedCount = model.Product.NoBookedCount;

    			this.Product_Galleryful = model.Product.Galleryful;
    			this.Product_HasToilet = model.Product.HasToilet;
            }
            if(model.Photos != null){
            	try {

                	JsonPhoto photo = new JsonPhoto(model.Photos);
                	this.JsonPhotos = new Gson().toJson(photo);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
            }
        }
    }

    public Coupon getModel()
    {
        // TODO Auto-generated method stub
        Coupon model = new Coupon();

        model.setID(ID); 
        model.BegDate  = this.Coupon_BegDate    ;
        model.EndDate  = this.Coupon_BegDate    ;
        model.Date     = this.Coupon_Date       ;
        model.Remark   = this.Coupon_Remark     ;
        model.Content  = this.Coupon_Content    ;
        model.Name     = this.Coupon_Name       ;
        model.Type_ID  = this.Coupon_Type_ID    ;
        model.Pb_ID    = this.Coupon_Pb_ID      ;
        model.For_ID   = this.Coupon_For_ID     ;
        model.Discount = this.Coupon_Discount   ;
        model.OrderNumber = this.OrderNumber;
        
        model.Product = new Product();

        model.Product.setID(this.Product_ID);
        model.Product.Discount = this.Product_Discount  ;
        model.Product.Name     = this.Product_Name      ;
        model.Product.HeadImg  = this.Product_HeadImg   ;
        model.Product.Abstrct  = this.Product_Abstrct   ;
        model.Product.Price    = this.Product_Price     ;

        model.Product.NowPrice = this.Product_NowPrice;
        model.Product.ProductCount = this.Product_ProductCount;
        model.Product.BookedCount = this.Product_BookedCount;
        model.Product.NoBookedCount = this.Product_NoBookedCount;

		model.Product.Galleryful = this.Product_Galleryful;
		model.Product.HasToilet = this.Product_HasToilet;
        
        model.StoreBase = super.getStoreBase();

        try {
            JsonPhoto photo = new Gson().fromJson(JsonPhotos,JsonPhoto.class);
            if(photo != null && photo.Photos != null){
            	model.Photos = photo.Photos;
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();//handled
			AppExceptionHandler.handler(e, "CouponEntity，Gson 转换 出现异常");
		}
        
        return model;
    }
    /**
     * List<Model> 转换 List<Entity> 
     * @param ltEntity
     * @return
     */
    public static List<CouponEntity> listFormModel(List<Coupon> ltModel)
    {
        // TODO Auto-generated method stub
        List<CouponEntity> ltEntity = new ArrayList<CouponEntity>();
        for (Coupon model : ltModel)
        {
            ltEntity.add(new CouponEntity(model));
        }
        return ltEntity;
    }
    /**
     * List<Entity> 转换 List<Model> 
     * @param ltEntity
     * @return
     */
    public static List<Coupon> listToModel(List<CouponEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        List<Coupon> ltModel = new ArrayList<Coupon>();
        for (CouponEntity entity : ltEntity)
        {
            ltModel.add(entity.getModel());
        }
        return ltModel;
    }
}

package com.suiding.entity.framework;

import java.util.UUID;

import com.suiding.model.Product;
import com.suiding.util.UUIDUtil;

public class ProductEntity {
	//product Ù–‘
	public UUID Sb_ID = UUIDUtil.Empty;
	public String Name = "";
	public String HeadImg = "";
	public String Abstrct = "";
	public int Discount;
	public double Price =0;
	public UUID ID = UUIDUtil.Empty;

	public double NowPrice = 0;
	public int ProductCount = 0;
	public int BookedCount = 0;
	public int NoBookedCount = 0;
	
	public String Galleryful = "";
	public boolean HasToilet = false;
	
	public ProductEntity()
    {
        // TODO Auto-generated constructor stub
    }

    public ProductEntity(Product model)
    {
        // TODO Auto-generated constructor stub
        if (model != null)
        {
            this.Sb_ID = model.getSb_ID();
            this.Name = model.getPbName();
            this.HeadImg = model.getPbHeadImg();
            this.Abstrct = model.getPbAbstrct();
            this.Discount = model.getPbDiscount();
            this.Price = model.getSbPrice();
            this.ID = model.getID();
            
            this.NowPrice = model.NowPrice;
            this.ProductCount = model.ProductCount;
            this.BookedCount = model.BookedCount;
            this.NoBookedCount = model.NoBookedCount;

			this.Galleryful = model.Galleryful;
			this.HasToilet = model.HasToilet;
			
        }
    }
    
    public Product getAddress()
    {
        // TODO Auto-generated method stub
    	Product model = new Product();

        model.setID(this.ID);
        model.setSb_ID(this.Sb_ID);
        model.setPbName(this.Name);
        model.setPbAbstrct(this.Abstrct);
        model.setPbHeadImg(this.HeadImg);
        model.setPbDiscount(this.Discount);
        model.setSbPrice(this.Price);

        model.NowPrice = this.NowPrice;
        model.ProductCount = this.ProductCount;
        model.BookedCount = this.BookedCount;
        model.NoBookedCount = this.NoBookedCount;

        model.HasToilet = this.HasToilet;
		model.Galleryful = this.Galleryful;

        return model;
    }
}

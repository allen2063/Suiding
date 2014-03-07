package com.suiding.entity.framework;

import java.util.UUID;

import com.suiding.model.Address;


public abstract class AddressEntity
{
    //Address  Ù–‘
	public UUID ADR_ID;
    public int ADR_Contry;
    public int ADR_Province;
    public int ADR_City;
    public int ADR_Xian;
    public int ADR_Xiang;
    public int ADR_Cun;
    public String ADR_Custom;
    public float ADR_PostionX;
    public float ADR_PostionY;

    public AddressEntity()
    {
        // TODO Auto-generated constructor stub
    }

    public AddressEntity(Address address)
    {
        // TODO Auto-generated constructor stub
        if (address != null)
        {
            this.ADR_ID = address.getID();
            this.ADR_Contry = address.Contry;
            this.ADR_Province = address.Province;
            this.ADR_City = address.City;
            this.ADR_Xian = address.Xian;
            this.ADR_Xiang = address.Xiang;
            this.ADR_Cun = address.Cun;
            this.ADR_Custom = address.Custom;
            this.ADR_PostionX = address.PostionX;
            this.ADR_PostionY = address.PostionY;
        }
    }

    public Address getAddress()
    {
        // TODO Auto-generated method stub
        Address address = new Address();

        address.setID(this.ADR_ID);
        address.Contry = this.ADR_Contry;
        address.Province = this.ADR_Province;
        address.City = this.ADR_City;
        address.Xian = this.ADR_Xian;
        address.Xiang = this.ADR_Xiang;
        address.Cun = this.ADR_Cun;
        address.Custom = this.ADR_Custom;
        address.PostionX = this.ADR_PostionX;
        address.PostionY = this.ADR_PostionY;

        return address;
    }

}

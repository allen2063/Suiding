package com.suiding.entity.framework;

import java.util.UUID;

import com.suiding.model.Club;
import com.suiding.model.Hotel;
import com.suiding.model.KTV;
import com.suiding.model.Restaurant;
import com.suiding.model.Stall;
import com.suiding.model.StoreBase;
import com.suiding.util.UUIDUtil;

public abstract class StoreEntity extends StoreBaseEntity implements EntityBase<StoreBase>
{
    public static final int INVAILD = -1;
    public static final int STOREBASE = 0;
    public static final int HOTEL = 1;
    public static final int STALL = 2;
    public static final int RESTAURANT = 3;
    public static final int KTV = 4;
    public static final int CLUB = 5;
    
    // Ù–‘
    public int Level;
    public int Entity;
    public UUID ID;

    public StoreEntity()
    {
        // TODO Auto-generated constructor stub
    }
    

    public StoreEntity(StoreBase store)
    {
        // TODO Auto-generated constructor stub
        super(store);

        if(store.getClass().equals(StoreBase.class)){
            this.Level = -1;
            this.Entity= STOREBASE;
            this.ID = UUID.randomUUID();
        }
        else if(store instanceof Hotel){
            Hotel tStore = (Hotel)store;
            this.Level = tStore.Level;
            this.ID    = tStore.getHtl_ID();
            this.Entity= HOTEL;
        }
        else if(store instanceof Restaurant){
            Restaurant tStore = (Restaurant)store;
            this.Level = tStore.Level;
            this.ID    = tStore.getRsr_ID();
            this.Entity= RESTAURANT;
        }
        else if(store instanceof Stall){
            Stall tStore = (Stall)store;
            this.Level = 0;
            this.ID    = tStore.getSta_ID();
            this.Entity= STALL;
        }
        else if(store instanceof KTV){
            KTV tStore = (KTV)store;
            this.Level = 0;
            this.ID    = tStore.getKtv_ID();
            this.Entity= KTV;
        }
        else if(store instanceof Club){
            Club tStore = (Club)store;
            this.Level = 0;
            this.ID    = tStore.getClu_ID();
            this.Entity= CLUB;
        }else{
            this.Level = -1;
            this.ID    = UUIDUtil.Empty;
            this.Entity= INVAILD;
        }
    }

    public StoreBase getModel()
    {
        // TODO Auto-generated method stub
        StoreBase store = super.getStoreBase();

        if(this.Entity== HOTEL){
            Hotel tStore = new Hotel(store,this.Level);
            tStore.setHtl_ID(ID);
            store = tStore;
        }
        else if(this.Entity == RESTAURANT){
            Restaurant tStore = new Restaurant(store,this.Level);
            tStore.setRsr_ID(ID);
            store = tStore;
        }
        else if(this.Entity== STALL){
            Stall tStore = new Stall(store);
            tStore.setSta_ID(ID);
            store = tStore;
        }
        else if(this.Entity== KTV){
            KTV tStore = new KTV(store);
            tStore.setKtv_ID(ID);
            store = tStore;
        }
        else if(this.Entity== CLUB){
            Club tStore = new Club(store);
            tStore.setClu_ID(ID);
            store = tStore;
        }
        else if(this.Entity== STOREBASE){
            
        }
        else{
            store = null;
        }
        return store;
    }

    
}

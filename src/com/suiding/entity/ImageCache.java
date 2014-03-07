package com.suiding.entity;

import java.util.Date;

import com.suiding.util.DateGuid;

public class ImageCache
{
    public String ID = DateGuid.NewID();
    public String Name = "";
    public String Key = "";
    public String Path = "";
    public Date LastDate = new Date();
    public Date RegDate = new Date();
    public String Remark = "";
    
    public ImageCache(){}
    
    public ImageCache(String name, String key, String path,String remark)
    {
        super();
        Name = name;
        Key = key;
        Path = path;
        Remark = remark;
        LastDate = RegDate = new Date();
    }
}

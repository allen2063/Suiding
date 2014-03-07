package com.suiding.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.suiding.entity.framework.EntityBase;
import com.suiding.model.Photo;
import com.suiding.util.UUIDUtil;


/**
 * PhotoEntity
 * @author SCWANG
 *         Model.Photo 在手机本地数据库 的实体
 */
public class PhotoEntity implements EntityBase<Photo>
{
    public UUID ID = UUIDUtil.Empty;
    public String Remark = "";
    public String Name = "";
    public String Url = "";
    public String Describe = "";
    public UUID ForID = UUIDUtil.Empty;
    public UUID Pid = UUIDUtil.Empty;

    public PhotoEntity()
    {
    }

    public PhotoEntity(Photo photo)
    {
        this.ID = photo.getID();
        this.Name = photo.Name;
        this.Describe = photo.Describe;
        this.Remark = photo.Remark;
        this.Url = photo.Url;

        this.ForID = photo.For_ID;
        this.Pid = photo.Pid;
    }

    public Photo getModel()
    {
        Photo photo = new Photo();
        photo.setID(ID);

        photo.Name = this.Name;
        photo.Describe = this.Describe;
        photo.Remark = this.Remark;
        photo.Url = this.Url;
        photo.For_ID = this.ForID;
        photo.Pid = this.Pid;
        return photo;
    }

    public static List<Photo> toListPhoto(List<PhotoEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        return toListModel(ltEntity);
    }
    
    public static List<Photo> toListModel(List<PhotoEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        List<Photo> ltModel = new ArrayList<Photo>();
        for (PhotoEntity entity : ltEntity)
        {
            ltModel.add(entity.getModel());
        }
        return ltModel;
    }

    /**
     * List<Model> 转换 List<Entity> 
     * @param ltEntity
     * @return
     */
    public static List<PhotoEntity> listFormModel(List<Photo> ltModel)
    {
        // TODO Auto-generated method stub
        List<PhotoEntity> ltEntity = new ArrayList<PhotoEntity>();
        for (Photo model : ltModel)
        {
            ltEntity.add(new PhotoEntity(model));
        }
        return ltEntity;
    }
    /**
     * List<Entity> 转换 List<Model> 
     * @param ltEntity
     * @return
     */
    public static List<Photo> listToModel(List<PhotoEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        List<Photo> ltModel = new ArrayList<Photo>();
        for (PhotoEntity entity : ltEntity)
        {
            ltModel.add(entity.getModel());
        }
        return ltModel;
    }
}

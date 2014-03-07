package com.suiding.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.suiding.entity.framework.EntityBase;
import com.suiding.entity.framework.StoreBaseEntity;
import com.suiding.model.Club;
import com.suiding.util.UUIDUtil;


/**
 * ClubEntity
 * 
 * @author SCWANG
 *         Model.Club 在手机本地数据库 的实体
 */
public class ClubEntity extends StoreBaseEntity implements EntityBase<Club>
{
    //Club 属性
    public UUID ID = UUIDUtil.Empty;

    public ClubEntity()
    {
    }

    public ClubEntity(Club tClub)
    {
        super(tClub);
        this.ID = tClub.getClu_ID();
    }

    public Club getModel()
    {
        Club tClub = new Club(super.getStoreBase());
        tClub.setClu_ID(ID);
        return tClub;
    }

    /**
     * List<Model> 转换 List<Entity>
     * 
     * @param ltEntity
     * @return
     */
    public static List<ClubEntity> listFormModel(List<Club> ltModel)
    {
        // TODO Auto-generated method stub
        List<ClubEntity> ltEntity = new ArrayList<ClubEntity>();
        for (Club model : ltModel)
        {
            ltEntity.add(new ClubEntity(model));
        }
        return ltEntity;
    }

    /**
     * List<Entity> 转换 List<Model>
     * 
     * @param ltEntity
     * @return
     */
    public static List<Club> listToModel(List<ClubEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        List<Club> ltModel = new ArrayList<Club>();
        for (ClubEntity entity : ltEntity)
        {
            ltModel.add(entity.getModel());
        }
        return ltModel;
    }
}

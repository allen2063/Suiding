package com.suiding.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.suiding.entity.framework.EntityBase;
import com.suiding.entity.framework.StoreBaseEntity;
import com.suiding.model.KTV;
import com.suiding.util.UUIDUtil;


/**
 * KTVEntity
 * 
 * @author SCWANG
 *         Model.KTV ���ֻ��������ݿ� ��ʵ��
 */
public class KTVEntity extends StoreBaseEntity implements EntityBase<KTV>
{
    //KTV ����
    public UUID ID = UUIDUtil.Empty;

    public KTVEntity()
    {
    }

    public KTVEntity(KTV tKTV)
    {
        super(tKTV);
        this.ID = tKTV.getID();
    }

    public KTV getModel()
    {
        KTV tKTV = new KTV(super.getStoreBase());
        tKTV.setKtv_ID(ID);
        return tKTV;
    }

    /**
     * List<Model> ת�� List<Entity>
     * 
     * @param ltEntity
     * @return
     */
    public static List<KTVEntity> listFormModel(List<KTV> ltModel)
    {
        // TODO Auto-generated method stub
        List<KTVEntity> ltEntity = new ArrayList<KTVEntity>();
        for (KTV model : ltModel)
        {
            ltEntity.add(new KTVEntity(model));
        }
        return ltEntity;
    }

    /**
     * List<Entity> ת�� List<Model>
     * 
     * @param ltEntity
     * @return
     */
    public static List<KTV> listToModel(List<KTVEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        List<KTV> ltModel = new ArrayList<KTV>();
        for (KTVEntity entity : ltEntity)
        {
            ltModel.add(entity.getModel());
        }
        return ltModel;
    }
}

package com.suiding.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.suiding.entity.framework.EntityBase;
import com.suiding.model.Area;

public class AreaEntity implements EntityBase<Area>
{
    public int ID = new Random().nextInt();
    
    public String Name = "";
    //NameµÄÆ´Òô
    public String Sell = "";
    
    public int Level = -1;
    
    public int UpID = new Random().nextInt();
    
    private boolean mSelected;

    public AreaEntity()
    {
        // TODO Auto-generated constructor stub
    }
    
    public AreaEntity(Area area)
    {
        // TODO Auto-generated constructor stub
        this.ID = area.getID();
        this.Name = area.Name;
        this.Level = area.Level;
        this.UpID = area.Pid;
        this.Sell = area.Name;
        //SpellUtil.getSpell(area.Name.trim());
    }
    
    public boolean isSelected()
    {
        return mSelected;
    }
    
    public void setSelected(boolean selected)
    {
        this.mSelected = selected;
    }
    
    public Area getModel()
    {
        // TODO Auto-generated method stub
        Area tModel = new Area();
        tModel.setID(ID);
        tModel.Level = this.Level;
        tModel.Pid = this.UpID;
        tModel.Name = this.Name;
        return tModel;
    }

    public static List<AreaEntity> listFormAreaModel(List<Area> ltArea)
    {
        // TODO Auto-generated method stub
        return listFormModel(ltArea);
    }
    
    public static List<Area> listToAreaModel(List<AreaEntity> ltArea)
    {
        // TODO Auto-generated method stub
        return listToModel(ltArea);
    }
    

    /**
     * List<Model> ×ª»» List<Entity> 
     * @param ltEntity
     * @return
     */
    public static List<AreaEntity> listFormModel(List<Area> ltModel)
    {
        // TODO Auto-generated method stub
        List<AreaEntity> ltEntity = new ArrayList<AreaEntity>();
        for (Area model : ltModel)
        {
            ltEntity.add(new AreaEntity(model));
        }
        return ltEntity;
    }
    /**
     * List<Entity> ×ª»» List<Model> 
     * @param ltEntity
     * @return
     */
    public static List<Area> listToModel(List<AreaEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        List<Area> ltModel = new ArrayList<Area>();
        for (AreaEntity entity : ltEntity)
        {
            ltModel.add(entity.getModel());
        }
        return ltModel;
    }
}

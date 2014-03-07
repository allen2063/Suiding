package com.suiding.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.suiding.entity.framework.EntityBase;
import com.suiding.entity.framework.StoreBaseEntity;
import com.suiding.model.BirthInvitation;
import com.suiding.util.UUIDUtil;

public class InvitationEntity extends StoreBaseEntity implements
		EntityBase<BirthInvitation> {
	public UUID ID = UUIDUtil.Empty;
	public UUID User_ID = UUIDUtil.Empty;
	public UUID From_ID = UUIDUtil.Empty;
	public String Name = "";
	public String Content = "";
	public String Remark = "";
	public Date Date = new Date();

	public InvitationEntity() {
	}

	public InvitationEntity(BirthInvitation tBirthInvitation) {
		super(tBirthInvitation.StoreBase);
		this.ID = tBirthInvitation.getID();
		this.User_ID = tBirthInvitation.User_ID;
		this.From_ID = tBirthInvitation.From_ID;
		this.Name = tBirthInvitation.Name;
		this.Content = tBirthInvitation.Content;
		this.Remark = tBirthInvitation.Remark;
		this.Date = tBirthInvitation.Date;

	}

	public BirthInvitation getModel() {
		BirthInvitation entity = new BirthInvitation();
		entity.setID(this.ID);
		entity.User_ID = this.User_ID;
		entity.From_ID = this.From_ID;
		entity.Name = this.Name;
		entity.Content = this.Content;
		entity.Remark = this.Remark;
		entity.Date = this.Date;

		entity.setStoreBase(super.getStoreBase());
		return entity;
	}

	/**
	 * List<Model> ×ª»» List<Entity>
	 * 
	 * @param ltEntity
	 * @return
	 */
	public static List<InvitationEntity> listFormModel(
			List<BirthInvitation> ltModel) {
		// TODO Auto-generated method stub
		List<InvitationEntity> ltEntity = new ArrayList<InvitationEntity>();
		for (BirthInvitation model : ltModel) {
			ltEntity.add(new InvitationEntity(model));
		}
		return ltEntity;
	}

	/**
	 * List<Entity> ×ª»» List<Model>
	 * 
	 * @param ltEntity
	 * @return
	 */
	public static List<BirthInvitation> listToModel(
			List<InvitationEntity> ltEntity) {
		// TODO Auto-generated method stub
		List<BirthInvitation> ltModel = new ArrayList<BirthInvitation>();
		for (InvitationEntity entity : ltEntity) {
			ltModel.add(entity.getModel());
		}
		return ltModel;
	}
}

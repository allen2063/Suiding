package com.suiding.model;

import java.util.UUID;

import com.suiding.constant.FavoriteType;
import com.suiding.util.UUIDUtil;

/**
 * 
 *  ’≤ÿ
 * 
 * @author tt
 * 
 */
public class Favorite {
	private UUID ID = UUIDUtil.Empty;
	public UUID For_ID = UUIDUtil.Empty;
	public UUID User_ID = UUIDUtil.Empty;
	public String Remark = "";
	public int Type;

	public Favorite() {
		this.ID = UUID.randomUUID();
	}

	public Favorite(UUID For_ID, UUID User_ID, String Remark, int Type) {
		this.For_ID = For_ID;
		this.User_ID = User_ID;
		this.Remark = Remark;
		this.Type = Type;
		this.ID = UUID.randomUUID();
	}

	public Favorite(User user, StoreBase store) {
		if (store.getID() != null)
			this.For_ID = store.getID();
		if (user.getID() != null)
			this.User_ID = user.getID();
		this.Type = FavoriteType.STORE;
		this.ID = UUID.randomUUID();
	}

	public UUID getUser_ID() {
		return User_ID;
	}

	public void setUser_ID(UUID user_ID) {
		User_ID = user_ID;
	}

	public UUID getID() {
		return ID;
	}

	public void setID(UUID iD) {
		ID = iD;
	}

	public UUID getFor_ID() {
		return For_ID;
	}

	public void setFor_ID(UUID for_ID) {
		For_ID = for_ID;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

}

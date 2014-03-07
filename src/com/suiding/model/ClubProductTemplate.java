package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class ClubProductTemplate {

	public String Name = "";
	private UUID ID = UUIDUtil.Empty;

	public ClubProductTemplate() {
		this.ID = UUID.randomUUID();
	}

	public ClubProductTemplate(String Name) {
		if (Name != null)
			this.Name = Name;
		this.ID = UUID.randomUUID();
	}

	public UUID getID() {
		return ID;
	}

	public void setID(UUID ID) {
		this.ID = ID;
	}

	/**
	 * ���ID�ֶκ�name�ֶ��Ƿ�Ϊ�ջ���Ϊ���� ͨ������true ���򷵻�false �����ֶμ�����Ϊ�� �޸�ΪĬ��ֵ
	 */
	public boolean checkModelIsPassed() {
		if (ID == null || this.ID == UUIDUtil.Empty || this.Name == null
				|| this.Name.equals("")) {
			return false;
		}

		return true;
	}
}
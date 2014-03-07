package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class Role {

	protected String Name = "";
	private UUID ID = UUIDUtil.Empty;

	public Role() {
		this.ID = UUID.randomUUID();
	}

	public Role(String roleName) {
		if (roleName != null) {
			this.Name = roleName;
		}
		this.ID = UUID.randomUUID();
	}

	public Role(Role role) {
		if (role.getID() != null)
			this.ID = role.getID();
		if (role.getRoleName() != null)
			this.Name = role.getRoleName();
	}

	public String getRoleName() {
		return Name;
	}

	public void setRoleName(String RoleName) {
		this.Name = RoleName;
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

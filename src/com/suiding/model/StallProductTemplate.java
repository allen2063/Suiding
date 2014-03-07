package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class StallProductTemplate {

	public String Name = "";
	public double Price;
	private UUID ID = UUIDUtil.Empty;

	public StallProductTemplate() {
		this.ID = UUID.randomUUID();
	}

	public StallProductTemplate(String Name, double Price) {
		if (null != Name)
			this.Name = Name;
		this.Price = Price;
		this.ID = UUID.randomUUID();
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public double getPrice() {
		return Price;
	}

	public void setPrice(double Price) {
		this.Price = Price;
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
		if (Name == null || Name.equals("") || ID == null
				|| this.ID == UUIDUtil.Empty) {
			return false;
		}

		return true;
	}
}

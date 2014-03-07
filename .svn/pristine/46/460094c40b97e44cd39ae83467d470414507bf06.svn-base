package com.suiding.model;

import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class User {

	public String UserName="";
	public String Password="";
	public String Email="";
	public String NickName = "";
	public boolean IsEmailVerifi = false;
	public UUID Role_ID=UUIDUtil.Empty;
	private UUID ID =UUIDUtil.Empty;

	public String PhoneNo="";
	public boolean IsPhoneVerifi = false;

	public Role Role;

	public User(){
		this.ID = UUID.randomUUID();
	}

	/**
	 * 
	 * @param UserName
	 * @param Password
	 * @param Email
	 * @param role
	 */
	public User(Role role,String UserName,String Password,String Email,String NickName){
		if(UserName!=null) this.UserName = UserName;
		if(Password!=null) this.Password = Password;
		if(Email!=null) this.Email = Email;
		if(NickName!=null) this.NickName = NickName;

		this.ID = UUID.randomUUID();
		this.Role = role;
		if(role!=null)
		{
			this.Role_ID = role.getID();
		}
	}
	
	public User(User users)
	{
		if(users.getID()!=null) this.ID = users.getID();
		if(users.UserName!=null) this.UserName = users.UserName;
		if(users.Password!=null) this.Password = users.Password;
		if(users.Email!=null) this.Email = users.Email;
		if(users.NickName!=null) this.NickName = users.NickName;
		if(users.Role_ID!=null) this.Role_ID = users.Role_ID;
		this.PhoneNo = users.PhoneNo;
		this.IsEmailVerifi = users.IsEmailVerifi;
		this.IsPhoneVerifi = users.IsPhoneVerifi;
		
		this.Role = users.getRole();
		if(this.Role!=null)
		{
			this.Role_ID = this.Role.getID();
		}
	}


	public boolean isIsEmailVerifi() {
		return IsEmailVerifi;
	}

	public void setIsEmailVerifi(boolean isEmailVerifi) {
		IsEmailVerifi = isEmailVerifi;
	}

	public UUID getID(){
		return ID;
	}
	public void setID(UUID ID){
		this.ID = ID;
	}

	public void setRole(Role role) {
		Role = role;
		if(role!=null)
		{
			this.Role_ID = role.getID();
		}
	}

	public Role getRole() {
		return Role;
	}

	/**
	 * 检查ID字段和name字段是否为空或者为“” 通过返回true 否则返回false 其他字段检查如果为空 修复为默认值
	 */
	public boolean checkModelIsPassed()
	{
		if(this.Role_ID == null
				|| this.Role_ID == UUIDUtil.Empty
				|| ID == null
				|| this.ID == UUIDUtil.Empty
				|| this.UserName == null || this.UserName.equals("")
				|| this.Password == null || this.Password.equals(""))
		{
			return false;
		}
		
		if(this.Email == null) this.Email = "";
		if(this.NickName == null) this.NickName = "";
		
		return true;
	}
}

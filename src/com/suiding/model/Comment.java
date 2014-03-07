package com.suiding.model;

import java.util.Date;
import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class Comment {

	public int Score;
	public String Content="";
	public Date Date=new Date();
	public UUID User_ID=UUIDUtil.Empty;
	public UUID For_ID=UUIDUtil.Empty;
	private UUID ID =UUIDUtil.Empty;

	public User User;

	public Comment(){
		this.ID = UUID.randomUUID();
	}

	public Comment(Comment cm){
		this.Score = cm.Score;
		this.Content = cm.Content;
		this.Date = cm.Date;
		this.For_ID = cm.For_ID;
		this.ID = cm.ID;
		
		this.User = cm.User;
		this.User_ID = cm.User_ID;
	}

	public void setUser(User user) {
		if(user != null){
			this.User_ID = user.getID();
		}
		this.User = user;
	}
	public UUID getID(){
		return ID;
	}
	public void setID(UUID ID){
		this.ID = ID;
	}
	/**
	 * 检查ID字段和name字段是否为空或者为“”
	 * 通过返回true 否则返回false
	 * 其他字段检查如果为空 修复为默认值
	 */
	public boolean checkModelIsPassed()
	{
		if(this.User_ID == null
				|| this.ID == UUIDUtil.Empty
				|| For_ID == null
				|| this.For_ID == UUIDUtil.Empty
				|| User_ID == null
				|| this.User_ID == UUIDUtil.Empty)
		{
			return false;
		}

		if(this.Content==null)
		{
			 Content="";
		}
		if(this.Date == null)
		{
			Date=new Date();
		}
		return true;
	}
}

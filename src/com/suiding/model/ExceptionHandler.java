package com.suiding.model;

import java.util.Date;
import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class ExceptionHandler {
	
	public static final int STATUS_NEW = 0;
	public static final int STATUS_RECEIVE = 1;
	public static final int STATUS_SUBMIT = 2;
	public static final int STATUS_CONFIRM = 3;

	private UUID ID =UUIDUtil.Empty;
    
    public UUID User_ID=UUIDUtil.Empty;
    public Date RegDate = new Date();
    public String Device = "";
    public String Version = "";
    public String Name = "";
    public String Remark = "";
    public String Message = "";
    public String Stack = "";
    public String Thread = "";
    public int Status = STATUS_NEW;
	
	public ExceptionHandler(){
		this.ID = UUID.randomUUID();
	}

	public ExceptionHandler(String Name,String Message,
			String Stack,String Version,String Thread){
		this.ID = UUID.randomUUID();
		this.Name = Name;
		this.Message = Message;
		this.Stack = Stack;
		this.Version = Version;
		this.Thread = Thread;
	}
	
	/**
	 * 检查ID字段和name字段是否为空或者为“”
	 * 通过返回true 否则返回false
	 * 其他字段检查如果为空 修复为默认值
	 */	
	public boolean checkModelIsPassed()
	{
		if(this.ID == null
				|| this.User_ID == UUIDUtil.Empty)
		{
			return false;
		}

		return true;
	}
}

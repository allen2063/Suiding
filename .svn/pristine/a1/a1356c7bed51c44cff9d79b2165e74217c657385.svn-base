package com.suiding.model;

import java.util.Date;
import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class Feedback {
	
	UUID ID = UUIDUtil.Empty;
	public String Name = "";
	public String Content = "";
	public Date Date = new Date();
	public String Contact = "";
	public UUID UserID = UUIDUtil.Empty;
	public int CurVersion;
	public String BugInfo = "";

	public Feedback() {
		this.ID = UUID.randomUUID();
	}

	public Feedback(String Name, String Content, Date Date, String Contact,
			UUID UserID, int CurVersion, String BugInfo) {
		this.Name = Name;
		this.Content = Content;
		this.Date = Date;
		this.Contact = Contact;
		this.UserID = UserID;
		this.CurVersion = CurVersion;
		this.BugInfo = BugInfo;
		this.ID = UUID.randomUUID();
	}

	public UUID getID() {
		return ID;
	}

	public void setID(UUID iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
	}

	public String getContact() {
		return Contact;
	}

	public void setContact(String contact) {
		Contact = contact;
	}

	public UUID getUserID() {
		return UserID;
	}

	public void setUserID(UUID userID) {
		UserID = userID;
	}

	public int getCurVersion() {
		return CurVersion;
	}

	public void setCurVersion(int curVersion) {
		CurVersion = curVersion;
	}

	public String getBugInfo() {
		return BugInfo;
	}

	public void setBugInfo(String bugInfo) {
		BugInfo = bugInfo;
	}
}

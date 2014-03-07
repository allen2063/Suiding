package com.suiding.model;

import java.util.Date;
import java.util.UUID;

import com.suiding.util.UUIDUtil;

public class Version {

	private UUID ID = UUIDUtil.Empty;

	// 版本描述字符串
	public String Version = "";
	// 版本更新时间
	public Date UpdateTime = new Date();
	// 新版本更新下载地址
	public String DownloadURL = "";
	// 更新描述信息
	public String DisplayMessage = "";
	// 版本号
	public int VersionCode = 0;
	// apk名称
	public String ApkName = "";

	public Version() {
		// TODO Auto-generated constructor stub
		this.ID = UUID.randomUUID();
	}

	public Version(String version, Date date, String url, String msg, int code,
			String apk) {
		// TODO Auto-generated constructor stub
		this.ID = UUID.randomUUID();
		this.VersionCode = code;
		if (version != null)
			this.Version = version;
		if (date != null)
			this.UpdateTime = date;
		if (url != null)
			this.DownloadURL = url;
		if (msg != null)
			this.DisplayMessage = msg;
		if (apk != null)
			this.ApkName = apk;
	}

	public Version(Version version) {
		// TODO Auto-generated constructor stub
		this.VersionCode = version.VersionCode;
		if (version.ID != null)
			this.ID = version.ID;
		if (version.Version != null)
			this.Version = version.Version;
		if (version.UpdateTime != null)
			this.UpdateTime = version.UpdateTime;
		if (version.DownloadURL != null)
			this.DownloadURL = version.DownloadURL;
		if (version.DisplayMessage != null)
			this.DisplayMessage = version.DisplayMessage;
		if (version.ApkName != null)
			this.ApkName = version.ApkName;
	}

	public UUID getID() {
		return ID;
	}

	public void setID(UUID id) {
		ID = id;
	}
}

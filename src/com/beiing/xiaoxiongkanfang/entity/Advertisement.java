package com.beiing.xiaoxiongkanfang.entity;

public class Advertisement {
	private String type;
	
	private String picurl;
	
	private String title;
	
	private String houseid;

	public Advertisement(String type, String picurl, String title,
			String houseid) {
		this.type = type;
		this.picurl = picurl;
		this.title = title;
		this.houseid = houseid;
	}

	public Advertisement() {
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}

	public String getType() {
		return type;
	}

	public String getPicurl() {
		return picurl;
	}

	public String getTitle() {
		return title;
	}

	public String getHouseid() {
		return houseid;
	}
	
	
}





















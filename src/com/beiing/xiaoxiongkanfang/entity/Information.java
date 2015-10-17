package com.beiing.xiaoxiongkanfang.entity;

public class Information {
	
	public static final int NORMAL_PIC = 0;//一张图
	public static final int BIG_PIC = 1;//三张图
	
	public static int getTypeCount(){
		return 2;
	}
	
	public int getItemViewType(){
		if(type.equals("0"))
			return NORMAL_PIC;
		else
			return BIG_PIC;
	}
	
	
	
	private String id;
	
	private String type;
	
	private String title;
	
	private String summary;
	
	private String thumbnail;
	
	private String groupthumbnail;
	
	private int commentcount;
	
	private int imagecount;
	
	private String commentid;

	public Information(String id, String type, String title, String summary,
			String thumbnail, String groupthumbnail, int commentcount,
			int imagecount, String commentid) {
		super();
		this.id = id;
		this.type = type;
		this.title = title;
		this.summary = summary;
		this.thumbnail = thumbnail;
		this.groupthumbnail = groupthumbnail;
		this.commentcount = commentcount;
		this.imagecount = imagecount;
		this.commentid = commentid;
	}

	public Information() {
		super();
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public void setGroupthumbnail(String groupthumbnail) {
		this.groupthumbnail = groupthumbnail;
	}

	public void setCommentcount(int commentcount) {
		this.commentcount = commentcount;
	}

	public void setImagecount(int imagecount) {
		this.imagecount = imagecount;
	}

	public void setCommentid(String commentid) {
		this.commentid = commentid;
	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}

	public String getSummary() {
		return summary;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public String getGroupthumbnail() {
		return groupthumbnail;
	}

	public int getCommentcount() {
		return commentcount;
	}

	public int getImagecount() {
		return imagecount;
	}

	public String getCommentid() {
		return commentid;
	}
	
	
	
}














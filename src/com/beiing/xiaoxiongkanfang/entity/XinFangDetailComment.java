package com.beiing.xiaoxiongkanfang.entity;

public class XinFangDetailComment {
	private String user;
	private String date;
	private String summary;// 总结
	private String adv;// 优点
	private String disadv;// 缺点
	private String score;// 评分

	public XinFangDetailComment(String user, String date, String summary,
			String adv, String disadv, String score) {
		this.user = user;
		this.date = date;
		this.summary = summary;
		this.adv = adv;
		this.disadv = disadv;
		this.score = score;
	}

	public XinFangDetailComment() {
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAdv() {
		return adv;
	}

	public void setAdv(String adv) {
		this.adv = adv;
	}

	public String getDisadv() {
		return disadv;
	}

	public void setDisadv(String disadv) {
		this.disadv = disadv;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}

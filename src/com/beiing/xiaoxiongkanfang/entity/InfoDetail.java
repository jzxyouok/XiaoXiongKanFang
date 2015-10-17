package com.beiing.xiaoxiongkanfang.entity;

import java.util.List;

public class InfoDetail {

	private String id;
	private String title;
	private String source;
	private String time;
	private String url;
	private String surl;
	private List<InfoContent> content;

	public InfoDetail(String id, String title, String source, String time,
			String url, String surl, List<InfoContent> content) {
		this.id = id;
		this.title = title;
		this.source = source;
		this.time = time;
		this.url = url;
		this.surl = surl;
		this.content = content;
	}

	public InfoDetail() {
	}

	public String getSub() {
		return source + "  " + time;
	}

	public InfoDetail setId(String id) {
		this.id = id;
		return this;
	}

	public InfoDetail setTitle(String title) {
		this.title = title;
		return this;
	}

	public InfoDetail setSource(String source) {
		this.source = source;
		return this;
	}

	public InfoDetail setTime(String time) {
		this.time = time;
		return this;
	}

	public InfoDetail setUrl(String url) {
		this.url = url;
		return this;
	}

	public InfoDetail setSurl(String surl) {
		this.surl = surl;
		return this;
	}

	public InfoDetail setContent(List<InfoContent> content) {
		this.content = content;
		return this;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getSource() {
		return source;
	}

	public String getTime() {
		return time;
	}

	public String getUrl() {
		return url;
	}

	public String getSurl() {
		return surl;
	}

	public List<InfoContent> getContent() {
		return content;
	}
}

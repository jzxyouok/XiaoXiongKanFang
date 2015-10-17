package com.beiing.xiaoxiongkanfang.entity;

public class InfoContent {
	private int type;
	private String value;

	public void setType(int type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public InfoContent(int type, String value) {
		this.type = type;
		this.value = value;
	}

	public InfoContent() {
	}
}

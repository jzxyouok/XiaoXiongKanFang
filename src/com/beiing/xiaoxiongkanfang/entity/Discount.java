package com.beiing.xiaoxiongkanfang.entity;

public class Discount {
	private String hid;
	private String name;
	private String cover;
	private String address;
	private String sellstatus;
	private String tel;
	private String price_pre;
	private String price_value;
	private String price_unit;
	private String discount;
	private String discountendtime;

	public Discount(String hid, String name, String cover, String address,
			String sellstatus, String tel, String price_pre,
			String price_value, String price_unit, String discount,
			String discountendtime) {
		this.hid = hid;
		this.name = name;
		this.cover = cover;
		this.address = address;
		this.sellstatus = sellstatus;
		this.tel = tel;
		this.price_pre = price_pre;
		this.price_value = price_value;
		this.price_unit = price_unit;
		this.discount = discount;
		this.discountendtime = discountendtime;
	}

	public Discount() {
	}

	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSellstatus() {
		return sellstatus;
	}

	public void setSellstatus(String sellstatus) {
		this.sellstatus = sellstatus;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPrice_pre() {
		return price_pre;
	}

	public void setPrice_pre(String price_pre) {
		this.price_pre = price_pre;
	}

	public String getPrice_value() {
		return price_value;
	}

	public void setPrice_value(String price_value) {
		this.price_value = price_value;
	}

	public String getPrice_unit() {
		return price_unit;
	}

	public void setPrice_unit(String price_unit) {
		this.price_unit = price_unit;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getDiscountendtime() {
		return discountendtime;
	}

	public void setDiscountendtime(String discountendtime) {
		this.discountendtime = discountendtime;
	}

}

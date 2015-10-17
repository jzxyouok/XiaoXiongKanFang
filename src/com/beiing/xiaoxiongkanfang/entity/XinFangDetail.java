package com.beiing.xiaoxiongkanfang.entity;

import java.util.List;

public class XinFangDetail {

	private int id;
	private String kftid;
	private String kftrtid;
	private String name;
	private String price;
	private String discount;
	private String label;
	private int wanttosigned;
	private String lat;
	private String lng;
	private String panoid;
	private int heading;
	private int pitch;
	private String tel;
	private String features;
	private String title;
	private String summary;
	private String url; //-->最新动态-WebView
	private String news;
	private String traffic;
	private String around;
	
	private List<Pic> pics;//图集
	
	/**
	 * 效果图类
	 */
	public static class Pic{
		private String type;
		private String name;
		private String covre;
		private int num;
		public Pic(String type, String name, String covre, int num) {
			this.type = type;
			this.name = name;
			this.covre = covre;
			this.num = num;
		}
		public Pic() {
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCovre() {
			return covre;
		}
		public void setCovre(String covre) {
			this.covre = covre;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
	}
	
	private List<String> info;
	
	private List<Price> priceList;
	/**
	 *	历史价格类
	 */
	public static class Price{
		private int price;
		private String tme;
		public Price(int price, String tme) {
			this.price = price;
			this.tme = tme;
		}
		public Price() {
		}
		public int getPrice() {
			return price;
		}
		public void setPrice(int price) {
			this.price = price;
		}
		public String getTme() {
			return tme;
		}
		public void setTme(String tme) {
			this.tme = tme;
		}
		
	}
	
	private List<UnitData> unitlist;
	
	/**
	 * 单元信息类
	 *
	 */
	public static class UnitData{
		private String name;
		private String desc;
		private String url;
		private int postion;
		public UnitData(String name, String desc, String url, int postion) {
			this.name = name;
			this.desc = desc;
			this.url = url;
			this.postion = postion;
		}
		public UnitData() {
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public int getPostion() {
			return postion;
		}
		public void setPostion(int postion) {
			this.postion = postion;
		}
		
	}
	
	private String sellstatus;
	private String istencentdiscount;
	private String commentnum;
	private String price_pre;
	private String price_value;
	private String price_unit;
	private String ktsf_cover;
	private String houseurl;
	private String discountendtime;
	
	private List<Agent> agent;//中介
	
	/**
	 * 中介类
	 */
	public static class Agent{
		String fid;
		String name;
		String avatar;
		String slogan;
		String tel;
		
		public Agent(String fid, String name, String avatar, String slogan,
				String tel) {
			this.fid = fid;
			this.name = name;
			this.avatar = avatar;
			this.slogan = slogan;
			this.tel = tel;
		}
		public Agent() {
		}
		public String getFid() {
			return fid;
		}
		public void setFid(String fid) {
			this.fid = fid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAvatar() {
			return avatar;
		}
		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}
		public String getSlogan() {
			return slogan;
		}
		public void setSlogan(String slogan) {
			this.slogan = slogan;
		}
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
	}

	public XinFangDetail(int id, String kftid, String kftrtid, String name,
			String price, String discount, String label, int wanttosigned,
			String lat, String lng, String panoid, int heading, int pitch,
			String tel, String features, String title, String summary,
			String url, String news, String traffic, String around,
			List<Pic> pics, List<String> info, List<Price> priceList,
			List<UnitData> unitlist, String sellstatus,
			String istencentdiscount, String commentnum, String price_pre,
			String price_value, String price_unit, String ktsf_cover,
			String houseurl, String discountendtime, List<Agent> agent) {
		this.id = id;
		this.kftid = kftid;
		this.kftrtid = kftrtid;
		this.name = name;
		this.price = price;
		this.discount = discount;
		this.label = label;
		this.wanttosigned = wanttosigned;
		this.lat = lat;
		this.lng = lng;
		this.panoid = panoid;
		this.heading = heading;
		this.pitch = pitch;
		this.tel = tel;
		this.features = features;
		this.title = title;
		this.summary = summary;
		this.url = url;
		this.news = news;
		this.traffic = traffic;
		this.around = around;
		this.pics = pics;
		this.info = info;
		this.priceList = priceList;
		this.unitlist = unitlist;
		this.sellstatus = sellstatus;
		this.istencentdiscount = istencentdiscount;
		this.commentnum = commentnum;
		this.price_pre = price_pre;
		this.price_value = price_value;
		this.price_unit = price_unit;
		this.ktsf_cover = ktsf_cover;
		this.houseurl = houseurl;
		this.discountendtime = discountendtime;
		this.agent = agent;
	}

	public XinFangDetail() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKftid() {
		return kftid;
	}

	public void setKftid(String kftid) {
		this.kftid = kftid;
	}

	public String getKftrtid() {
		return kftrtid;
	}

	public void setKftrtid(String kftrtid) {
		this.kftrtid = kftrtid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getWanttosigned() {
		return wanttosigned;
	}

	public void setWanttosigned(int wanttosigned) {
		this.wanttosigned = wanttosigned;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getPanoid() {
		return panoid;
	}

	public void setPanoid(String panoid) {
		this.panoid = panoid;
	}

	public int getHeading() {
		return heading;
	}

	public void setHeading(int heading) {
		this.heading = heading;
	}

	public int getPitch() {
		return pitch;
	}

	public void setPitch(int pitch) {
		this.pitch = pitch;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNews() {
		return news;
	}

	public void setNews(String news) {
		this.news = news;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getAround() {
		return around;
	}

	public void setAround(String around) {
		this.around = around;
	}

	public List<Pic> getPics() {
		return pics;
	}

	public void setPics(List<Pic> pics) {
		this.pics = pics;
	}

	public List<String> getInfo() {
		return info;
	}

	public void setInfo(List<String> info) {
		this.info = info;
	}

	public List<Price> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<Price> priceList) {
		this.priceList = priceList;
	}

	public List<UnitData> getUnitlist() {
		return unitlist;
	}

	public void setUnitlist(List<UnitData> unitlist) {
		this.unitlist = unitlist;
	}

	public String getSellstatus() {
		return sellstatus;
	}

	public void setSellstatus(String sellstatus) {
		this.sellstatus = sellstatus;
	}

	public String getIstencentdiscount() {
		return istencentdiscount;
	}

	public void setIstencentdiscount(String istencentdiscount) {
		this.istencentdiscount = istencentdiscount;
	}

	public String getCommentnum() {
		return commentnum;
	}

	public void setCommentnum(String commentnum) {
		this.commentnum = commentnum;
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

	public String getKtsf_cover() {
		return ktsf_cover;
	}

	public void setKtsf_cover(String ktsf_cover) {
		this.ktsf_cover = ktsf_cover;
	}

	public String getHouseurl() {
		return houseurl;
	}

	public void setHouseurl(String houseurl) {
		this.houseurl = houseurl;
	}

	public String getDiscountendtime() {
		return discountendtime;
	}

	public void setDiscountendtime(String discountendtime) {
		this.discountendtime = discountendtime;
	}

	public List<Agent> getAgent() {
		return agent;
	}

	public void setAgent(List<Agent> agent) {
		this.agent = agent;
	}
	
}













































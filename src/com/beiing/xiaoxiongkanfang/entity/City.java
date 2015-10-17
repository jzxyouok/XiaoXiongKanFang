package com.beiing.xiaoxiongkanfang.entity;

public class City {
	
	public static final int TYPE_LABEL = 0;//标签
	public static final int TYPE_CITY = 1;//城市
	
	public static final String CATE_LABEL = "label";
	public static final String CATE_CITY = "city";
	
	private String category;//属性
	
	public City setCategory(String category){
		this.category = category;
		return this;
	}
	
	public static int getTypeCount() {
		return 2;
	}

	public int getType() {
		if (category.equals("label")) {
			return TYPE_LABEL;
		} else
			return TYPE_CITY;
	}
	
	//---------------------------城市属性
	private String cityid;
	
	private String cityalias;
	
	private String cityname;
	
	private String comparename;
	
	private double center_x;//中心点
	
	private double center_y;//中心点
	
	private double lng;//经度
	
	private double lat;//纬度
	
	private String mobiletype;
	
	private String citypinyin;//拼音显示
	
	private String esfalias;

	public City(String cityid, String cityalias, String comparename,
			double center_x, double center_y, double lng, double lat,
			String mobiletype, String citypinyin, String esfalias) {
		super();
		this.cityid = cityid;
		this.cityalias = cityalias;
		this.comparename = comparename;
		this.center_x = center_x;
		this.center_y = center_y;
		this.lng = lng;
		this.lat = lat;
		this.mobiletype = mobiletype;
		this.citypinyin = citypinyin;
		this.esfalias = esfalias;
	}

	public City() {
	}

	public City setCityid(String cityid) {
		this.cityid = cityid;
		return this;
	}

	public City setCityalias(String cityalias) {
		this.cityalias = cityalias;
		return this;
	}

	public City setCityname(String cityname){
		this.cityname = cityname;
		return this;
	}
	
	public City setComparename(String comparename) {
		this.comparename = comparename;
		return this;
	}

	public City setCenter_x(double center_x) {
		this.center_x = center_x;
		return this;
	}

	public City setCenter_y(double center_y) {
		this.center_y = center_y;
		return this;
	}

	public City setLng(double lng) {
		this.lng = lng;
		return this;
	}

	public City setLat(double lat) {
		this.lat = lat;
		return this;
	}

	public City setMobiletype(String mobiletype) {
		this.mobiletype = mobiletype;
		return this;
	}

	public City setCitypinyin(String citypinyin) {
		this.citypinyin = citypinyin;
		return this;
	}

	public City setEsfalias(String esfalias) {
		this.esfalias = esfalias;
		return this;
	}

	public String getCityid() {
		return cityid;
	}

	public String getCityalias() {
		return cityalias;
	}

	public String getCityname() {
		return cityname;
	}

	public String getComparename() {
		return comparename;
	}

	public double getCenter_x() {
		return center_x;
	}

	public double getCenter_y() {
		return center_y;
	}

	public double getLng() {
		return lng;
	}

	public double getLat() {
		return lat;
	}

	public String getMobiletype() {
		return mobiletype;
	}

	public String getCitypinyin() {
		return citypinyin;
	}

	public String getEsfalias() {
		return esfalias;
	}

	@Override
	public String toString() {
		return "City [cityid=" + cityid + ", cityalias=" + cityalias
				+ ", cityname=" + cityname + ", comparename=" + comparename
				+ ", center_x=" + center_x + ", center_y=" + center_y
				+ ", lng=" + lng + ", lat=" + lat + ", mobiletype="
				+ mobiletype + ", citypinyin=" + citypinyin + ", esfalias="
				+ esfalias + "]";
	}
	
}












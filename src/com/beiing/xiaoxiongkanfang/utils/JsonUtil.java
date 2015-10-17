package com.beiing.xiaoxiongkanfang.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.beiing.xiaoxiongkanfang.entity.Advertisement;
import com.beiing.xiaoxiongkanfang.entity.City;
import com.beiing.xiaoxiongkanfang.entity.Discount;
import com.beiing.xiaoxiongkanfang.entity.InfoComment;
import com.beiing.xiaoxiongkanfang.entity.InfoContent;
import com.beiing.xiaoxiongkanfang.entity.InfoDetail;
import com.beiing.xiaoxiongkanfang.entity.Information;
import com.beiing.xiaoxiongkanfang.entity.XinFangDetail;
import com.beiing.xiaoxiongkanfang.entity.XinFangDetailComment;
import com.beiing.xiaoxiongkanfang.entity.XinFangList;
import com.beiing.xiaoxiongkanfang.entity.XinFangDetail.Agent;
import com.beiing.xiaoxiongkanfang.entity.XinFangDetail.Pic;
import com.beiing.xiaoxiongkanfang.entity.XinFangDetail.Price;
import com.beiing.xiaoxiongkanfang.entity.XinFangDetail.UnitData;
import com.beiing.xiaoxiongkanfang.entity.XinFangList.BookMark;
import com.beiing.xiaoxiongkanfang.entity.XinFangList.XinFang;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonUtil {

	/**
	 * 解析城市列表
	 * 
	 * @throws JSONException
	 */
	public static List<City> parseCity(String jsonStr) throws JSONException {
		List<City> list = new ArrayList<City>();
		Gson gson = new Gson();
		JSONObject json = new JSONObject(jsonStr);
		JSONObject j_cities = json.getJSONObject("cities");

		for (char i = 'A'; i <= 'Z'; i++) {
			if (j_cities.has(i + "")) {
				JSONArray jsonCharArr = j_cities.getJSONArray(i + "");
				if (jsonCharArr != null) {
					City label = new City();
					label.setCityname(i + "").setCategory(City.CATE_LABEL);
					list.add(label);// 标签页
					for (int j = 0; j < jsonCharArr.length(); j++) {
						JSONObject j_city = jsonCharArr.getJSONObject(j);
						City city = gson
								.fromJson(j_city.toString(), City.class);
						city.setCategory(City.CATE_CITY);
						list.add(city);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 解析资讯列表
	 * 
	 * @param jsontr
	 * @return
	 * @throws JSONException
	 */
	public static List<Information> parseInfo(String jsontr)
			throws JSONException {
		List<Information> list = new ArrayList<Information>();
		JSONObject json = new JSONObject(jsontr);
		JSONArray j_data = json.getJSONArray("data");
		TypeToken<List<Information>> token = new TypeToken<List<Information>>() {
		};
		Gson gson = new Gson();
		list = gson.fromJson(j_data.toString(), token.getType());
		return list;
	}

	/**
	 * 解析资讯详情
	 * 
	 * @param jsonStr
	 * @return
	 * @throws JSONException
	 */
	public static InfoDetail parseInfoDetail(String jsonStr)
			throws JSONException {
		InfoDetail detail = null;
		JSONObject json = new JSONObject(jsonStr);
		String id = json.getString("id");
		String title = json.getString("title");
		String source = json.getString("source");
		String time = json.getString("time");
		String url = json.getString("url");
		String surl = json.getString("surl");

		JSONArray j_content = json.getJSONArray("content");
		Log.i("--", "j_content :" + j_content.toString());
		List<InfoContent> content = new ArrayList<InfoContent>();
		for (int i = 0; i < j_content.length(); i++) {
			JSONObject obj = j_content.getJSONObject(i);
			InfoContent con = new InfoContent();
			con.setType(obj.getInt("type"));
			con.setValue(obj.getString("value"));
			content.add(con);
		}
		detail = new InfoDetail(id, title, source, time, url, surl, content);
		return detail;
	}

	/**
	 * 解析广告信息
	 * 
	 * @param jsonStr
	 * @return
	 * @throws JSONException
	 */
	public static List<Advertisement> parseAds(String jsonStr)
			throws JSONException {
		List<Advertisement> list = null;
		JSONObject json = new JSONObject(jsonStr);
		JSONArray j_data = json.getJSONArray("data");
		TypeToken<List<Advertisement>> token = new TypeToken<List<Advertisement>>() {
		};
		Gson gson = new Gson();
		list = gson.fromJson(j_data.toString(), token.getType());
		return list;
	}

	/**
	 * 解析资讯评论
	 * 
	 * @param jsonStr
	 * @return
	 * @throws JSONException
	 */
	public static List<InfoComment> parseInfoComment(String jsonStr)
			throws JSONException {
		List<InfoComment> list = null;
		JSONObject json = new JSONObject(jsonStr);
		JSONObject j_data = json.getJSONObject("data");
		JSONArray j_comments = j_data.getJSONArray("comments");
		Gson gson = new Gson();
		TypeToken<List<InfoComment>> token = new TypeToken<List<InfoComment>>() {
		};
		list = gson.fromJson(j_comments.toString(), token.getType());
		return list;
	}

	/**
	 * 解析新房列表
	 * 
	 * @param jsonStr
	 * @return
	 * @throws JSONException
	 */
	public static XinFangList parseXfList(String jsonStr) throws JSONException {
		XinFangList xinFangList = null;
		JSONObject json = new JSONObject(jsonStr);
		String total = json.getString("total");
		List<XinFang> xfList = new ArrayList<XinFang>();
		Gson gson = new Gson();
		JSONArray j_data = json.getJSONArray("data");
		TypeToken<List<BookMark>> token = new TypeToken<List<BookMark>>() {
		};
		for (int i = 0; i < j_data.length(); i++) {
			XinFang xinFang = null;
			JSONObject j_xf = j_data.getJSONObject(i);
			String fid = j_xf.getString("fid");
			String fcover = j_xf.getString("fcover");
			String fname = j_xf.getString("fname");
			String faddress = j_xf.getString("faddress");
			String fregion = j_xf.getString("fregion");
			String fpricedisplaystr = j_xf.getString("fpricedisplaystr");
			int faroundhighprice = j_xf.getInt("faroundhighprice");
			int faroundlowprice = j_xf.getInt("faroundlowprice");
			int groupbuynum = j_xf.getInt("groupbuynum");
			String lng = j_xf.getString("lng");
			String lat = j_xf.getString("lat");
			String fsellstatus = j_xf.getString("fsellstatus");
			int istencentdiscount = j_xf.getInt("istencentdiscount");
			JSONArray j_bookmark = j_xf.getJSONArray("bookmark");
			List<BookMark> bookMarks = gson.fromJson(j_bookmark.toString(),
					token.getType());
			String price_pre = j_xf.getString("price_pre");
			String price_value = j_xf.getString("price_value");
			String price_unit = j_xf.getString("price_unit");
			String panoid = j_xf.getString("panoid");
			String heading = j_xf.getString("heading");
			String pitch = j_xf.getString("pitch");
			int has_agent = j_xf.getInt("has_agent");
			int hui = j_xf.getInt("hui");
			xinFang = new XinFang(fcover, fname, fid, faddress, fregion,
					fpricedisplaystr, faroundhighprice, faroundlowprice,
					groupbuynum, lng, lat, fsellstatus, istencentdiscount,
					bookMarks, price_pre, price_value, price_unit, panoid,
					heading, pitch, has_agent, hui);

			xfList.add(xinFang);
		}

		xinFangList = new XinFangList(total, xfList);

		return xinFangList;
	}

	/**
	 * 解析新房详情
	 * 
	 * @param jsonStr
	 * @return
	 * @throws JSONException
	 */
	public static XinFangDetail parseXfDetail(String jsonStr)
			throws JSONException {
		XinFangDetail fangDetail = null;
		Gson gson = new Gson();
		TypeToken<List<Pic>> pic_token = new TypeToken<List<Pic>>() {
		};
		TypeToken<List<Price>> price_token = new TypeToken<List<Price>>() {
		};
		TypeToken<List<UnitData>> unit_token = new TypeToken<List<UnitData>>() {
		};
		TypeToken<List<Agent>> agent_token = new TypeToken<List<Agent>>() {
		};
		TypeToken<List<String>> info_token = new TypeToken<List<String>>() {
		};

		JSONObject json = new JSONObject(jsonStr);
		int id = json.getInt("id");
		String kftid = json.getString("kftid");
		String kftrtid = json.getString("kftrtid");
		String name = json.getString("name");
		String price = json.getString("price");
		String discount = json.getString("discount");
		String label = json.getString("label");
		int wanttosigned = json.getInt("wanttosigned");
		String lat = json.getString("lat");
		String lng = json.getString("lng");
		String panoid = json.getString("panoid");
		int heading = json.getInt("heading");
		int pitch = json.getInt("pitch");
		String tel = json.getString("tel");
		String features = json.getString("features");
		String title = json.getString("title");
		String summary = json.getString("summary");
		String url = json.getString("url");
		String news = json.getString("news");
		;
		String traffic = json.getString("traffic");
		String around = json.getString("around");
		JSONArray ja_pic = json.getJSONArray("pic");
		List<Pic> pics = gson.fromJson(ja_pic.toString(), pic_token.getType());
		JSONArray ja_info = json.getJSONArray("info");
		List<String> info = gson.fromJson(ja_info.toString(),
				info_token.getType());
		JSONArray ja_pricelist = json.getJSONArray("pricelist");
		List<Price> priceList = gson.fromJson(ja_pricelist.toString(),
				price_token.getType());
		JSONObject j_unitlist = json.getJSONObject("unitlist");
		JSONArray ja_data = j_unitlist.getJSONArray("data");
		List<UnitData> unitlist = gson.fromJson(ja_data.toString(),
				unit_token.getType());
		String sellstatus = json.getString("sellstatus");
		String istencentdiscount = json.getString("istencentdiscount");
		String commentnum = json.getString("commentnum");
		String price_pre = json.getString("price_pre");
		String price_value = json.getString("price_value");
		String price_unit = json.getString("price_unit");
		String ktsf_cover = json.getString("ktsf_cover");
		String houseurl = json.getString("houseurl");
		String discountendtime = json.getString("discountendtime");
		JSONArray ja_agent = json.getJSONArray("agent");
		List<Agent> agent = gson.fromJson(ja_agent.toString(),
				agent_token.getType());

		fangDetail = new XinFangDetail(id, kftid, kftrtid, name, price,
				discount, label, wanttosigned, lat, lng, panoid, heading,
				pitch, tel, features, title, summary, url, news, traffic,
				around, pics, info, priceList, unitlist, sellstatus,
				istencentdiscount, commentnum, price_pre, price_value,
				price_unit, ktsf_cover, houseurl, discountendtime, agent);
		return fangDetail;
	}

	/**
	 * 解析新房详情评论
	 * 
	 * @param jsonStr
	 * @return
	 * @throws JSONException
	 */
	public static List<XinFangDetailComment> parseXfDetailComments(
			String jsonStr) throws JSONException {
		List<XinFangDetailComment> list = null;
		JSONObject json = new JSONObject(jsonStr);
		JSONObject j_data = json.getJSONObject("data");
		JSONArray ja_info = j_data.getJSONArray("info");
		TypeToken<List<XinFangDetailComment>> token = new TypeToken<List<XinFangDetailComment>>() {
		};
		Gson gson = new Gson();
		list = gson.fromJson(ja_info.toString(), token.getType());
		return list;
	}

	/**
	 * 解析打折优惠
	 * 
	 * @param jsonStr
	 * @return
	 * @throws JSONException
	 */
	public static List<Discount> parseDiscounts(String jsonStr) throws JSONException{
		 List<Discount> list = null;
		 JSONObject json = new JSONObject(jsonStr);
		 JSONArray ja_data = json.getJSONArray("data");
		 TypeToken<List<Discount>> token = new TypeToken<List<Discount>>(){};
		 Gson gson = new Gson();
		 list = gson.fromJson(ja_data.toString(), token.getType());
		 return list;
	}

}






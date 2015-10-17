package com.beiing.xiaoxiongkanfang.entity;

import java.io.Serializable;
import java.util.List;

public class XinFangList {
	private String total;

	private List<XinFang> xfList;

	public static class XinFang implements Serializable {
		String fid;
		String fcover;// ͼƬ
		String fname;// ����
		String faddress;// ��ַ
		String fregion;// ����
		String fpricedisplaystr;// ��ʾ�۸�
		int faroundhighprice;// ��߼۸�
		int faroundlowprice;// ��ͼ۸�
		int groupbuynum;// �Ź�����
		String lng;// ����
		String lat;// γ��
		String fsellstatus;// �������
		int istencentdiscount;// �Ƿ����ۿ�
		List<BookMark> bookMarks;// ��ǩ
		String price_pre;// --����--
		String price_value;// �۸�
		String price_unit;// �۸�λ
		String panoid;
		String heading;
		String pitch;
		int has_agent;// �Ƿ����н�
		int hui;
		public XinFang(String fcover, String fname, String fid,
				String faddress, String fregion, String fpricedisplaystr,
				int faroundhighprice, int faroundlowprice,
				int groupbuynum, String lng, String lat, String fsellstatus,
				int istencentdiscount, List<BookMark> bookMarks,
				String price_pre, String price_value, String price_unit,
				String panoid, String heading, String pitch, int has_agent,
				int hui) {
			super();
			this.fcover = fcover;
			this.fname = fname;
			this.fid = fid;
			this.faddress = faddress;
			this.fregion = fregion;
			this.fpricedisplaystr = fpricedisplaystr;
			this.faroundhighprice = faroundhighprice;
			this.faroundlowprice = faroundlowprice;
			this.groupbuynum = groupbuynum;
			this.lng = lng;
			this.lat = lat;
			this.fsellstatus = fsellstatus;
			this.istencentdiscount = istencentdiscount;
			this.bookMarks = bookMarks;
			this.price_pre = price_pre;
			this.price_value = price_value;
			this.price_unit = price_unit;
			this.panoid = panoid;
			this.heading = heading;
			this.pitch = pitch;
			this.has_agent = has_agent;
			this.hui = hui;
		}
		
		public XinFang() {
			super();
		}

		public void setFid(String fid) {
			this.fid = fid;
		}

		public void setFcover(String fcover) {
			this.fcover = fcover;
		}

		public void setFname(String fname) {
			this.fname = fname;
		}

		public void setFaddress(String faddress) {
			this.faddress = faddress;
		}

		public void setFregion(String fregion) {
			this.fregion = fregion;
		}

		public void setFpricedisplaystr(String fpricedisplaystr) {
			this.fpricedisplaystr = fpricedisplaystr;
		}

		public void setFaroundhighprice(int faroundhighprice) {
			this.faroundhighprice = faroundhighprice;
		}

		public void setFaroundlowprice(int faroundlowprice) {
			this.faroundlowprice = faroundlowprice;
		}

		public void setGroupbuynum(int groupbuynum) {
			this.groupbuynum = groupbuynum;
		}

		public void setLng(String lng) {
			this.lng = lng;
		}

		public void setLat(String lat) {
			this.lat = lat;
		}

		public void setFsellstatus(String fsellstatus) {
			this.fsellstatus = fsellstatus;
		}

		public void setIstencentdiscount(int istencentdiscount) {
			this.istencentdiscount = istencentdiscount;
		}

		public void setBookMarks(List<BookMark> bookMarks) {
			this.bookMarks = bookMarks;
		}

		public void setPrice_pre(String price_pre) {
			this.price_pre = price_pre;
		}

		public void setPrice_value(String price_value) {
			this.price_value = price_value;
		}

		public void setPrice_unit(String price_unit) {
			this.price_unit = price_unit;
		}

		public void setPanoid(String panoid) {
			this.panoid = panoid;
		}

		public void setHeading(String heading) {
			this.heading = heading;
		}

		public void setPitch(String pitch) {
			this.pitch = pitch;
		}

		public void setHas_agent(int has_agent) {
			this.has_agent = has_agent;
		}

		public void setHui(int hui) {
			this.hui = hui;
		}

		public String getFid() {
			return fid;
		}

		public String getFcover() {
			return fcover;
		}

		public String getFname() {
			return fname;
		}

		public String getFaddress() {
			return faddress;
		}

		public String getFregion() {
			return fregion;
		}

		public String getFpricedisplaystr() {
			return fpricedisplaystr;
		}

		public int getFaroundhighprice() {
			return faroundhighprice;
		}

		public int getFaroundlowprice() {
			return faroundlowprice;
		}

		public int getGroupbuynum() {
			return groupbuynum;
		}

		public String getLng() {
			return lng;
		}

		public String getLat() {
			return lat;
		}

		public String getFsellstatus() {
			return fsellstatus;
		}

		public int getIstencentdiscount() {
			return istencentdiscount;
		}

		public List<BookMark> getBookMarks() {
			return bookMarks;
		}

		public String getPrice_pre() {
			return price_pre;
		}

		public String getPrice_value() {
			return price_value;
		}

		public String getPrice_unit() {
			return price_unit;
		}

		public String getPanoid() {
			return panoid;
		}

		public String getHeading() {
			return heading;
		}

		public String getPitch() {
			return pitch;
		}

		public int getHas_agent() {
			return has_agent;
		}

		public int getHui() {
			return hui;
		}

		@Override
		public String toString() {
			return "XinFang [fid=" + fid + ", fcover=" + fcover + ", fname="
					+ fname + ", faddress=" + faddress + ", fregion=" + fregion
					+ ", fpricedisplaystr=" + fpricedisplaystr
					+ ", faroundhighprice=" + faroundhighprice
					+ ", faroundlowprice=" + faroundlowprice + ", groupbuynum="
					+ groupbuynum + ", lng=" + lng + ", lat=" + lat
					+ ", fsellstatus=" + fsellstatus + ", istencentdiscount="
					+ istencentdiscount + ", bookMarks=" + bookMarks
					+ ", price_pre=" + price_pre + ", price_value="
					+ price_value + ", price_unit=" + price_unit + ", panoid="
					+ panoid + ", heading=" + heading + ", pitch=" + pitch
					+ ", has_agent=" + has_agent + ", hui=" + hui + "]";
		}
		
	}

	public static class BookMark {
		String tag;// ��ǩ
		int type;// ����
		public BookMark(String tag, int type) {
			super();
			this.tag = tag;
			this.type = type;
		}
		public BookMark() {
			super();
		}
		public String getTag() {
			return tag;
		}
		public void setTag(String tag) {
			this.tag = tag;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		
	}
	
	

	public XinFangList(String total, List<XinFang> xfList) {
		this.total = total;
		this.xfList = xfList;
	}
	
	

	public XinFangList() {
	}



	public void setTotal(String total) {
		this.total = total;
	}

	public void setXfList(List<XinFang> xfList) {
		this.xfList = xfList;
	}

	public String getTotal() {
		return total;
	}

	public List<XinFang> getXfList() {
		return xfList;
	}



	@Override
	public String toString() {
		return "XinFangList [total=" + total + ", xfList=" + xfList + "]";
	}
	

}

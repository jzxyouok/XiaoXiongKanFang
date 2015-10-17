package com.beiing.xiaoxiongkanfang.entity;

/**
 * ×ÊÑ¶ÆÀÂÛ
 * 
 * @author Administrator
 * 
 */
public class InfoComment {
	private String id;
	private String time;
	private String timestamp;
	private String content;
	private String nick;
	private String head;
	private String region;
	private int isreply;
	private String replynick;
	private String replycontent;
	
	
	public String getTimeLoc(){
		return region + " " + time;
	}
	
	public String getReply(){
		return replynick + ":" + replycontent;
	}

	public InfoComment(String id, String time, String timestamp,
			String content, String nick, String head, String region,
			int isreply, String replynick, String replycontent) {
		this.id = id;
		this.time = time;
		this.timestamp = timestamp;
		this.content = content;
		this.nick = nick;
		this.head = head;
		this.region = region;
		this.isreply = isreply;
		this.replynick = replynick;
		this.replycontent = replycontent;
	}

	public InfoComment() {
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setIsreply(int isreply) {
		this.isreply = isreply;
	}

	public void setReplynick(String replynick) {
		this.replynick = replynick;
	}

	public void setReplycontent(String replycontent) {
		this.replycontent = replycontent;
	}

	public String getId() {
		return id;
	}

	public String getTime() {
		return time;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getContent() {
		return content;
	}

	public String getNick() {
		return nick;
	}

	public String getHead() {
		return head;
	}

	public String getRegion() {
		return region;
	}

	public int getIsreply() {
		return isreply;
	}

	public String getReplynick() {
		return replynick;
	}

	public String getReplycontent() {
		return replycontent;
	}

}

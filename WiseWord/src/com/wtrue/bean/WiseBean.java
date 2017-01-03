package com.wtrue.bean;

import java.io.Serializable;

public class WiseBean implements Serializable{
//	 "id": "5869bb756e36a8dec7414d6e",
//     "title": "最近天冷，我给妹妹买",
//     "text": "最近天冷，我给妹妹买了条围巾，她很开心，连说谢谢。\r\n我说你应该谢谢你嫂子，她惊讶道：“哥，你谈女朋友了？”\r\n我说：“没有，你应该感谢她一直都没出现，我才有钱给你买东西。”",
//     "type": 1,
//     "ct": "2017-01-02 10:31:17.215"
	private String id;
	private String title;
	private String text;
	private int type;
	private String ct;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCt() {
		return ct;
	}
	public void setCt(String ct) {
		this.ct = ct;
	}
	
}

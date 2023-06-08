package com.koreaIT.java.BAM.dto;

public class Article{
	public int id;
	public String regDate;
	public int memberId;
	public String title;
	public String body;
	public int count;
	
	public Article(int id,String regDate,int memberId, String title, String body){
		this(id, regDate,memberId, title, body, 0);
	}
	
	public Article(int id,String regDate,int memberId, String title, String body, int count){
		this.id = id;
		this.regDate = regDate;
		this.memberId = memberId;
		this.title = title;
		this.body = body;
		this.count = count;
	}
}

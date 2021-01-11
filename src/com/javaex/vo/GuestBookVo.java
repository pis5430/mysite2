package com.javaex.vo;

public class GuestBookVo {
	

	//필드
	public int no;
	public String name;
	public String password;
	public String content;
	public String date;
	
	//생성자
	public GuestBookVo() {}
	
	public GuestBookVo(String password) {
		this.password = password;
	}
	
	
	public GuestBookVo(int no,String password) {
		this.no = no;
		this.password = password;
	}
	
	public GuestBookVo(String name, String password, String content) {
		this.name = name;
		this.password = password;
		this.content = content;
	}
			

	public GuestBookVo(String name, String password, String content, String date) {
		this.name = name;
		this.password = password;
		this.content = content;
		this.date = date;
	}
			
	
	
	
	public GuestBookVo(int no, String name, String password, String content, String date) {
		this.no = no;
		this.name = name;
		this.password = password;
		this.content = content;
		this.date = date;
	}
	
	
	
	
	//메소드 getter setter

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}


	//일반메소드
	
	@Override
	public String toString() {
		return "GuestBookVo [no=" + no + ", name=" + name + ", password=" + password + ", content=" + content + ", date="
				+ date + "]";
	}



	
	
}

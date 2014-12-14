package com.nonfamous.tang.dao.query;

import java.util.Date;

public class NewsQuery extends QueryBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4313136504546361104L;

	private String newsId;

	private String newsTitle;
	
	private String newsType;
	
	private String memberId;

	private Date gmtAbandon;

	private String newsStatus;
	
	private String creator;
	
	private String nick;
	
	private String phone;

	public String getNewsStatus() {
		return newsStatus;
	}

	public void setNewsStatus(String newsStatus) {
		this.newsStatus = newsStatus;
	}

	public Date getGmtAbandon() {
		return gmtAbandon;
	}

	public void setGmtAbandon(Date gmtAbandon) {
		this.gmtAbandon = gmtAbandon;
	}
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

	
}

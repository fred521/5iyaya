package com.nonfamous.tang.domain;

import com.nonfamous.tang.domain.base.DomainBase;
/**
 * @author: fred
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: NewsBaseInfo.java,v 1.2 2008/11/29 02:53:14 fred Exp $
 */
public class NewsBaseInfo extends DomainBase{

	private static final long serialVersionUID = 5743129589700578124L;
	//字段描述:商业资讯编号
	private java.lang.String newsId;
	//字段描述:商业资讯标题
	private java.lang.String newsTitle;
	//字段描述:商业资讯所属分类
	private java.lang.String newsType;
	//字段描述:联系电话
	private java.lang.String phone;
	//字段描述:信息所属会员
	private java.lang.String memberId;
	//字段描述:昵称：登陆使用，全局唯一
	private java.lang.String nick;
	//字段描述:商业资讯状态:N 正常、P过期
	private java.lang.String newsStatus;
	//字段描述:浏览次数
	private java.lang.Long viewCount;
	//字段描述:信息过期月数
	private java.lang.Long abandonDays;
	//字段描述:根据商品过期月数和商品创建时间计算得到
	private java.util.Date gmtAbandon;
	//字段描述:
	private java.util.Date gmtCreate;
	//字段描述:
	private java.lang.String creator;
	//字段描述:
	private java.util.Date gmtModify;
	//字段描述:
	private java.lang.String modifier;
	//News description
	private NewsContent content= new NewsContent();
	
	private NewsType newsTypeDO= new NewsType();
	
	public final static String NORMAL_STATUS = "N";

	public final static String EXPIRE_STATUS = "P";
	
	public final static String WAITING_STATUS="W";
	
	public final static String DELETE_STATUS="D";

	public void setNewsId(java.lang.String  newsId){
		this.newsId = newsId;	
	}
	
	public java.lang.String getNewsId(){
		return this.newsId;	
	}
	public void setNewsTitle(java.lang.String  newsTitle){
		this.newsTitle = newsTitle;	
	}
	
	public java.lang.String getNewsTitle(){
		return this.newsTitle;	
	}
	public void setNewsType(java.lang.String  newsType){
		this.newsType = newsType;	
	}
	
	public java.lang.String getNewsType(){
		return this.newsType;	
	}
	public void setPhone(java.lang.String  phone){
		this.phone = phone;	
	}
	
	public java.lang.String getPhone(){
		return this.phone;	
	}
	public void setMemberId(java.lang.String  memberId){
		this.memberId = memberId;	
	}
	
	public java.lang.String getMemberId(){
		return this.memberId;	
	}
	public void setNick(java.lang.String  nick){
		this.nick = nick;	
	}
	
	public java.lang.String getNick(){
		return this.nick;	
	}
	public void setNewsStatus(java.lang.String  newsStatus){
		this.newsStatus = newsStatus;	
	}
	
	public java.lang.String getNewsStatus(){
		return this.newsStatus;	
	}
	public void setViewCount(java.lang.Long  viewCount){
		this.viewCount = viewCount;	
	}
	
	public java.lang.Long getViewCount(){
		return this.viewCount;	
	}
	public void setAbandonDays(java.lang.Long  abandonDays){
		this.abandonDays = abandonDays;	
	}
	
	public java.lang.Long getAbandonDays(){
		return this.abandonDays;	
	}
	public void setGmtAbandon(java.util.Date  gmtAbandon){
		this.gmtAbandon = gmtAbandon;	
	}
	
	public java.util.Date getGmtAbandon(){
		return this.gmtAbandon;	
	}
	public void setGmtCreate(java.util.Date  gmtCreate){
		this.gmtCreate = gmtCreate;	
	}
	
	public java.util.Date getGmtCreate(){
		return this.gmtCreate;	
	}
	public void setCreator(java.lang.String  creator){
		this.creator = creator;	
	}
	
	public java.lang.String getCreator(){
		return this.creator;	
	}
	public void setGmtModify(java.util.Date  gmtModify){
		this.gmtModify = gmtModify;	
	}
	
	public java.util.Date getGmtModify(){
		return this.gmtModify;	
	}
	public void setModifier(java.lang.String  modifier){
		this.modifier = modifier;	
	}
	
	public java.lang.String getModifier(){
		return this.modifier;	
	}

	public NewsContent getContent() {
		return content;
	}
	public NewsType getNewsTypeDO() {
		return newsTypeDO;
	}

	public void setNewsTypeDO(NewsType newsTypeDO) {
		this.newsTypeDO = newsTypeDO;
	}

	public void setContent(NewsContent content) {
		this.content = content;
	}
	


	

}
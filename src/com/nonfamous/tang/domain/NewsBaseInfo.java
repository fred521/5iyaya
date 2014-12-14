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
	//�ֶ�����:��ҵ��Ѷ���
	private java.lang.String newsId;
	//�ֶ�����:��ҵ��Ѷ����
	private java.lang.String newsTitle;
	//�ֶ�����:��ҵ��Ѷ��������
	private java.lang.String newsType;
	//�ֶ�����:��ϵ�绰
	private java.lang.String phone;
	//�ֶ�����:��Ϣ������Ա
	private java.lang.String memberId;
	//�ֶ�����:�ǳƣ���½ʹ�ã�ȫ��Ψһ
	private java.lang.String nick;
	//�ֶ�����:��ҵ��Ѷ״̬:N ������P����
	private java.lang.String newsStatus;
	//�ֶ�����:�������
	private java.lang.Long viewCount;
	//�ֶ�����:��Ϣ��������
	private java.lang.Long abandonDays;
	//�ֶ�����:������Ʒ������������Ʒ����ʱ�����õ�
	private java.util.Date gmtAbandon;
	//�ֶ�����:
	private java.util.Date gmtCreate;
	//�ֶ�����:
	private java.lang.String creator;
	//�ֶ�����:
	private java.util.Date gmtModify;
	//�ֶ�����:
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
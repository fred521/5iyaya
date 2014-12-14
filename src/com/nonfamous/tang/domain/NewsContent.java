package com.nonfamous.tang.domain;

import com.nonfamous.commom.util.HtmlUtils;
import com.nonfamous.tang.domain.base.DomainBase;
/**
 * @author: fred
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: NewsContent.java,v 1.2 2008/11/29 02:53:14 fred Exp $
 */
public class NewsContent extends DomainBase{

	private static final long serialVersionUID = -6121514696033299765L;
	//字段描述:商业资讯编号
	private java.lang.String newsId;
	//字段描述:商业咨询内容
	private java.lang.String content;

	public void setNewsId(java.lang.String  newsId){
		this.newsId = newsId;	
	}
	
	public java.lang.String getNewsId(){
		return this.newsId;	
	}
	public void setContent(java.lang.String  content){
		this.content = content;	
	}
	
	public java.lang.String getContent(){
		return this.content;	
	}
	public String getContentWithoutHtml(){
		return HtmlUtils.parseHtml(this.getContent());
	}
}
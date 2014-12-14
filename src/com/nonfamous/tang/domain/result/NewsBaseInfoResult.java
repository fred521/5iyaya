package com.nonfamous.tang.domain.result;

/**
 * <p>
 * Include all the information related to the News result
 * </p>
 * 
 * @author:fred
 * @version $Id: NewsBaseInfoResult.java,v 1.1 2008/07/11 00:47:06 fred Exp $
 */
public class NewsBaseInfoResult extends ResultBase {

	private static final long serialVersionUID = 7623393416413453518L;
	
	private String newsId;

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	
	
	/***************************************Result for updating NewsBaseInfo*************************************/
	
	/** 没有该新闻信息 **/
	public static final String ERROR_NO_NEWS = "ERROR_NO_NEWS";
	
	
	/***************************************Result for adding NewsBaseInfo*************************************/
	
	/** 已经存在该新闻**/ 
	
	public static final String ERROR_NEWS_EXIST = "ERROR_NEWS_EXIST";
	

	/** 校验码错误 *//*
	public static final String ERROR_INVALID_CHECK_CODE = "ERROR_INVALID_CHECK_CODE";

	*//** 用户手机已激活 *//*
	public static final String ERROR_HAS_ACTIVE = "ERROR_HAS_ACTIVE";

	*//** 没有该注册用户 *//*
	public static final String ERROR_NO_MEMBER = "ERROR_NO_MEMBER";

	*//** 不允许更改，具体原因以后再扩展 *//*
	public static final String ERROR_UPDATE_NOT_ALLOWED = "ERROR_UPDATE_NOT_ALLOWED";*/
	
	
}

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
	
	/** û�и�������Ϣ **/
	public static final String ERROR_NO_NEWS = "ERROR_NO_NEWS";
	
	
	/***************************************Result for adding NewsBaseInfo*************************************/
	
	/** �Ѿ����ڸ�����**/ 
	
	public static final String ERROR_NEWS_EXIST = "ERROR_NEWS_EXIST";
	

	/** У������� *//*
	public static final String ERROR_INVALID_CHECK_CODE = "ERROR_INVALID_CHECK_CODE";

	*//** �û��ֻ��Ѽ��� *//*
	public static final String ERROR_HAS_ACTIVE = "ERROR_HAS_ACTIVE";

	*//** û�и�ע���û� *//*
	public static final String ERROR_NO_MEMBER = "ERROR_NO_MEMBER";

	*//** ��������ģ�����ԭ���Ժ�����չ *//*
	public static final String ERROR_UPDATE_NOT_ALLOWED = "ERROR_UPDATE_NOT_ALLOWED";*/
	
	
}

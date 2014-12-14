package com.nonfamous.tang.service.search;

import com.nonfamous.tang.dao.query.SearchNewsQuery;

/**
 * 
 * @author victor
 * 
 */
public interface NewsIndexService {
	/**
	 * ���½�����Ѷ������ȡ���ݵĿ�ʼʱ��Ӷ�ʱ��¼���ݱ��ж�ȡ
	 * */
	public void createNewsIndex();
	/**
	 * ���½�����Ѷ������ȡ���ݵĿ�ʼʱ��Ϊ2000��
	 * */
	public void rebuildNewsIndex();
	/**
	 * ������Ѷ����
	 * */
	public void updateNewsIndex();
	/**
	 * ������Ѷ��������Ѷ����
	 * */
	public SearchNewsQuery findNews(SearchNewsQuery query);
	
}

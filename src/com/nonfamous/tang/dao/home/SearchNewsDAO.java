package com.nonfamous.tang.dao.home;


import com.nonfamous.tang.dao.query.SearchNewsQuery;

/**
 * 
 * @author victor
 * 
 */
public interface SearchNewsDAO {
	/**
	 * ������Ѷ��������Ѷ����
	 * */
	public SearchNewsQuery findAllNews(SearchNewsQuery query);
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
}

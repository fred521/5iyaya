package com.nonfamous.tang.dao.home;


import com.nonfamous.tang.dao.query.SearchMemberShopQuery;

/**
 * 
 * @author victor
 * 
 */
public interface SearchMemberShopDAO {
	/**
	 * ������Ѷ��������Ѷ����
	 * */
	public SearchMemberShopQuery findAllMemberShop(SearchMemberShopQuery query);
	/**
	 * ���½�����Ա����������ȡ���ݵĿ�ʼʱ��Ӷ�ʱ��¼���ݱ��ж�ȡ
	 * */
	public void createMemberShopIndex();
	/**
	 * ���½�����Ա����������ȡ���ݵĿ�ʼʱ��Ϊ2000��
	 * */
	public void rebuildMemberShopIndex();
	/**
	 * ���»�Ա��������
	 * */
	public void updateMemberShopIndex();
}

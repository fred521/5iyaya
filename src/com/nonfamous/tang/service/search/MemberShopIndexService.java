package com.nonfamous.tang.service.search;

import com.nonfamous.tang.dao.query.SearchMemberShopQuery;

/**
 * 
 * @author victor
 * 
 */
public interface MemberShopIndexService {
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
	/**
	 * ������Ա���̣����ػ�Ա���̼���
	 * */
	public SearchMemberShopQuery findMemberShop(SearchMemberShopQuery query);
	
}

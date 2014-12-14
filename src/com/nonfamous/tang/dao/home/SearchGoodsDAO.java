package com.nonfamous.tang.dao.home;


import com.nonfamous.tang.dao.query.SearchGoodsQuery;

/**
 * 
 * @author victor
 * 
 */
public interface SearchGoodsDAO {
	/**
	 * ������Ʒ��������Ʒ����
	 * */
	public SearchGoodsQuery findAllGoods(SearchGoodsQuery query);
	/**
	 * ���½�����Ʒ������ȡ���ݵĿ�ʼʱ��Ӷ�ʱ��¼���ݱ��ж�ȡ
	 * */
	public void createGoodsIndex();
	/**
	 * ��ȫ���½�����Ʒ������ȡ���ݵĿ�ʼʱ��Ϊ2000��
	 * */
	public void rebuildGoodsIndex();
	/**
	 * ������Ʒ����
	 * */
	public void updateGoodsIndex();
	
	public void setIndexFilePath(String id);
	/**
	 * ������Ʒ��������Ч��Ʒ����
	 * */
	public SearchGoodsQuery findEffectGoods(SearchGoodsQuery query);
}

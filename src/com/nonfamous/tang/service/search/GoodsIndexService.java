package com.nonfamous.tang.service.search;

import com.nonfamous.tang.dao.query.SearchGoodsQuery;

/**
 * 
 * @author victor
 * 
 */
public interface GoodsIndexService {
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
	/**
	 * ������Ʒ��������Ʒ����
	 * */
	public SearchGoodsQuery findGoods(SearchGoodsQuery query);
	/**
	 * ȡ������Ч��Ʒ�ĸ���
	 * */
	public SearchGoodsQuery findEffectGoods(SearchGoodsQuery query);
	
}

package com.nonfamous.tang.service.search;

import com.nonfamous.tang.dao.query.SearchGoodsQuery;

public interface BabyIndexService {
	/**
	 * ���½�����Ʒ������ȡ���ݵĿ�ʼʱ��Ӷ�ʱ��¼���ݱ��ж�ȡ
	 * */
	public void createBabyIndex();
	/**
	 * ��ȫ���½�����Ʒ������ȡ���ݵĿ�ʼʱ��Ϊ2000��
	 * */
	public void rebuildBabyIndex();
	/**
	 * ������Ʒ����
	 * */
	public void updateBabyIndex();
	/**
	 * ������Ʒ��������Ʒ����
	 * */
	public SearchGoodsQuery findBaby(SearchGoodsQuery query);
	/**
	 * ȡ������Ч��Ʒ�ĸ���
	 * */
	public SearchGoodsQuery findEffectBaby(SearchGoodsQuery query);
	
}
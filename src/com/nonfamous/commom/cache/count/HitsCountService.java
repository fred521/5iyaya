package com.nonfamous.commom.cache.count;

/**
 * ������ֵ���л��棬����˵��̳�����ӵ����������Ʒ����������ȡ� ���������ֵ������һ����д��
 * 
 * @author fred
 * @version $Id: HitsCountService.java,v 1.2 2008/11/29 02:51:49 fred Exp $
 */
public interface HitsCountService<T> {

	/**
	 * ��id��������һ��
	 * 
	 * @param id
	 */
	public void addHitOnce(T id);

	/**
	 * ��id��������i��
	 * 
	 * @param id
	 * @param count
	 *            ���� > 0
	 */
	public void addHits(T id, int count);

	/**
	 * ˢ�»���
	 * 
	 */
	public void flush();


}

package com.nonfamous.commom.cache;

/**
 * @author fish
 * @version $Id: CompactCache.java,v 1.1 2008/07/11 00:47:08 fred Exp $
 */
public interface CompactCache {
	/**
	 * ��һ��key - value ����cache
	 * 
	 * @param key
	 *            ����Ϊnull
	 * @param value
	 *            ���Ϊnull�����൱�� remove(key)
	 */
	public void put(Object key, Object value);

	/**
	 * ��cache�еõ�key��Ӧ��ֵ
	 * 
	 * @param key
	 *            ����Ϊnull
	 * @return ���cache�����ڣ������Ѿ����ڣ��򷵻�null
	 */
	public Object get(Object key);

	/**
	 * ���cache�е���������
	 * 
	 */
	public void clean();
}

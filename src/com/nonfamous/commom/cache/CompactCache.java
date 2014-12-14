package com.nonfamous.commom.cache;

/**
 * @author fish
 * @version $Id: CompactCache.java,v 1.1 2008/07/11 00:47:08 fred Exp $
 */
public interface CompactCache {
	/**
	 * 把一对key - value 放入cache
	 * 
	 * @param key
	 *            不能为null
	 * @param value
	 *            如果为null，则相当于 remove(key)
	 */
	public void put(Object key, Object value);

	/**
	 * 从cache中得到key对应的值
	 * 
	 * @param key
	 *            不能为null
	 * @return 如果cache不存在，或者已经过期，则返回null
	 */
	public Object get(Object key);

	/**
	 * 清楚cache中的所有数据
	 * 
	 */
	public void clean();
}

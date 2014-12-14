package com.nonfamous.commom.cache.count;

/**
 * 对命中值进行缓存，比如说论坛上帖子的浏览数，商品的浏览数量等。 在满足最大值条件后一次性写出
 * 
 * @author fred
 * @version $Id: HitsCountService.java,v 1.2 2008/11/29 02:51:49 fred Exp $
 */
public interface HitsCountService<T> {

	/**
	 * 此id被命中了一次
	 * 
	 * @param id
	 */
	public void addHitOnce(T id);

	/**
	 * 此id被命中了i次
	 * 
	 * @param id
	 * @param count
	 *            必需 > 0
	 */
	public void addHits(T id, int count);

	/**
	 * 刷新缓存
	 * 
	 */
	public void flush();


}

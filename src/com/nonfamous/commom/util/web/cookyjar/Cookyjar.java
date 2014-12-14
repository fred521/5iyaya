/**
 * 
 */
package com.nonfamous.commom.util.web.cookyjar;

import java.util.Map;

/**
 * @author fred 对http中的cookie的包装,得到方法:
 *         request.getAttrubute(Cookyjar.CookyjarInRequest)
 *         注意,如果key没有在配置中设置过,则视为无效
 */
public interface Cookyjar {
	public static final String CookyjarInRequest = "cookyjar";

	/**
	 * 设置一个值,如果已存在,则覆盖,如果value=null,则相当于 remove(key)
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value);

	public void set(String key, Long value);

	/**
	 * 设置一个值,如果已存在,则覆盖,如果value=null,则相当于 remove(key)
	 * 
	 * @param key
	 * @param value
	 * @param expiry
	 *            an integer specifying the maximum age of the cookie in
	 *            seconds; if negative, means the cookie is not stored; if zero,
	 *            deletes the cookie
	 */
	public void set(String key, String value, int expiry);

	public void set(String key, Long value, int expiry);

	/**
	 * 得到一个值
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key);

	/**
	 * 得到所有的值对
	 * 
	 * @return
	 */
	public Map<String, String> getAll();

	/**
	 * 删除一个值
	 * 
	 * @param key
	 * @return
	 */
	public String remove(String key);

	/**
	 * 
	 * 将所有值都清楚
	 */
	public void clean();
}

/**
 * 
 */
package com.nonfamous.commom.util.web.cookyjar;

import java.util.Map;

/**
 * @author fred ��http�е�cookie�İ�װ,�õ�����:
 *         request.getAttrubute(Cookyjar.CookyjarInRequest)
 *         ע��,���keyû�������������ù�,����Ϊ��Ч
 */
public interface Cookyjar {
	public static final String CookyjarInRequest = "cookyjar";

	/**
	 * ����һ��ֵ,����Ѵ���,�򸲸�,���value=null,���൱�� remove(key)
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value);

	public void set(String key, Long value);

	/**
	 * ����һ��ֵ,����Ѵ���,�򸲸�,���value=null,���൱�� remove(key)
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
	 * �õ�һ��ֵ
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key);

	/**
	 * �õ����е�ֵ��
	 * 
	 * @return
	 */
	public Map<String, String> getAll();

	/**
	 * ɾ��һ��ֵ
	 * 
	 * @param key
	 * @return
	 */
	public String remove(String key);

	/**
	 * 
	 * ������ֵ�����
	 */
	public void clean();
}

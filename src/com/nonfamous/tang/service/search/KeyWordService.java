package com.nonfamous.tang.service.search;

import java.util.List;

/**
 * 
 * @author fish
 * @version $Id: KeyWordService.java,v 1.1 2008/07/11 00:47:12 fred Exp $
 */
public interface KeyWordService {
	/**
	 * 关键词命中,增加命中值,如果没有,则新增 注意:此方法为异步调用
	 * 
	 * @param keyWord
	 *            不能为null或者blank
	 * @param keyType
	 *            不能为null
	 */
	public void keyWordHit(String keyWord, String keyType);

	/**
	 * 批量的关键词命中 注意:此方法为异步调用
	 * 
	 * @param keyWords
	 *            不能为null
	 * @param keyType
	 *            不能为null
	 */
	public void keyWordHit(List<String> keyWords, String keyType);
}

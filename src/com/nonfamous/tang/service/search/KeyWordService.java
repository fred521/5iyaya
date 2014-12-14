package com.nonfamous.tang.service.search;

import java.util.List;

/**
 * 
 * @author fish
 * @version $Id: KeyWordService.java,v 1.1 2008/07/11 00:47:12 fred Exp $
 */
public interface KeyWordService {
	/**
	 * �ؼ�������,��������ֵ,���û��,������ ע��:�˷���Ϊ�첽����
	 * 
	 * @param keyWord
	 *            ����Ϊnull����blank
	 * @param keyType
	 *            ����Ϊnull
	 */
	public void keyWordHit(String keyWord, String keyType);

	/**
	 * �����Ĺؼ������� ע��:�˷���Ϊ�첽����
	 * 
	 * @param keyWords
	 *            ����Ϊnull
	 * @param keyType
	 *            ����Ϊnull
	 */
	public void keyWordHit(List<String> keyWords, String keyType);
}

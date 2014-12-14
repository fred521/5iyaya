package com.nonfamous.tang.dao.query;

import java.util.List;

public abstract class SearchQueryBase extends QueryBase {
	protected String keyWords;// ��������

	protected String sort;// �����ֶ�����

	protected boolean reverse = false;// ����ʽ,true��ʾ��������Ĭ��Ϊfalse

	protected List<String> keyWordList;// ��¼�ؼ��ֵ��б�

	/**
	 * �õ�����ʱʹ�õĹؼ��֣�һЩ�����߼��ڴ�
	 * 
	 * @return
	 */
	public String getKeyWordsForSearch() {
		if (this.keyWords == null) {
			return null;
		}
		if (this.keyWords.length() == 1) {
			return this.keyWords + '?';// ��Ϊ����2Ԫ�з֣����Ե����������޷����������Ϻ�׺��?����
		}
		return this.keyWords;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		if (keyWords != null) {
			keyWords = keyWords.trim();
		}
		this.keyWords = keyWords;
	}

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public List<String> getKeyWordList() {
		return keyWordList;
	}

	public void setKeyWordList(List<String> keyWordList) {
		this.keyWordList = keyWordList;
	}
}

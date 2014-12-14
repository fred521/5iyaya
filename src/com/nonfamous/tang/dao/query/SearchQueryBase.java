package com.nonfamous.tang.dao.query;

import java.util.List;

public abstract class SearchQueryBase extends QueryBase {
	protected String keyWords;// 搜索内容

	protected String sort;// 排序字段名称

	protected boolean reverse = false;// 排序方式,true表示倒置排序，默认为false

	protected List<String> keyWordList;// 记录关键字的列表

	/**
	 * 得到搜索时使用的关键字，一些处理逻辑在此
	 * 
	 * @return
	 */
	public String getKeyWordsForSearch() {
		if (this.keyWords == null) {
			return null;
		}
		if (this.keyWords.length() == 1) {
			return this.keyWords + '?';// 因为采用2元切分，所以单个中文字无法搜索，加上后缀的?即可
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

package com.nonfamous.tang.service.search.pojo;

import java.util.List;
import java.util.concurrent.Executor;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.service.POJOServiceBase;
import com.nonfamous.tang.dao.home.SearchKeyWordDAO;
import com.nonfamous.tang.domain.SearchKeyWord;
import com.nonfamous.tang.service.search.KeyWordService;

public class POJOKeyWordService extends POJOServiceBase implements
		KeyWordService {
	private Executor executor;

	private SearchKeyWordDAO searchKeyWordDAO;

	public void keyWordHit(final String keyWord, final String keyType) {
		if (StringUtils.isBlank(keyWord)) {
			throw new NullPointerException("key word can't be null or blank");
		}
		if (keyType == null) {
			throw new NullPointerException("key type can't be null");
		}
		this.executor.execute(new Runnable() {

			public void run() {
				SearchKeyWord searchKeyWord = new SearchKeyWord();
				searchKeyWord.setKeyName(keyWord);
				searchKeyWord.setKeyType(keyType);
				int row = searchKeyWordDAO
						.updateSearchKeyWordCount(searchKeyWord);
				if (row == 0) {
					searchKeyWordDAO.addSearchKeyWord(searchKeyWord);
				}
			}
		});

	}

	public void keyWordHit(List<String> keyWords, String keyType) {
		if (keyWords == null) {
			throw new NullPointerException("key word can't be null");
		}
		if (keyType == null) {
			throw new NullPointerException("key type can't be null");
		}
		for (String keyWord : keyWords) {
			if (StringUtils.isNotBlank(keyWord)) {
				keyWordHit(keyWord, keyType);
			}
		}
	}

	public Executor getExecutor() {
		return executor;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	public SearchKeyWordDAO getSearchKeyWordDAO() {
		return searchKeyWordDAO;
	}

	public void setSearchKeyWordDAO(SearchKeyWordDAO searchKeyWordDAO) {
		this.searchKeyWordDAO = searchKeyWordDAO;
	}

}

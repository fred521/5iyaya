package com.nonfamous.tang.dao.home.cache;

import java.util.Collections;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nonfamous.commom.cache.CompactCache;
import com.nonfamous.tang.dao.home.SearchKeyWordDAO;
import com.nonfamous.tang.domain.SearchKeyWord;

public class CachedSearchKeyWordDAO implements SearchKeyWordDAO {

	private SearchKeyWordDAO target;

	private CompactCache cache;

	private int cacheKeyWordSize = 10;

	public int getCacheKeyWordSize() {
		return cacheKeyWordSize;
	}

	public void setCacheKeyWordSize(int cacheKeyWordSize) {
		this.cacheKeyWordSize = cacheKeyWordSize;
	}

	public CompactCache getCache() {
		return cache;
	}

	public void setCache(CompactCache cache) {
		this.cache = cache;
	}

	public SearchKeyWordDAO getTarget() {
		return target;
	}

	public void setTarget(SearchKeyWordDAO target) {
		this.target = target;
	}

	public SearchKeyWord addSearchKeyWord(SearchKeyWord searchKeyWord)
			throws DataAccessException {
		return this.target.addSearchKeyWord(searchKeyWord);
	}

	@SuppressWarnings("unchecked")
	public List<SearchKeyWord> getHotKeyWord(int num, String keyType)
			throws DataAccessException {
		if (keyType == null) {
			throw new NullPointerException("key type can't be null");
		}
		if (num <= 0) {
			return Collections.EMPTY_LIST;
		}
		List<SearchKeyWord> back = (List<SearchKeyWord>) this.cache
				.get(keyType);
		if (back == null) {
			back = this.target.getHotKeyWord(this.cacheKeyWordSize, keyType);
			this.cache.put(keyType, back);
		}
		if (back == null) {
			return Collections.EMPTY_LIST;
		}
		if (back.size() > num) {
			back.subList(0, num);
		}
		return back;
	}

	public SearchKeyWord getKeyWordByWord(String word)
			throws DataAccessException {
		return this.target.getKeyWordByWord(word);
	}

	public int updateSearchKeyWordCount(SearchKeyWord searchKeyWord) {
		return this.target.updateSearchKeyWordCount(searchKeyWord);
	}

}

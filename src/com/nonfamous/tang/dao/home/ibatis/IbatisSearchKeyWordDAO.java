package com.nonfamous.tang.dao.home.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.tang.dao.home.SearchKeyWordDAO;
import com.nonfamous.tang.domain.SearchKeyWord;

/**
 * 
 * @author victor
 * 
 */
public class IbatisSearchKeyWordDAO extends SqlMapClientDaoSupport implements
		SearchKeyWordDAO {
	public static final String SPACE = "SEARCHKEYWORD_SPACE.";

	public SearchKeyWord addSearchKeyWord(SearchKeyWord searchKeyWord)
			throws DataAccessException {
		if (searchKeyWord == null)
			throw new NullPointerException("searchKeyWord can not be null");
		this.getSqlMapClientTemplate().insert(SPACE + "searchkeyword_insert",
				searchKeyWord);
		return searchKeyWord;
	}

	@SuppressWarnings("unchecked")
	public List<SearchKeyWord> getHotKeyWord(int num, String keyType)
			throws DataAccessException {
		List<SearchKeyWord> search_keyWords = null;
		Map map = new HashMap();
		map.put("num", num);
		map.put("keyType", keyType);
		search_keyWords = getSqlMapClientTemplate().queryForList(
				SPACE + "get_hotkeyword_list", map);
		return search_keyWords;
	}

	public SearchKeyWord getKeyWordByWord(String word)
			throws DataAccessException {
		if (word == null)
			throw new NullPointerException("word can not be null");
		return (SearchKeyWord) getSqlMapClientTemplate().queryForObject(
				SPACE + "get_searchkeyword", word);
	}

	public int updateSearchKeyWordCount(SearchKeyWord searchKeyWord) {
		if (searchKeyWord == null) {
			throw new NullPointerException("word can not be null");
		}
		return this.getSqlMapClientTemplate().update(
				SPACE + "update_count_by_name_and_type", searchKeyWord);
	}

}

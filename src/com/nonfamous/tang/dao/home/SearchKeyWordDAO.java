package com.nonfamous.tang.dao.home;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.domain.SearchKeyWord;

/**
 * 
 * @author victor
 * 
 */
public interface SearchKeyWordDAO {
	/**
	 * 获取前num个热门关键字
	 * 
	 * @param num
	 * @param keyType
	 *            关键字类型 1、商品、2、店铺、3、店主、4、资讯信息
	 * @return
	 * @throws DataAccessException
	 */
	public List<SearchKeyWord> getHotKeyWord(int num, String keyType)
			throws DataAccessException;

	/**
	 * 根据关键字获取关键字信息
	 * 
	 * @param word
	 * @return
	 * @throws DataAccessException
	 */
	public SearchKeyWord getKeyWordByWord(String word)
			throws DataAccessException;

	/**
	 * 新增关键字
	 * 
	 * @param searchKeyWord
	 * @return
	 * @throws DataAccessException
	 */
	public SearchKeyWord addSearchKeyWord(SearchKeyWord searchKeyWord)
			throws DataAccessException;

	/**
	 * 根据关键字名称和类型对关键字的值加一
	 * 
	 * @param searchKeyWord
	 * @return 修改的行数
	 */
	public int updateSearchKeyWordCount(SearchKeyWord searchKeyWord);

}

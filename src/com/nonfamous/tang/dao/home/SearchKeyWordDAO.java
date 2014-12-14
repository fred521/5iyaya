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
	 * ��ȡǰnum�����Źؼ���
	 * 
	 * @param num
	 * @param keyType
	 *            �ؼ������� 1����Ʒ��2�����̡�3��������4����Ѷ��Ϣ
	 * @return
	 * @throws DataAccessException
	 */
	public List<SearchKeyWord> getHotKeyWord(int num, String keyType)
			throws DataAccessException;

	/**
	 * ���ݹؼ��ֻ�ȡ�ؼ�����Ϣ
	 * 
	 * @param word
	 * @return
	 * @throws DataAccessException
	 */
	public SearchKeyWord getKeyWordByWord(String word)
			throws DataAccessException;

	/**
	 * �����ؼ���
	 * 
	 * @param searchKeyWord
	 * @return
	 * @throws DataAccessException
	 */
	public SearchKeyWord addSearchKeyWord(SearchKeyWord searchKeyWord)
			throws DataAccessException;

	/**
	 * ���ݹؼ������ƺ����ͶԹؼ��ֵ�ֵ��һ
	 * 
	 * @param searchKeyWord
	 * @return �޸ĵ�����
	 */
	public int updateSearchKeyWordCount(SearchKeyWord searchKeyWord);

}

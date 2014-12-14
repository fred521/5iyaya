//===================================================================
// Created on Jun 9, 2007
//===================================================================
package com.nonfamous.tang.dao.admin.ibatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.dao.admin.CommendContentDAO;
import com.nonfamous.tang.dao.query.CommendContentQuery;
import com.nonfamous.tang.dao.query.CommendTextQuery;
import com.nonfamous.tang.domain.CommendContent;
import com.nonfamous.tang.domain.CommendPosition;

/**
 * <p>
 * IbatisCommendContentDAO
 * </p>
 * 
 * @author jacky
 * @version $Id: IbatisCommendContentDAO.java,v 1.1 2007/06/09 16:27:18 gongl
 *          Exp $
 */

public class IbatisCommendContentDAO extends SqlMapClientDaoSupport implements
		CommendContentDAO {

	@SuppressWarnings("unchecked")
	public List<CommendContent> getCommendContents(CommendContentQuery query)
			throws DataAccessException {
		query.setTotalItem((Integer) this.getSqlMapClientTemplate()
				.queryForObject("COMMENDCONTENT_SPACE.count_commend_contents",
						query));

		return this.getSqlMapClientTemplate().queryForList(
				"COMMENDCONTENT_SPACE.get_commend_contents", query);
	}

	/**
	 * 根据推荐ID得到推荐代码
	 * 
	 * @param commendId
	 * @return
	 */
	public String getCommendCode(int commendId) throws DataAccessException {

		return (String) this.getSqlMapClientTemplate().queryForObject(
				"COMMENDCONTENT_SPACE.get_commend_code", commendId);
	}

	/**
	 * 根据查询条件获取记录数
	 * 
	 * @param query
	 * @return
	 */
	public Integer getCommendCount(CommendTextQuery query)
			throws DataAccessException {

		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"COMMENDCONTENT_SPACE.get_commend_count", query);
	}

	public void deleteCommendContent(String contentId) {
		this.getSqlMapClientTemplate().delete(
				"COMMENDCONTENT_SPACE.delete_commendcontent", contentId);
	}

	public void insertCommendContent(CommendContent cc) {
		this.getSqlMapClientTemplate().insert(
				"COMMENDCONTENT_SPACE.commendcontent_insert", cc);

	}

	public void updateCommendContent(CommendContent cc) {
		if (cc == null) {
			return;
		}
		this.getSqlMapClientTemplate().update(
				"COMMENDCONTENT_SPACE.commendcontent_update", cc);
	}

	public CommendContent getCommendContentsByContetnId(String contentId)
			throws DataAccessException {
		if (StringUtils.isBlank(contentId)) {
			return null;
		}
		return (CommendContent) this.getSqlMapClientTemplate().queryForObject(
				"COMMENDCONTENT_SPACE.get_commend_contents_by_contetn_id",
				contentId);
	}

	// ====================================需要cache====================================

	// public Map<String, List> getCommendContents() throws DataAccessException
	// {
	// List<CommendPosition> cpList = this.getCommendPositions();
	// if (cpList == null || cpList.size() == 0)
	// return null;
	// Map<String, List> returnMap = new HashMap<String, List>();
	// for (CommendPosition cp : cpList) {
	// List<CommendContent> ccList =
	// this.getCommendContentsByCommendCode(cp.getCommendId());
	// returnMap.put(cp.getCommendCode(), ccList);
	// }
	// return returnMap;
	// }
	public Map<String, List> getCommendContentsByPage(String commendPage)
			throws DataAccessException {
		List<String> codeList = this.getCommendCodesByPage(commendPage);
		if (codeList == null) {
			return null;
		}
		Map<String, List> returnMap = new HashMap<String, List>();
		for (String code : codeList) {
			List list = this.getSqlMapClientTemplate().queryForList(
					"COMMENDCONTENT_SPACE.get_commend_content_by_commend_code",
					code);
			returnMap.put(code, list);
		}
		return returnMap;
	}

	@SuppressWarnings("unchecked")
	public List<CommendPosition> getCommendPositions()
			throws DataAccessException {
		return this.getSqlMapClientTemplate().queryForList(
				"COMMENDPOSITION_SPACE.get_commendposition_by_commend_page",
				null);
	}

	@SuppressWarnings("unchecked")
	// public List<CommendContent> getCommendContentsByCommendCode(Long
	// commendPositionId)
	// throws DataAccessException {
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("commendPositionId", commendPositionId);
	// map.put("size", Integer.valueOf(MAX_SIZE));
	// // map.put("date", DateUtils.simpleDate(new Date()));
	// return this.getSqlMapClientTemplate()
	// .queryForList("COMMENDCONTENT_SPACE.get_commend_content_by_commend_code",
	// map);
	// }
	public CommendContent getCommendContentsByPositionId(
			String commendPositionId) throws DataAccessException {
		return (CommendContent) this.getSqlMapClientTemplate().queryForObject(
				"COMMENDCONTENT_SPACE.get_commend_content_by_position_id",
				commendPositionId);
	}

	@SuppressWarnings("unchecked")
	public List<String> getCommendCodesByPage(String commendPage) {
		List<CommendPosition> cpList = this
				.getSqlMapClientTemplate()
				.queryForList(
						"COMMENDPOSITION_SPACE.get_commendposition_by_commend_page",
						commendPage);
		if (cpList == null || cpList.size() == 0)
			return null;
		else {
			return this.getCommendCodes(cpList);
		}
	}

	private List<String> getCommendCodes(List cpList) {
		String code = null;
		String tmp = null;
		List<String> codeList = new ArrayList<String>();

		for (Iterator iter = cpList.iterator(); iter.hasNext();) {
			CommendPosition element = (CommendPosition) iter.next();
			code = element.getCommendCode();

			if ((StringUtils.isNotBlank(code))
					&& !StringUtils.equals(code, tmp)
					&& !codeList.contains(code)) {
				codeList.add(code);
			}
			tmp = code;
		}

		return codeList;
	}

}

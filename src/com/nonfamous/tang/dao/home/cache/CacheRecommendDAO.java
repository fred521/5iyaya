//===================================================================
// Created on May 29, 2007
//===================================================================
package com.nonfamous.tang.dao.home.cache;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.nonfamous.commom.cache.CompactCache;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.dao.admin.CommendContentDAO;
import com.nonfamous.tang.dao.query.CommendContentQuery;
import com.nonfamous.tang.dao.query.CommendTextQuery;
import com.nonfamous.tang.domain.CommendContent;
import com.nonfamous.tang.domain.CommendPosition;

/**
 * <p>
 * 推荐内容
 * </p>
 * 
 * @author jacky
 * @version $Id: CacheRecommendDAO.java,v 1.1 2008/07/11 00:46:47 fred Exp $
 */
public class CacheRecommendDAO implements CommendContentDAO {

	// ====================================cache开始====================================

	private CompactCache cache;

	private CommendContentDAO target;

	// private static final String CommendContentKey = "commendContentKey";

	private static final String CommendPositionKey = "commendPositionKey";

	// @SuppressWarnings("unchecked")
	// public Map<String, List> getCommendContents() throws DataAccessException
	// {
	// Map<String, List> all = (Map<String, List>) cache
	// .get(CommendContentKey);
	// if (all == null) {
	// all = this.target.getCommendContents();
	// cache.put(CommendContentKey, all);
	// }
	// return all;
	// }
	@SuppressWarnings("unchecked")
	public Map<String, List> getCommendContentsByPage(String commendPage) {
		Map<String, List> all = (Map<String, List>) cache.get(commendPage
				+ "con");
		if (all == null) {
			all = this.target.getCommendContentsByPage(commendPage);
			cache.put(commendPage + "con", all);
		}
		return all;
	}

	@SuppressWarnings("unchecked")
	public List<CommendPosition> getCommendPositions()
			throws DataAccessException {
		List<CommendPosition> all = (List<CommendPosition>) cache
				.get(CommendPositionKey);
		if (all == null) {
			all = this.target.getCommendPositions();
			cache.put(CommendPositionKey, all);
		}
		return all;
	}

	@SuppressWarnings("unchecked")
	public List<String> getCommendCodesByPage(String commendPage) {
		if (StringUtils.isBlank(commendPage)) {
			return null;
		}
		List<String> list = (List<String>) this.cache.get(commendPage + "pos");
		if (list == null) {
			list = this.target.getCommendCodesByPage(commendPage);
			cache.put(commendPage + "pos", list);
		}
		return list;
	}

	public void setCache(CompactCache cache) {
		this.cache = cache;
	}

	public void setTarget(CommendContentDAO target) {
		this.target = target;
	}

	// ====================================cache结束====================================

	public void deleteCommendContent(String contentId) {
		target.deleteCommendContent(contentId);
		this.cache.clean();
	}

	public List<CommendContent> getCommendContents(CommendContentQuery query)
			throws DataAccessException {
		return target.getCommendContents(query);
	}

	public CommendContent getCommendContentsByContetnId(String contentId)
			throws DataAccessException {
		return target.getCommendContentsByContetnId(contentId);
	}

	public void insertCommendContent(CommendContent cc) {
		target.insertCommendContent(cc);
		this.cache.clean();
	}

	public void updateCommendContent(CommendContent cc) {
		target.updateCommendContent(cc);
		this.cache.clean();
	}

	public CommendContent getCommendContentsByPositionId(
			String commendPositionId) {
		return target.getCommendContentsByPositionId(commendPositionId);
	}

	public Integer getCommendCount(CommendTextQuery query)
			throws DataAccessException {
		return target.getCommendCount(query);
	}
	
	public String getCommendCode(int commendId)	throws DataAccessException {
		return target.getCommendCode(commendId);
	}
}

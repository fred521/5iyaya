package com.nonfamous.tang.service.home.pojo;

import java.util.List;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.UUIDGenerator;
import com.nonfamous.commom.util.service.POJOServiceBase;
import com.nonfamous.tang.dao.home.HelperDAO;
import com.nonfamous.tang.dao.home.HelperTypeDAO;
import com.nonfamous.tang.dao.query.HelperQuery;
import com.nonfamous.tang.domain.Helper;
import com.nonfamous.tang.exception.ServiceException;
import com.nonfamous.tang.service.home.HelperService;

/**
 * 
 * @author fred
 * 
 */
public class POJOHelperService extends POJOServiceBase implements HelperService {

	private HelperDAO helperDAO;

	private HelperTypeDAO helperTypeDAO;

	public Helper addHelper(Helper helper) {
		String result = null;
		if (helper == null) {
			throw new ServiceException("helper info can't be null");
		}
		// 3.insert into the database
		String helperId = UUIDGenerator.generate();
		helper.setHelperId(helperId);
		// include the inserter Helper base info and helper content function
		helperDAO.insertHelper(helper);
		return helper;

	}

	public int updateHelper(Helper helper) {
		// 1、判断入参是否为空
		if (helper == null) {
			throw new NullPointerException("helper base info can't be null");
		}
		return helperDAO.updateHelper(helper);

	}

	public Helper getHelperById(String helperId) {
		if (StringUtils.isBlank(helperId)) {
			throw new ServiceException("helperId is null");
		}
		return helperDAO.getHelperById(helperId);
	}

	public List getQueryHelperList(HelperQuery query) {
		return this.helperDAO.queryHelperList(query);
	}

	public List getAllHelperList( ) {
		return this.helperDAO.getAllHelperList();
	}
	
	public HelperDAO getHelperDAO() {
		return helperDAO;
	}

	public void setHelperDAO(HelperDAO helperDAO) {
		this.helperDAO = helperDAO;
	}

	public HelperTypeDAO getHelperTypeDAO() {
		return helperTypeDAO;
	}

	public void setHelperTypeDAO(HelperTypeDAO helperTypeDAO) {
		this.helperTypeDAO = helperTypeDAO;
	}
}

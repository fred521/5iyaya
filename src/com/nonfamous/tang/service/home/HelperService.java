package com.nonfamous.tang.service.home;

import java.util.List;

import com.nonfamous.tang.dao.query.HelperQuery;
import com.nonfamous.tang.domain.Helper;

/**
 * 
 * @author fred
 * 
 */
public interface HelperService {

	/**
	 * 添加Helper
	 * 
	 * @param nbi
	 * @param content
	 * @return
	 */
	public Helper addHelper(Helper helper);

	/**
	 * 更新Helper信息
	 * 
	 * @param nbi
	 * @param content
	 */
	public int updateHelper(Helper helper);

	/**
	 * 得到Helper instance(Normal)
	 * 
	 * @return
	 */
	public Helper getHelperById(String helperId);

	/**
	 * query the helper from admin module
	 * 
	 * @param query
	 * @return
	 */
	public List getQueryHelperList(HelperQuery query);
	
	/**
	 * query the helper from admin module
	 * 
	 * @return
	 */
	public List getAllHelperList( ) ;
}

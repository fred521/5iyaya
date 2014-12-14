package com.nonfamous.tang.service.usercenter.pojo;

import com.nonfamous.tang.dao.home.usercenter.UserCenterDAO;
import com.nonfamous.tang.domain.UserCenter;
import com.nonfamous.tang.service.usercenter.UserCenterService;



public class POJOUserCenterService implements UserCenterService {

	private UserCenterDAO userCenterDAO;
		
	public UserCenterDAO getUserCenterDAO() {
		return userCenterDAO;
	}

	public void setUserCenterDAO(UserCenterDAO userCenterDAO) {
		this.userCenterDAO = userCenterDAO;
	}

	public void addUserCenter(UserCenter record) {
		 this.userCenterDAO.insert(record);
	}

	public int deleteByMember(UserCenter record) {
		return this.userCenterDAO.deleteByMember(record);
	}

	public UserCenter getByVerifyCode(String code) {
		return this.userCenterDAO.selectByPrimaryKey(code);
	}

	public UserCenter getLastRequest(UserCenter record) {
		return this.userCenterDAO.selectLastRequest(record);
	}

}

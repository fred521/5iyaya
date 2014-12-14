package com.nonfamous.tang.service.usercenter;

import com.nonfamous.tang.domain.UserCenter;



public interface UserCenterService {
	
	public void addUserCenter(UserCenter record);
	
	public int deleteByMember(UserCenter record);
	
	public UserCenter getByVerifyCode(String code);
	
	public UserCenter getLastRequest(UserCenter record);
}

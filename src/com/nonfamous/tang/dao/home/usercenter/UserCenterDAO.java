package com.nonfamous.tang.dao.home.usercenter;

import java.util.List;

import com.nonfamous.tang.domain.UserCenter;

public interface UserCenterDAO {
    int deleteByPrimaryKey(String verifyCode);

    void insert(UserCenter record);

    UserCenter selectByPrimaryKey(String verifyCode);
    
    int deleteByMember(UserCenter record);
    
    List<UserCenter> selectByMember(UserCenter record);
    
    UserCenter selectLastRequest(UserCenter record);
}
package com.nonfamous.tang.dao.home.usercenter;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.commom.util.MD5Encrypt;
import com.nonfamous.commom.util.UUIDGenerator;
import com.nonfamous.tang.domain.UserCenter;

public class UserCenterDAOImpl extends SqlMapClientDaoSupport implements UserCenterDAO {
	

	private String salt="8315380438874039293L";
	
    public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public UserCenterDAOImpl() {
        super();
    }

    public int deleteByPrimaryKey(String verifyCode) {
        UserCenter key = new UserCenter();
        key.setVerifyCode(verifyCode);
        int rows = getSqlMapClientTemplate().delete("DB2INST1_USER_CENTER.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(UserCenter record) {
    	String code = UUIDGenerator.generate();
    	code = MD5Encrypt.encode(code);
    	record.setVerifyCode(code);
        getSqlMapClientTemplate().insert("DB2INST1_USER_CENTER.ibatorgenerated_insert", record);
    }


    public UserCenter selectByPrimaryKey(String verifyCode) {
        UserCenter key = new UserCenter();
        key.setVerifyCode(verifyCode);
        UserCenter record = (UserCenter) getSqlMapClientTemplate().queryForObject("DB2INST1_USER_CENTER.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

	public int deleteByMember(UserCenter record) {
		return this.getSqlMapClientTemplate().delete("DB2INST1_USER_CENTER.deleteByMember",record);
	}

	public List<UserCenter> selectByMember(UserCenter record) {
		return this.getSqlMapClientTemplate().queryForList("DB2INST1_USER_CENTER.selectByMember",record);
	}

	public UserCenter selectLastRequest(UserCenter record) {
		return (UserCenter)this.getSqlMapClientTemplate().queryForObject("DB2INST1_USER_CENTER.selectLastRequest",record);
	}
	
}
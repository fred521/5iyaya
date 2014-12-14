package com.nonfamous.tang.service.ucenter;

import java.util.Map;

/**
 * 该接口是用来定义与UCenter应用交换中心集成的.
 * 
 * 目前支持的集成有: 1. 用户在5iyya网登录，ucenter的所有子系统能够同步登陆（单点登录）. 2.
 * 用户在5iyya网登出，ucenter的所有子系统能够同步登出（单点登出）. 3.
 * 用户在5iyya网注册，ucenter的所有子系统能够自动注册（单点注册）. 4.
 * 用户在5iyya网修改密码，ucenter的所有子系统能够自动修改密码（单点修改密码）. 5.
 * 暂时不支持反向的服务，比如说用户在ucenter的其他子系统登录，登出，注册，修改密码，并不会影响用户在5iyya网的信息.
 * 
 * TODO：需要支持反响服务.
 * 
 * @author frank
 * 
 */
public interface UCenterService {

	/**
	 * 用户注册
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @return 大于 0:返回用户 ID，表示用户注册成功 -1:用户名不合法 -2:包含不允许注册的词语 -3:用户名已经存在 -4:Email
	 *         格式有误 -5:Email 不允许注册 -6:该 Email 已经被注册
	 * @throws Exception
	 * 
	 */
	public int userRegister(String username, String password, String email)
			throws UCenterException;

	/**
	 * 多应用同步登录
	 * 
	 * @param uid
	 * @return 同步登录的脚本串
	 * @throws Exception
	 */
	public String userSynlogin(String uid) throws UCenterException;

	/**
	 * 多应用同步注销
	 * 
	 * @param uid
	 * @return 同步注销的脚本串
	 * @throws Exception
	 */
	public String userSynlogout(String uid) throws UCenterException;

	/**
	 * 用户登录
	 * 
	 * @param username
	 * @param password
	 * @return 返回用户的udi,username,email
	 * @throws Exception
	 */
	public Map<String, String> userLogin(String username, String password)
			throws UCenterException;

	/**
	 * 判断用户是否存在
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> userExists(String username)
			throws UCenterException;

	/**
	 * 更新用户
	 * 
	 * @param username
	 * @param oldPwd
	 * @param newPwd
	 * @param email
	 * @param ignoreOldPwd 是否需要对密码进行验证
	 * @return
	 * @throws UCenterException
	 */
	public int userUpdate(String username, String oldPwd, String newPwd,
			String email, boolean ignoreOldPwd) throws UCenterException;

}
package com.nonfamous.tang.service.ucenter;

import java.util.Map;

/**
 * �ýӿ�������������UCenterӦ�ý������ļ��ɵ�.
 * 
 * Ŀǰ֧�ֵļ�����: 1. �û���5iyya����¼��ucenter��������ϵͳ�ܹ�ͬ����½�������¼��. 2.
 * �û���5iyya���ǳ���ucenter��������ϵͳ�ܹ�ͬ���ǳ�������ǳ���. 3.
 * �û���5iyya��ע�ᣬucenter��������ϵͳ�ܹ��Զ�ע�ᣨ����ע�ᣩ. 4.
 * �û���5iyya���޸����룬ucenter��������ϵͳ�ܹ��Զ��޸����루�����޸����룩. 5.
 * ��ʱ��֧�ַ���ķ��񣬱���˵�û���ucenter��������ϵͳ��¼���ǳ���ע�ᣬ�޸����룬������Ӱ���û���5iyya������Ϣ.
 * 
 * TODO����Ҫ֧�ַ������.
 * 
 * @author frank
 * 
 */
public interface UCenterService {

	/**
	 * �û�ע��
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @return ���� 0:�����û� ID����ʾ�û�ע��ɹ� -1:�û������Ϸ� -2:����������ע��Ĵ��� -3:�û����Ѿ����� -4:Email
	 *         ��ʽ���� -5:Email ������ע�� -6:�� Email �Ѿ���ע��
	 * @throws Exception
	 * 
	 */
	public int userRegister(String username, String password, String email)
			throws UCenterException;

	/**
	 * ��Ӧ��ͬ����¼
	 * 
	 * @param uid
	 * @return ͬ����¼�Ľű���
	 * @throws Exception
	 */
	public String userSynlogin(String uid) throws UCenterException;

	/**
	 * ��Ӧ��ͬ��ע��
	 * 
	 * @param uid
	 * @return ͬ��ע���Ľű���
	 * @throws Exception
	 */
	public String userSynlogout(String uid) throws UCenterException;

	/**
	 * �û���¼
	 * 
	 * @param username
	 * @param password
	 * @return �����û���udi,username,email
	 * @throws Exception
	 */
	public Map<String, String> userLogin(String username, String password)
			throws UCenterException;

	/**
	 * �ж��û��Ƿ����
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> userExists(String username)
			throws UCenterException;

	/**
	 * �����û�
	 * 
	 * @param username
	 * @param oldPwd
	 * @param newPwd
	 * @param email
	 * @param ignoreOldPwd �Ƿ���Ҫ�����������֤
	 * @return
	 * @throws UCenterException
	 */
	public int userUpdate(String username, String oldPwd, String newPwd,
			String email, boolean ignoreOldPwd) throws UCenterException;

}
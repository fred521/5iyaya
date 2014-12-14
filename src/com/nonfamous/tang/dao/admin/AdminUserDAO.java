package com.nonfamous.tang.dao.admin;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.domain.AdminUser;

/**
 * @author eyeieye
 * @version $Id: AdminUserDAO.java,v 1.1 2008/07/11 00:47:03 fred Exp $
 */
public interface AdminUserDAO {
	/**
	 * �����û��ĵ�¼���õ��û�
	 * 
	 * @param name
	 * @return
	 * @throws DataAccessException
	 */
	public AdminUser getUserByName(String name) throws DataAccessException;

	/**
	 * ����һ���û�
	 * 
	 * @param u
	 * @return �´������û�id
	 */
	public Long createAdminUser(AdminUser u) throws DataAccessException;

	/**
	 * �����û�����,���¿���޸���
	 * 
	 * @param au
	 * @param operatorId
	 * @return �Ƿ�ɹ�
	 * @throws DataAccessException
	 */
	public boolean updateAdminUserPassword(AdminUser au)
			throws DataAccessException;

	/**
	 * �����û���״̬,����״̬���޸���
	 * 
	 * @param userId
	 * @param status
	 * @return
	 * @throws DataAccessException
	 */
	public boolean setAdminUserStatus(AdminUser au) throws DataAccessException;

	/**
	 * ����id�õ��û�
	 * 
	 * @param adminUserId
	 * @return
	 */
	public AdminUser getUserById(Long adminUserId);

	/**
	 * �õ����еĹ���Ա
	 * 
	 * @return
	 */
	public List<AdminUser> findAll();
}

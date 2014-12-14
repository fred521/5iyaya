package com.nonfamous.tang.dao.home;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.dao.query.HelperQuery;
import com.nonfamous.tang.domain.Helper;
import com.nonfamous.tang.web.dto.HelperTypeAndInfoDTO;

/**
 * 
 * @author fred
 * 
 */
public interface HelperDAO {

    /**
     * ����Helper
     * 
     * @param nbi
     * @return
     * @throws DataAccessException
     */
    public void insertHelper(Helper helper) throws DataAccessException;

    /**
     * ����Helper��Ϣ
     * 
     * @param nbi
     * @return
     * @throws DataAccessException
     */
    public int updateHelper(Helper helper) throws DataAccessException;

    /**
     * ɾ��Helper�������ɾ��ͳָ����ɾ��
     * 
     * @param HelperId
     * @return
     * @throws DataAccessException
     */
    public int deleteHelperById(String helperId) throws DataAccessException;

    /**
     * �õ�Helper��base��Ϣ���������content
     * 
     * @param HelperId
     * @return
     * @throws DataAccessException
     */
    public Helper getHelperById(String helperId) throws DataAccessException;


    /**
     * ����ǰ̨�õ�����Helper��Ϣ
     * 
     * @param
     * @return
     * @throws DataAccessException
     */
    public List<Helper> getAllHelperList(HelperQuery query) throws DataAccessException;

    /**
     * ����ǰ̨�õ�����Helper��Ϣ
     * 
     * @param
     * @return
     * @throws DataAccessException
     */
    public List<Helper> getAllHelperList( ) throws DataAccessException;
    
    /**
     * ��̨��ѯ������Ϣ
     * @param query
     * @return
     * @throws DataAccessException
     */
    public List<Helper> queryHelperList(HelperQuery query) throws DataAccessException;

    /**
     * �õ�����ʱ������ĳʱ�������Helper��Ϣ
     * 
     * @param date
     * @return
     * @throws DataAccessException
     */
    public List<Helper> getHelperListByDate(Date date) throws DataAccessException;


    /**
     * ��ȡĳ����Ϣ����µ�������Ϣ������ѯ��Ϣ��ҳ��ʾ�����10����
     * @param newType
     */
    public HelperTypeAndInfoDTO getHelperByHelperType(String helperType) throws DataAccessException;;

    /**
     * ��ȡ���е���ѯ��Ϣ������ѯ��Ϣ��ҳ��ʾ��ÿ��helpertype�����10����
     * @param newType
     */
    public Map<String, HelperTypeAndInfoDTO> getAllHelperInfo() throws DataAccessException;
    
    
   //qjy add 20071225 �õ����ݿ�ϵͳʱ��
    public Date getSysDate();
}

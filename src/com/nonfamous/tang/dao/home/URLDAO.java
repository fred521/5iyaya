package com.nonfamous.tang.dao.home;

import java.util.List;
import org.springframework.dao.DataAccessException;
import com.nonfamous.tang.domain.URL;

/**
 * 
 * @author Jason 
 * 
 */
public interface URLDAO {

    /**
     * ��������������Ϣ
     * 
     * @param su
     * @return
     * @throws DataAccessException
     */
    public void insertURL(URL url) throws DataAccessException;

    /**
     * ��������������Ϣ
     * 
     * @param su
     * @return
     * @throws DataAccessException
     */
    public int updateURL(URL url) throws DataAccessException;

    /**
     * ɾ������������Ϣ�������ɾ��ͳָ����ɾ��
     * 
     * @param id
     * @return
     * @throws DataAccessException
     */
    public int deleteURLById(String id) throws DataAccessException;

    /**
     * �õ��ض�������������Ϣ
     * 
     * @param id
     * @return
     * @throws DataAccessException
     */
    public URL getURLById(String id) throws DataAccessException;

    /**
     * �õ����е�����������Ϣ
     * 
     * @return
     * @throws DataAccessException
     */
    public List<URL> getAllURLs() throws DataAccessException;

}

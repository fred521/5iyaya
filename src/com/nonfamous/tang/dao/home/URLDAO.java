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
     * 新增友情链接信息
     * 
     * @param su
     * @return
     * @throws DataAccessException
     */
    public void insertURL(URL url) throws DataAccessException;

    /**
     * 更新友情链接信息
     * 
     * @param su
     * @return
     * @throws DataAccessException
     */
    public int updateURL(URL url) throws DataAccessException;

    /**
     * 删除友情链接信息，这里的删除统指物理删除
     * 
     * @param id
     * @return
     * @throws DataAccessException
     */
    public int deleteURLById(String id) throws DataAccessException;

    /**
     * 得到特定的友情链接信息
     * 
     * @param id
     * @return
     * @throws DataAccessException
     */
    public URL getURLById(String id) throws DataAccessException;

    /**
     * 得到所有的友情链接信息
     * 
     * @return
     * @throws DataAccessException
     */
    public List<URL> getAllURLs() throws DataAccessException;

}

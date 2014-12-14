package com.nonfamous.tang.dao.home.ibatis;


import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.nonfamous.tang.dao.home.URLDAO;
import com.nonfamous.tang.domain.URL;
/**
 * @author: Jason
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: IbatisURLDAO.java,v 1.1 2009/04/12 09:09:00 jason Exp $
 */
public class IbatisURLDAO extends SqlMapClientDaoSupport implements URLDAO {

    public static final String NEWS_SPACE    = "URLS_SPACE.";

    public void insertURL(URL url) throws DataAccessException {
        if (url == null) {
            throw new NullPointerException("url can't be null");
        }
        try{
        this.getSqlMapClientTemplate().insert(NEWS_SPACE + "URLS_INSERT", url);}
        catch(Exception e){
        	e.printStackTrace();
        }
    }

    public int deleteURLById(String id) throws DataAccessException {
        if (id == null) {
            throw new NullPointerException("URL id can't be null");
        }
        return this.getSqlMapClientTemplate().delete(NEWS_SPACE + "DELETE_URLS_BY_ID", id);
    }

    public URL getURLById(String id) throws DataAccessException {
        if (id == null) {
            throw new NullPointerException("URL id can't be null");
        }
        return (URL) this.getSqlMapClientTemplate()
                                  .queryForObject(NEWS_SPACE + "GET_URL_BY_ID", id);
    }

    @SuppressWarnings("unchecked")
    public List<URL> getAllURLs() throws DataAccessException {
         return (List<URL>) this.getSqlMapClientTemplate()
                                   .queryForList(NEWS_SPACE + "GET_ALL_URLS", null);
    }

    public int updateURL(URL url) throws DataAccessException {
        if (url == null) {
            throw new NullPointerException("URL can't be null");
        }
        return this.getSqlMapClientTemplate().update(NEWS_SPACE + "URLS_UPDATE", url);
    }
}

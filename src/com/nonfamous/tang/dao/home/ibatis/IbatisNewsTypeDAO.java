package com.nonfamous.tang.dao.home.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.tang.dao.home.NewsTypeDAO;
import com.nonfamous.tang.domain.NewsType;

/**
 * @author: alan
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: IbatisNewsTypeDAO.java,v 1.1 2008/07/11 00:46:55 fred Exp $
 */
public class IbatisNewsTypeDAO extends SqlMapClientDaoSupport implements NewsTypeDAO {
    public static final String SPACE = "NEWSTYPE_SPACE.";

    @SuppressWarnings("unchecked")
    public List<NewsType> getAllNewsType() throws DataAccessException {
        return getSqlMapClientTemplate().queryForList(SPACE + "get_newstype_list", null);
    }
    public List<NewsType> getYYNewsType() throws DataAccessException {
        return getSqlMapClientTemplate().queryForList(SPACE + "get_yy_newstype_list", null);
    }

    public NewsType getNewsTypeById(String typeId) throws DataAccessException {
        return (NewsType) getSqlMapClientTemplate().queryForObject(SPACE + "get_newstype", typeId);
    }

}

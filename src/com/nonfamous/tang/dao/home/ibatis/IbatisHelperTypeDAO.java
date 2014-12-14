package com.nonfamous.tang.dao.home.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.tang.dao.home.HelperTypeDAO;
import com.nonfamous.tang.domain.HelperType;

/**
 * @author: fred
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: IbatisHelperTypeDAO.java,v 1.1 2008/09/15 13:44:58 fred Exp $
 */
public class IbatisHelperTypeDAO extends SqlMapClientDaoSupport implements HelperTypeDAO {
    public static final String SPACE = "HELPERTYPE_SPACE.";

    @SuppressWarnings("unchecked")
    public List<HelperType> getAllHelperType() throws DataAccessException {
        return getSqlMapClientTemplate().queryForList(SPACE + "get_helper_type_list", null);
    }

    public HelperType getHelperTypeById(String typeId) throws DataAccessException {
        return (HelperType) getSqlMapClientTemplate().queryForObject(SPACE + "get_helper_type", typeId);
    }

	public HelperType addHelperType(HelperType helperType) {
		return (HelperType) getSqlMapClientTemplate().insert(SPACE + "add_helper_type",helperType);
	}

	public String getHelperTypeByTypeName(String typeName) {
		 return  (String) getSqlMapClientTemplate().queryForObject(SPACE + "get_helper_type_name", typeName);
	}

}

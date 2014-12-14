package com.nonfamous.tang.dao.home.ibatis;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.dao.home.HelperDAO;
import com.nonfamous.tang.dao.query.HelperQuery;
import com.nonfamous.tang.domain.Helper;
import com.nonfamous.tang.domain.HelperType;
import com.nonfamous.tang.web.dto.HelperTypeAndInfoDTO;

/**
 * @author: fred
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: IbatisHelperDAO.java,v 1.3 2008/12/16 13:45:59 fred Exp $
 */
public class IbatisHelperDAO extends SqlMapClientDaoSupport implements HelperDAO {

    public static final String TYPE_SPACE    = "HELPERTYPE_SPACE.";

    public static final String HELPER_SPACE    = "HELPER_SPACE.";

    public void insertHelper(Helper helper) throws DataAccessException {
        if (helper == null) {
            throw new NullPointerException("helper can't be null");
        }
        this.getSqlMapClientTemplate().insert(HELPER_SPACE + "HELPER_INSERT", helper);
    }


    public int deleteHelperById(String helperId) throws DataAccessException {
        if (helperId == null) {
            throw new NullPointerException("helperId can't be null");
        }
        return this.getSqlMapClientTemplate().delete(HELPER_SPACE + "DELETE_HELPER_BY_ID", helperId);
    }


    public Helper getHelperById(String helperId) throws DataAccessException {
        if (helperId == null) {
            throw new NullPointerException("helperId can't be null");
        }
        return (Helper) this.getSqlMapClientTemplate()
                                  .queryForObject(HELPER_SPACE + "GET_HELPER_BY_HELPER_ID", helperId);
    }


    public List<Helper> getHelperListByDate(Date date) throws DataAccessException {
        // TODO Auto-generated method stub
        return null;
    }

    public int updateHelper(Helper helper) throws DataAccessException {
        if (helper == null) {
            throw new NullPointerException("helper can't be null");
        }
        return this.getSqlMapClientTemplate().update(HELPER_SPACE + "HELPER_UPDATE", helper);
    }

    @SuppressWarnings("unchecked")
    public List<Helper> getAllHelperList(HelperQuery query) throws DataAccessException {
        HelperQuery q = (HelperQuery) query;
        Integer totalItem = (Integer) this.getSqlMapClientTemplate()
                                          .queryForObject(HELPER_SPACE + "GET_ALL_HELPER_COUNT", q);
        q.setTotalItem(totalItem);
        return this.getSqlMapClientTemplate().queryForList(HELPER_SPACE + "GET_ALL_HELPER_LIST", q);
    }

    @SuppressWarnings("unchecked")
    public List<Helper> getAllHelperList( ) throws DataAccessException {
        return this.getSqlMapClientTemplate().queryForList(HELPER_SPACE + "GET_HELPER_LIST" );
    }
    
    @SuppressWarnings("unchecked")
    public List<Helper> queryHelperList(HelperQuery query) throws DataAccessException {

        HelperQuery q = (HelperQuery) query;
        Integer totalItem = (Integer) this.getSqlMapClientTemplate()
                                          .queryForObject(HELPER_SPACE + "QUERY_HELPER_COUNT", q);
        if (totalItem.intValue() > 0) {
            q.setTotalItem(totalItem);
            return this.getSqlMapClientTemplate().queryForList(HELPER_SPACE + "QUERY_HELPER_LIST", q);
        } else
            return null;
    }




    public Map<String, HelperTypeAndInfoDTO> getAllHelperInfo() throws DataAccessException {
        List infoList = getSqlMapClientTemplate().queryForList(HELPER_SPACE + "QUERY_HELPER_INFOS",
                                                               null);
        Map<String, HelperTypeAndInfoDTO> map = new HashMap<String, HelperTypeAndInfoDTO>();
        if (infoList != null && infoList.size() != 0) {
            List typeList = getSqlMapClientTemplate()
                                                     .queryForList(
                                                                   TYPE_SPACE + "get_helpertype_list",
                                                                   null);
            if (typeList != null && typeList.size() != 0) {
                for (Iterator iterator = typeList.iterator(); iterator.hasNext();) {
                    HelperType helperType = (HelperType) iterator.next();
                    List<Helper> list = new ArrayList<Helper>();
                    for (Iterator iter = infoList.iterator(); iter.hasNext();) {
                    	Helper helper = (Helper) iter.next();
                        if (helper.getHelperType().intValue()==helperType.getHelperType().intValue()) {
                            list.add(helper);
                        }
                    }
                    if (list.size() > 0) {
                        HelperTypeAndInfoDTO dto = new HelperTypeAndInfoDTO();
                        dto.setList(list);
                        dto.setHelperType(helperType);
                        map.put(helperType.getHelperType().toString(), dto);
                    }
                }
            }
        }
        return map;

    }

    public HelperTypeAndInfoDTO getHelperByHelperType(String typeId) throws DataAccessException {
        if (StringUtils.isBlank(typeId)) {
            throw new NullPointerException("helperType can't be null");
        }
        HelperTypeAndInfoDTO dto = new HelperTypeAndInfoDTO();
        HelperType helperType = (HelperType) getSqlMapClientTemplate()
                                                                .queryForObject(
                                                                                TYPE_SPACE
                                                                                        + "get_helpertype",
                                                                                typeId);
        List infoList = getSqlMapClientTemplate().queryForList(HELPER_SPACE + "QUERY_HELPER_INFOS",
                                                               null);
        dto.setHelperType(helperType);
        dto.setList(infoList);
        return dto;
    }
    
     //qjy add 20071225
    public Date getSysDate()throws DataAccessException{
    	return (Date) this.getSqlMapClientTemplate().queryForObject(HELPER_SPACE + "getSysDate");
    }
}

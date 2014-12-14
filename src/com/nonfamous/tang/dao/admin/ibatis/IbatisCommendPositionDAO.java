//===================================================================
// Created on Jun 3, 2007
//===================================================================
package com.nonfamous.tang.dao.admin.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.dao.admin.CommendPositionDAO;
import com.nonfamous.tang.dao.query.CommendPositionQuery;
import com.nonfamous.tang.domain.CommendPosition;

/**
 * <p>
 * IbatisCommendPositionDAO
 * </p>
 * 
 * @author jacky
 * @version $Id: IbatisCommendPositionDAO.java,v 1.4 2007/06/08 15:14:47 gongl
 *          Exp $
 */

public class IbatisCommendPositionDAO extends SqlMapClientDaoSupport implements CommendPositionDAO {

    @SuppressWarnings("unchecked")
    public List getCommendPositions(CommendPositionQuery query) throws DataAccessException {
        query
             .setTotalItem((Integer) this
                                         .getSqlMapClientTemplate()
                                         .queryForObject(
                                                         "COMMENDPOSITION_SPACE.count_commend_positions",
                                                         query));
        return this.getSqlMapClientTemplate()
                   .queryForList("COMMENDPOSITION_SPACE.get_commend_positions", query);
    }

    @SuppressWarnings("unchecked")
    public List<CommendPosition> getCommendPositionsByPage(String commendPage)
                                                                              throws DataAccessException {
        return this.getSqlMapClientTemplate()
                   .queryForList("COMMENDPOSITION_SPACE.get_commendposition_by_commend_page",
                                 commendPage);
    }

    public void insert(CommendPosition cp) throws DataAccessException {
        this.getSqlMapClientTemplate().insert("COMMENDPOSITION_SPACE.commendposition_insert", cp);
    }

    @SuppressWarnings("unchecked")
    public List<CommendPosition> getCommendPositionByCommendCode(String commendCode) throws DataAccessException {
        if (StringUtils.isBlank(commendCode)) {
            return null;
        }
        return (List) this
                          .getSqlMapClientTemplate()
                          .queryForList(
                                        "COMMENDPOSITION_SPACE.get_commendposition_by_commend_code",
                                        commendCode);
    }

    public CommendPosition getCommendPositionByCommendId(String commendId)
                                                                          throws DataAccessException {
        if (StringUtils.isBlank(commendId)) {
            return null;
        }
        return (CommendPosition) this
                                     .getSqlMapClientTemplate()
                                     .queryForObject(
                                                     "COMMENDPOSITION_SPACE.get_commendposition_by_commend_id",
                                                     commendId);
    }

    public void updateCommendPosition(CommendPosition cp) throws DataAccessException {
        if (cp == null) {
            return;
        }
        this.getSqlMapClientTemplate().update("COMMENDPOSITION_SPACE.commendposition_update", cp);

    }

    public void deleteCommendPosition(String commendId) {
        this.getSqlMapClientTemplate().delete("COMMENDPOSITION_SPACE.delete_commendposition",
                                              commendId);
    }

}

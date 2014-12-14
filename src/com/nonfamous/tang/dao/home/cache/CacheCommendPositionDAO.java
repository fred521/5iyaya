package com.nonfamous.tang.dao.home.cache;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nonfamous.commom.cache.CompactCache;
import com.nonfamous.tang.dao.admin.CommendPositionDAO;
import com.nonfamous.tang.dao.query.CommendPositionQuery;
import com.nonfamous.tang.domain.CommendPosition;

public class CacheCommendPositionDAO implements CommendPositionDAO {
    private CompactCache       cache;

    private CommendPositionDAO target;

    public void deleteCommendPosition(String commendId) {
        target.deleteCommendPosition(commendId);
        this.cache.clean();
    }

    public List<CommendPosition> getCommendPositionByCommendCode(String commendCode) {
        return target.getCommendPositionByCommendCode(commendCode);
    }

    public CommendPosition getCommendPositionByCommendId(String commendId) {
        return target.getCommendPositionByCommendId(commendId);
    }

    public List<CommendPosition> getCommendPositions(CommendPositionQuery query)
                                                                                throws DataAccessException {
        return target.getCommendPositions(query);
    }

    public List<CommendPosition> getCommendPositionsByPage(String commendPage)
                                                                              throws DataAccessException {
        return target.getCommendPositionsByPage(commendPage);
    }

    public void insert(CommendPosition cp) throws DataAccessException {
        target.insert(cp);
        this.cache.clean();
    }

    public void updateCommendPosition(CommendPosition cp) {
        target.updateCommendPosition(cp);
        this.cache.clean();
    }

    public CompactCache getCache() {
        return cache;
    }

    public void setCache(CompactCache cache) {
        this.cache = cache;
    }

    public CommendPositionDAO getTarget() {
        return target;
    }

    public void setTarget(CommendPositionDAO target) {
        this.target = target;
    }

}

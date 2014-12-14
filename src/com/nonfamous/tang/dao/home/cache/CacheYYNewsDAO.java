package com.nonfamous.tang.dao.home.cache;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nonfamous.commom.cache.CompactCache;
import com.nonfamous.tang.dao.home.YYNewsDAO;
import com.nonfamous.tang.dao.query.SearchNewsQuery;
import com.nonfamous.tang.domain.NewsBaseInfo;
public class CacheYYNewsDAO implements YYNewsDAO {

    private YYNewsDAO      target;
    
    private CompactCache cache;

    public CompactCache getCache() {
		return cache;
	}

	public void setCache(CompactCache cache) {
		this.cache = cache;
	}

	public YYNewsDAO getTarget() {
		return target;
	}

	public void setTarget(YYNewsDAO target) {
		this.target = target;
	}

	public List<NewsBaseInfo> getYYNewsListForIndex(SearchNewsQuery query) throws DataAccessException {
        return target.getYYNewsListForIndex(query);
    }
	 public Date getSysDate() {
			// TODO Auto-generated method stub
			return target.getSysDate();
		}
}
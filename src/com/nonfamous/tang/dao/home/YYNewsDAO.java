package com.nonfamous.tang.dao.home;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.dao.query.SearchNewsQuery;
import com.nonfamous.tang.domain.NewsBaseInfo;

/**
 * 
 * @author fred
 * 
 */
public interface YYNewsDAO {


    
    public List<NewsBaseInfo> getYYNewsListForIndex(SearchNewsQuery query) throws DataAccessException;
    
    public Date getSysDate();
}

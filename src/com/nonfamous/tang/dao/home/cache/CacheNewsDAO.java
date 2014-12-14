//===================================================================
// Created on 2007-6-10
//===================================================================
package com.nonfamous.tang.dao.home.cache;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.nonfamous.commom.cache.CompactCache;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.dao.home.NewsDAO;
import com.nonfamous.tang.dao.query.NewsQuery;
import com.nonfamous.tang.dao.query.SearchNewsQuery;
import com.nonfamous.tang.domain.NewsBaseInfo;
import com.nonfamous.tang.domain.NewsContent;
import com.nonfamous.tang.web.dto.NewsTypeAndInfoDTO;

public class CacheNewsDAO implements NewsDAO {

    private CompactCache cache;

    private NewsDAO      target;

    private String       allNewsInfoKey = "allNewsInfoKey";
    
    private String       yyNewsInfoKey = "yyNewsInfoKey";

    public int deleteNewsById(String newsId) throws DataAccessException {
        return target.deleteNewsById(newsId);
    }

    public List<NewsBaseInfo> getAllNewsList(NewsQuery query) throws DataAccessException {
        return target.getAllNewsList(query);
    }

    public NewsBaseInfo getNewsBaseInfoById(String newsId) throws DataAccessException {
        return target.getNewsBaseInfoById(newsId);
    }

    public NewsContent getNewsContentById(String newsId) throws DataAccessException {
        return target.getNewsContentById(newsId);
    }

    public NewsTypeAndInfoDTO getNewsInfoByNewsType(String newType) throws DataAccessException {
        if (StringUtils.isBlank(newType)) {
            throw new NullPointerException("news type id can't null");
        }
        NewsTypeAndInfoDTO nti = (NewsTypeAndInfoDTO) cache.get(newType);
        if (nti == null) {
            getAllNewsInfo();
        }
        nti = (NewsTypeAndInfoDTO) cache.get(newType);
        return nti;
    }

    public List<NewsBaseInfo> getNewsListByDate(Date date) throws DataAccessException {
        return target.getNewsListByDate(date);
    }

    public List<NewsBaseInfo> getNewsListForIndex(SearchNewsQuery query) throws DataAccessException {
        return target.getNewsListForIndex(query);
    }

    public int getNewsTotalByMemberId(String memberId) throws DataAccessException {
        return target.getNewsTotalByMemberId(memberId);
    }

    public void insertNews(NewsBaseInfo nbi) throws DataAccessException {
        target.insertNews(nbi);
        //cache.clean();
    }

    public void insertNewsContent(NewsContent newsContent) throws DataAccessException {
        target.insertNewsContent(newsContent);
        //cache.clean();
    }

    public List<NewsBaseInfo> queryNewsList(NewsQuery query) throws DataAccessException {
        return target.queryNewsList(query);
    }

    public int updateNews(NewsBaseInfo nbi) throws DataAccessException {
        int i = target.updateNews(nbi);
        //cache.clean();
        return i;
    }

    public void updateNewsContent(NewsContent newsContent) throws DataAccessException {
        target.updateNewsContent(newsContent);
        //cache.clean();
    }

    public void updateNewsViewCount(Map<String, Integer> id2ViewMap) throws DataAccessException {
        target.updateNewsViewCount(id2ViewMap);
        //cache.clean();
    }

    @SuppressWarnings("unchecked")
	public Map<String, NewsTypeAndInfoDTO> getAllNewsInfo() throws DataAccessException {

        Map<String, NewsTypeAndInfoDTO> all = (Map<String, NewsTypeAndInfoDTO>) cache
                                                                                     .get(allNewsInfoKey);
        if (all == null) {
            all = (Map<String, NewsTypeAndInfoDTO>) injectNewsInfo();
        }
        return all;
    }
    @SuppressWarnings("unchecked")
	public Map<String, NewsTypeAndInfoDTO> getYYNewsInfo() throws DataAccessException {

        Map<String, NewsTypeAndInfoDTO> all = (Map<String, NewsTypeAndInfoDTO>) cache
                                                                                     .get(yyNewsInfoKey);
        if (all == null) {
            all = (Map<String, NewsTypeAndInfoDTO>) injectYYNewsInfo();
        }
        return all;
    }

    private Map<String, NewsTypeAndInfoDTO> injectNewsInfo() {
        Map<String, NewsTypeAndInfoDTO> all;
        all = target.getAllNewsInfo();
        cache.put(allNewsInfoKey, all);
        for (String key : all.keySet()) {
            cache.put(key, all.get(key));
        }
        return all;
    }

    private Map<String, NewsTypeAndInfoDTO> injectYYNewsInfo() {
        Map<String, NewsTypeAndInfoDTO> all;
        all = target.getYYNewsInfo();
        cache.put(yyNewsInfoKey, all);
        for (String key : all.keySet()) {
            cache.put(key, all.get(key));
        }
        return all;
    }
    
    public Date getSysDate() {
		// TODO Auto-generated method stub
		return target.getSysDate();
	}

    public void setCache(CompactCache cache) {
        this.cache = cache;
    }

    public void setTarget(NewsDAO target) {
        this.target = target;
    }

}

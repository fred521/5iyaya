package com.nonfamous.tang.dao.home.ibatis;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.dao.home.NewsDAO;
import com.nonfamous.tang.dao.query.NewsQuery;
import com.nonfamous.tang.dao.query.SearchNewsQuery;
import com.nonfamous.tang.domain.NewsBaseInfo;
import com.nonfamous.tang.domain.NewsContent;
import com.nonfamous.tang.domain.NewsType;
import com.nonfamous.tang.web.dto.NewsTypeAndInfoDTO;

/**
 * @author: fred
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: IbatisNewsDAO.java,v 1.1 2008/07/11 00:46:55 fred Exp $
 */
public class IbatisNewsDAO extends SqlMapClientDaoSupport implements NewsDAO {

    public static final String TYPE_SPACE    = "NEWSTYPE_SPACE.";

    public static final String CONTENT_SPACE = "NEWSCONTENT_SPACE.";

    public static final String NEWS_SPACE    = "NEWSBASEINFO_SPACE.";

    public void insertNews(NewsBaseInfo nbi) throws DataAccessException {
        if (nbi == null) {
            throw new NullPointerException("news base info can't be null");
        }
        this.getSqlMapClientTemplate().insert(NEWS_SPACE + "NEWS_BASE_INFO_INSERT", nbi);
        NewsContent newsContent = new NewsContent();
        newsContent.setNewsId(nbi.getNewsId());
        newsContent.setContent(nbi.getContent().getContent());
        insertNewsContent(newsContent);
    }

    public void insertNewsContent(NewsContent newsContent) throws DataAccessException {
        if (newsContent == null) {
            throw new NullPointerException("news content can't be null");
        }
        this.getSqlMapClientTemplate().insert(CONTENT_SPACE + "NEWS_CONTENT_INSERT", newsContent);
    }

    public int deleteNewsById(String newsId) throws DataAccessException {
        if (newsId == null) {
            throw new NullPointerException("newsId can't be null");
        }
        return this.getSqlMapClientTemplate().delete(NEWS_SPACE + "DELETE_NEWS_BY_ID", newsId);
    }

    public int deleteNewsContentById(String newsId) throws DataAccessException {
        if (newsId == null) {
            throw new NullPointerException("newsId can't be null");
        }
        return this.getSqlMapClientTemplate().delete(CONTENT_SPACE + "DELETE_NEWS_CONTENT_BY_ID",
                                                     newsId);
    }

    public NewsBaseInfo getNewsBaseInfoById(String newsId) throws DataAccessException {
        if (newsId == null) {
            throw new NullPointerException("newsId can't be null");
        }
        return (NewsBaseInfo) this.getSqlMapClientTemplate()
                                  .queryForObject(NEWS_SPACE + "GET_NEWS_INFO_WITH_CONTENT", newsId);
    }

    public NewsContent getNewsContentById(String newsId) throws DataAccessException {
        if (newsId == null) {
            throw new NullPointerException("newsId can't be null");
        }
        return (NewsContent) this.getSqlMapClientTemplate()
                                 .queryForObject(CONTENT_SPACE + "GET_NEWS_CONTENT_BY_ID", newsId);
    }

    public List<NewsBaseInfo> getNewsListByDate(Date date) throws DataAccessException {
        // TODO Auto-generated method stub
        return null;
    }

    public int updateNews(NewsBaseInfo nbi) throws DataAccessException {
        if (nbi == null) {
            throw new NullPointerException("news base info can't be null");
        }
        NewsContent newsContent =new NewsContent();
        newsContent.setContent(nbi.getContent().getContent());
        newsContent.setNewsId(nbi.getNewsId());
        updateNewsContent(newsContent);
        return this.getSqlMapClientTemplate().update(NEWS_SPACE + "NEWS_BASE_INFO_UPDATE", nbi);
    }

    @SuppressWarnings("unchecked")
    public List<NewsBaseInfo> getAllNewsList(NewsQuery query) throws DataAccessException {
        NewsQuery q = (NewsQuery) query;
        Integer totalItem = (Integer) this.getSqlMapClientTemplate()
                                          .queryForObject(NEWS_SPACE + "GET_ALL_NEWS_COUNT", q);
        q.setTotalItem(totalItem);
        return this.getSqlMapClientTemplate().queryForList(NEWS_SPACE + "GET_ALL_NEWS_LIST", q);
    }

    @SuppressWarnings("unchecked")
    public List<NewsBaseInfo> queryNewsList(NewsQuery query) throws DataAccessException {

        NewsQuery q = (NewsQuery) query;
        Integer totalItem = (Integer) this.getSqlMapClientTemplate()
                                          .queryForObject(NEWS_SPACE + "QUERY_NEWS_COUNT", q);
        if (totalItem.intValue() > 0) {
            q.setTotalItem(totalItem);
            return this.getSqlMapClientTemplate().queryForList(NEWS_SPACE + "QUERY_NEWS_LIST", q);
        } else
            return null;
    }

    public void updateNewsContent(NewsContent newsContent) {
        if (newsContent == null) {
            throw new NullPointerException("news content can't be null");
        }
        this.getSqlMapClientTemplate().update(CONTENT_SPACE + "UPDATE_NEWS_CONTENT_BY_ID",
                                              newsContent);

    }

    public int getNewsTotalByMemberId(String memberId) {
        if (memberId == null) {
            throw new NullPointerException("memberId can't be null");
        }
        Integer totalItem = (Integer) this
                                          .getSqlMapClientTemplate()
                                          .queryForObject(
                                                          NEWS_SPACE
                                                                  + "GET_ALL_NEWS_COUNT_BY_MEMBERID",
                                                          memberId);
        return totalItem;
    }

    @SuppressWarnings("unchecked")
    public List<NewsBaseInfo> getNewsListForIndex(SearchNewsQuery query) throws DataAccessException {
        Integer totalItem = (Integer) this
                                          .getSqlMapClientTemplate()
                                          .queryForObject(
                                                          NEWS_SPACE + "QUERY_NEWS_COUNT_FOR_INDEX",
                                                          query);
        query.setTotalItem(totalItem);
        if ( totalItem.intValue() == 0){
			return java.util.Collections.EMPTY_LIST;
		}
        return this.getSqlMapClientTemplate()
                   .queryForList(NEWS_SPACE + "QUERY_NEWS_LIST_FOR_INDEX", query);
    }

    public void updateNewsViewCount(final Map<String, Integer> id2ViewMap) {
        if (id2ViewMap == null) {
            throw new NullPointerException("id2ViewMap can't be null");
        }
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();
                for (Entry<String, Integer> entry : id2ViewMap.entrySet()) {
                    StringToInteger sti = new StringToInteger(entry.getKey(), entry.getValue());
                    executor.update(NEWS_SPACE + "UDPATE_VIEW_COUNT", sti);
                }
                executor.executeBatch();
                return null;
            }
        });
    }

    public Map<String, NewsTypeAndInfoDTO> getAllNewsInfo() throws DataAccessException {
        List infoList = getSqlMapClientTemplate().queryForList(NEWS_SPACE + "QUERY_NEWS_INFOS",
                                                               null);
        Map<String, NewsTypeAndInfoDTO> map = new HashMap<String, NewsTypeAndInfoDTO>();
        if (infoList != null && infoList.size() != 0) {
            List typeList = getSqlMapClientTemplate()
                                                     .queryForList(
                                                                   TYPE_SPACE + "get_newstype_list",
                                                                   null);
            if (typeList != null && typeList.size() != 0) {
                for (Iterator iterator = typeList.iterator(); iterator.hasNext();) {
                    NewsType newsType = (NewsType) iterator.next();
                    List<NewsBaseInfo> list = new ArrayList<NewsBaseInfo>();
                    for (Iterator iter = infoList.iterator(); iter.hasNext();) {
                        NewsBaseInfo newsBaseInfo = (NewsBaseInfo) iter.next();
                        if (StringUtils.equals(newsBaseInfo.getNewsType(), newsType.getNewsType())) {
                            list.add(newsBaseInfo);
                        }
                    }
                    if (list.size() > 0) {
                        NewsTypeAndInfoDTO dto = new NewsTypeAndInfoDTO();
                        dto.setList(list);
                        dto.setNewsType(newsType);
                        map.put(newsType.getNewsType(), dto);
                    }
                }
            }
        }
        return map;

    }
    public Map<String, NewsTypeAndInfoDTO> getYYNewsInfo() throws DataAccessException {
        List infoList = getSqlMapClientTemplate().queryForList(NEWS_SPACE + "QUERY_YY_NEWS_INFOS",
                                                               null);
        Map<String, NewsTypeAndInfoDTO> map = new HashMap<String, NewsTypeAndInfoDTO>();
        if (infoList != null && infoList.size() != 0) {
            List typeList = getSqlMapClientTemplate()
                                                     .queryForList(
                                                                   TYPE_SPACE + "get_yy_newstype_list",
                                                                   null);
            if (typeList != null && typeList.size() != 0) {
                for (Iterator iterator = typeList.iterator(); iterator.hasNext();) {
                    NewsType newsType = (NewsType) iterator.next();
                    List<NewsBaseInfo> list = new ArrayList<NewsBaseInfo>();
                    for (Iterator iter = infoList.iterator(); iter.hasNext();) {
                        NewsBaseInfo newsBaseInfo = (NewsBaseInfo) iter.next();
                        if (StringUtils.equals(newsBaseInfo.getNewsType(), newsType.getNewsType())) {
                            list.add(newsBaseInfo);
                        }
                    }
                    if (list.size() > 0) {
                        NewsTypeAndInfoDTO dto = new NewsTypeAndInfoDTO();
                        dto.setList(list);
                        dto.setNewsType(newsType);
                        map.put(newsType.getNewsType(), dto);
                    }
                }
            }
        }
        return map;

    }

    public NewsTypeAndInfoDTO getNewsInfoByNewsType(String typeId) throws DataAccessException {
        if (StringUtils.isBlank(typeId)) {
            throw new NullPointerException("newType can't be null");
        }
        NewsTypeAndInfoDTO dto = new NewsTypeAndInfoDTO();
        NewsType newsType = (NewsType) getSqlMapClientTemplate()
                                                                .queryForObject(
                                                                                TYPE_SPACE
                                                                                        + "get_newstype",
                                                                                typeId);
        List infoList = getSqlMapClientTemplate().queryForList(NEWS_SPACE + "QUERY_NEWS_INFOS",
                                                               null);
        dto.setNewsType(newsType);
        dto.setList(infoList);
        return dto;
    }
    
     //qjy add 20071225
    public Date getSysDate()throws DataAccessException{
    	return (Date) this.getSqlMapClientTemplate().queryForObject(NEWS_SPACE + "getSysDate");
    }

}

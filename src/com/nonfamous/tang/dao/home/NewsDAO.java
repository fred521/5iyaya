package com.nonfamous.tang.dao.home;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.nonfamous.tang.dao.query.NewsQuery;
import com.nonfamous.tang.dao.query.SearchNewsQuery;
import com.nonfamous.tang.domain.NewsBaseInfo;
import com.nonfamous.tang.domain.NewsContent;
import com.nonfamous.tang.web.dto.NewsTypeAndInfoDTO;

/**
 * 
 * @author fred
 * 
 */
public interface NewsDAO {

    /**
     * 新增News
     * 
     * @param nbi
     * @return
     * @throws DataAccessException
     */
    public void insertNews(NewsBaseInfo nbi) throws DataAccessException;

    /**
     * 更新News信息
     * 
     * @param nbi
     * @return
     * @throws DataAccessException
     */
    public int updateNews(NewsBaseInfo nbi) throws DataAccessException;

    /**
     * 删除News，这里的删除统指物理删除
     * 
     * @param NewsId
     * @return
     * @throws DataAccessException
     */
    public int deleteNewsById(String newsId) throws DataAccessException;

    /**
     * 得到News的base信息，这里包括不content
     * 
     * @param NewsId
     * @return
     * @throws DataAccessException
     */
    public NewsBaseInfo getNewsBaseInfoById(String newsId) throws DataAccessException;

    /**
     * 得到News的描述信息
     * 
     * @param NewsId
     * @return
     * @throws DataAccessException
     */
    public NewsContent getNewsContentById(String newsId) throws DataAccessException;

    /**
     * 用于前台得到所有Newsbase信息
     * 
     * @param
     * @return
     * @throws DataAccessException
     */
    public List<NewsBaseInfo> getAllNewsList(NewsQuery query) throws DataAccessException;

    /**
     * 后台查询新闻信息
     * @param query
     * @return
     * @throws DataAccessException
     */
    public List<NewsBaseInfo> queryNewsList(NewsQuery query) throws DataAccessException;

    /**
     * 得到更新时间晚于某时间的所有Newsbase信息
     * 
     * @param date
     * @return
     * @throws DataAccessException
     */
    public List<NewsBaseInfo> getNewsListByDate(Date date) throws DataAccessException;

    /**
     * insert news content according to the news base info news ID
     * @param newsContent
     */

    public void insertNewsContent(NewsContent newsContent) throws DataAccessException;;

    /**
     * 更新新闻内容根据newsId
     * @param newsContent
     */
    public void updateNewsContent(NewsContent newsContent) throws DataAccessException;;

    public int getNewsTotalByMemberId(String memberId) throws DataAccessException;;

    /**
     * add by victor
     * 
     * 根据查询条件得到相应列表,为建索引调用
     * 
     * @param query
     * @return
     * @throws DataAccessException
     */
    public List<NewsBaseInfo> getNewsListForIndex(SearchNewsQuery query) throws DataAccessException;

    /**
     * 批量更新咨询的浏览量
     * @param id2ViewMap String->newsId, Integer->add viewCount
     */
    public void updateNewsViewCount(Map<String, Integer> id2ViewMap) throws DataAccessException;;

    /**
     * 获取某个信息编号下的所有信息用于咨询信息首页显示（最多10条）
     * @param newType
     */
    public NewsTypeAndInfoDTO getNewsInfoByNewsType(String newType) throws DataAccessException;;

    /**
     * 获取所有的咨询信息用于咨询信息首页显示（每个newstype下最多10条）
     * @param newType
     */
    public Map<String, NewsTypeAndInfoDTO> getAllNewsInfo() throws DataAccessException;
    
    public Map<String, NewsTypeAndInfoDTO> getYYNewsInfo() throws DataAccessException;
    
   //qjy add 20071225 得到数据库系统时间
    public Date getSysDate();
}

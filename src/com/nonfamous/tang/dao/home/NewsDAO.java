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
     * ����News
     * 
     * @param nbi
     * @return
     * @throws DataAccessException
     */
    public void insertNews(NewsBaseInfo nbi) throws DataAccessException;

    /**
     * ����News��Ϣ
     * 
     * @param nbi
     * @return
     * @throws DataAccessException
     */
    public int updateNews(NewsBaseInfo nbi) throws DataAccessException;

    /**
     * ɾ��News�������ɾ��ͳָ����ɾ��
     * 
     * @param NewsId
     * @return
     * @throws DataAccessException
     */
    public int deleteNewsById(String newsId) throws DataAccessException;

    /**
     * �õ�News��base��Ϣ�����������content
     * 
     * @param NewsId
     * @return
     * @throws DataAccessException
     */
    public NewsBaseInfo getNewsBaseInfoById(String newsId) throws DataAccessException;

    /**
     * �õ�News��������Ϣ
     * 
     * @param NewsId
     * @return
     * @throws DataAccessException
     */
    public NewsContent getNewsContentById(String newsId) throws DataAccessException;

    /**
     * ����ǰ̨�õ�����Newsbase��Ϣ
     * 
     * @param
     * @return
     * @throws DataAccessException
     */
    public List<NewsBaseInfo> getAllNewsList(NewsQuery query) throws DataAccessException;

    /**
     * ��̨��ѯ������Ϣ
     * @param query
     * @return
     * @throws DataAccessException
     */
    public List<NewsBaseInfo> queryNewsList(NewsQuery query) throws DataAccessException;

    /**
     * �õ�����ʱ������ĳʱ�������Newsbase��Ϣ
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
     * �����������ݸ���newsId
     * @param newsContent
     */
    public void updateNewsContent(NewsContent newsContent) throws DataAccessException;;

    public int getNewsTotalByMemberId(String memberId) throws DataAccessException;;

    /**
     * add by victor
     * 
     * ���ݲ�ѯ�����õ���Ӧ�б�,Ϊ����������
     * 
     * @param query
     * @return
     * @throws DataAccessException
     */
    public List<NewsBaseInfo> getNewsListForIndex(SearchNewsQuery query) throws DataAccessException;

    /**
     * ����������ѯ�������
     * @param id2ViewMap String->newsId, Integer->add viewCount
     */
    public void updateNewsViewCount(Map<String, Integer> id2ViewMap) throws DataAccessException;;

    /**
     * ��ȡĳ����Ϣ����µ�������Ϣ������ѯ��Ϣ��ҳ��ʾ�����10����
     * @param newType
     */
    public NewsTypeAndInfoDTO getNewsInfoByNewsType(String newType) throws DataAccessException;;

    /**
     * ��ȡ���е���ѯ��Ϣ������ѯ��Ϣ��ҳ��ʾ��ÿ��newstype�����10����
     * @param newType
     */
    public Map<String, NewsTypeAndInfoDTO> getAllNewsInfo() throws DataAccessException;
    
    public Map<String, NewsTypeAndInfoDTO> getYYNewsInfo() throws DataAccessException;
    
   //qjy add 20071225 �õ����ݿ�ϵͳʱ��
    public Date getSysDate();
}

package com.nonfamous.tang.dao.home;

import java.util.List;

import com.nonfamous.tang.domain.NewsType;

/**
 * @author: fred
 * 
 * <pre>
 * 咨询信息分类dao
 * </pre>
 * 
 * @version $Id: NewsTypeDAO.java,v 1.2 2008/11/29 02:52:35 fred Exp $
 */
public interface NewsTypeDAO {

    /**
     * 获取所有咨询分类
     * 
     * @return
     */
    public List<NewsType> getAllNewsType();
    
    public List<NewsType> getYYNewsType();

    /**
     * 根据咨询分类id获取分类信息
     * 
     * @param typeId
     * @return
     */
    public NewsType getNewsTypeById(String typeId);

}

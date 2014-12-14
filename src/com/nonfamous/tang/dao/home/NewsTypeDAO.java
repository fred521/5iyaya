package com.nonfamous.tang.dao.home;

import java.util.List;

import com.nonfamous.tang.domain.NewsType;

/**
 * @author: fred
 * 
 * <pre>
 * ��ѯ��Ϣ����dao
 * </pre>
 * 
 * @version $Id: NewsTypeDAO.java,v 1.2 2008/11/29 02:52:35 fred Exp $
 */
public interface NewsTypeDAO {

    /**
     * ��ȡ������ѯ����
     * 
     * @return
     */
    public List<NewsType> getAllNewsType();
    
    public List<NewsType> getYYNewsType();

    /**
     * ������ѯ����id��ȡ������Ϣ
     * 
     * @param typeId
     * @return
     */
    public NewsType getNewsTypeById(String typeId);

}

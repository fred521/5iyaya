//===================================================================
// Created on 2007-6-10
//===================================================================
package com.nonfamous.tang.dao.home.cache;

import java.util.Collections;
import java.util.List;

import com.nonfamous.commom.cache.CompactCache;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.dao.home.NewsTypeDAO;
import com.nonfamous.tang.domain.NewsType;

public class CacheNewsTypeDAO implements NewsTypeDAO {

    private CompactCache cache;

    private NewsTypeDAO  target;

    private String       allNewsTypeKey = "allNewsTypeKey";
    
    private String       yyNewsTypeKey = "yyNewsTypeKey";

    @SuppressWarnings("unchecked")
    public List<NewsType> getAllNewsType() {
        List<NewsType> all = (List<NewsType>) cache.get(allNewsTypeKey);
        if (all == null) {
            all = injectNewsType();
        }
        return Collections.unmodifiableList(all);
    }
    @SuppressWarnings("unchecked")
    public List<NewsType> getYYNewsType() {
        List<NewsType> all = (List<NewsType>) cache.get(yyNewsTypeKey);
        if (all == null) {
            all = injectYYNewsType();
        }
        return Collections.unmodifiableList(all);
    }

    private List<NewsType> injectNewsType() {
        List<NewsType> all;
        all = target.getAllNewsType();
        cache.put(allNewsTypeKey, all);
        for (NewsType nt : all) {
            cache.put(nt.getNewsType(), nt);
        }
        return all;
    }
    private List<NewsType> injectYYNewsType() {
        List<NewsType> all;
        all = target.getYYNewsType();
        cache.put(yyNewsTypeKey, all);
        for (NewsType nt : all) {
            cache.put(nt.getNewsType(), nt);
        }
        return all;
    }

    public NewsType getNewsTypeById(String typeId) {
        if (StringUtils.isBlank(typeId)) {
            throw new NullPointerException("news type id can't null");
        }
        NewsType nt = (NewsType) cache.get(typeId);
        if (nt == null) {
            getAllNewsType();
        }
        nt = (NewsType) cache.get(typeId);
        return nt;
    }

    public void setCache(CompactCache cache) {
        this.cache = cache;
    }

    public void setTarget(NewsTypeDAO target) {
        this.target = target;
    }

}

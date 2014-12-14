package com.nonfamous.tang.service.video.pojo;

import java.io.Serializable;

import com.nonfamous.commom.util.service.POJOServiceBase;
import com.nonfamous.tang.service.video.ITokenCacheManager;



import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class TokenCacheManager extends POJOServiceBase implements ITokenCacheManager {
	
	private Cache tokenCache = null;
	
    public void setCacheManager(CacheManager cacheManager) {
        tokenCache = cacheManager.getCache("tokenCache");
    }
    
	public void cache(String token, Serializable cachedObject) {
		if( logger.isDebugEnabled() ) {
			logger.debug( token + " used to cache " + cachedObject ) ;
		}
		tokenCache.put(new Element(token, cachedObject ) );
	}

	public boolean isTokenExists(String token) {
		return tokenCache.isKeyInCache( token )  ;
	}
	
	public Serializable remove(String token) {
		if( isTokenExists( token ) ) {
			Serializable obj = tokenCache.get( token ).getValue() ;
			tokenCache.remove( token ) ;
			return obj ;
		}
		return null;
	}
	public Serializable get(String token) {
		if( isTokenExists( token ) ) {
			Serializable obj = tokenCache.get( token ).getValue() ;
			return obj ;
		}
		return null;
	}
}

package com.nonfamous.commom.cache.ehcache;

import net.sf.ehcache.Ehcache;

import org.springframework.cache.ehcache.EhCacheFactoryBean;

/**
 * @author fish
 * @version $Id: CompactEhCacheFactoryBean.java,v 1.1 2008/07/11 00:47:15 fred Exp $
 */
public class CompactEhCacheFactoryBean extends EhCacheFactoryBean {

	@Override
	public Object getObject() {
		Ehcache ehcache = (Ehcache) super.getObject();
		return new CompactEhcache(ehcache);
	}

}

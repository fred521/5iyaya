package com.nonfamous.commom.cache.ehcache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.nonfamous.commom.cache.CompactCache;

/**
 * @author fish
 * @version $Id: CompactEhcache.java,v 1.1 2008/07/11 00:47:15 fred Exp $
 */
public class CompactEhcache implements CompactCache {

	private Ehcache ehcache;

	public CompactEhcache(Ehcache ehcache) {
		super();
		this.ehcache = ehcache;
	}

	public void clean() {
		this.ehcache.removeAll();
	}

	public Object get(Object key) {
		if (key == null) {
			throw new NullPointerException("caceh key can't be null");
		}
		Element element = this.ehcache.get(key);
		if (element == null) {
			return null;
		}
		return element.getObjectValue();
	}

	public void put(Object key, Object value) {
		if (key == null) {
			throw new NullPointerException("caceh key can't be null");
		}
		if (value == null) {
			this.ehcache.remove(key);
		}
		Element element = new Element(key, value);
		this.ehcache.put(element);
	}

}

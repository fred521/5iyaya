package com.nonfamous.tang.domain;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * 
 */
public class URL extends DomainBase
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -8809374066107087096L;

    private String id;
    
    private String path;
    
    private String url;

    private String title;
    
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
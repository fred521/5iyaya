package com.nonfamous.tang.service.home;

import java.util.List;
import com.nonfamous.tang.dao.home.URLDAO;
import com.nonfamous.tang.domain.URL;
import com.nonfamous.tang.domain.result.URLResult;

/**
 * @author Jason
 * 
 */
public interface URLService {

	
	public URLResult saveUrl(URL url);
	
	public URLResult updateUrl(URL url);
	
	public URLResult deleteUrl(String id);
	
	public URL getUrlById(String id);
	
	public List<URL> getAllUrls();
}

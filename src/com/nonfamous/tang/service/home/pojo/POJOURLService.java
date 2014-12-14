package com.nonfamous.tang.service.home.pojo;

import java.util.List;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.UUIDGenerator;
import com.nonfamous.tang.dao.home.URLDAO;
import com.nonfamous.tang.domain.URL;
import com.nonfamous.tang.domain.result.URLResult;
import com.nonfamous.tang.exception.ServiceException;
import com.nonfamous.tang.service.home.URLService;

/**
 * 
 * @author Jason
 * 
 */
public class POJOURLService implements URLService{
	
	private URLDAO urlDAO;

	
	public URLResult saveUrl(URL url) {
		if (url == null) {
			throw new ServiceException("URL can't be null");
		}
		URLResult result = new URLResult();
		if (StringUtils.isEmpty(url.getUrl())) {
			result.setErrorCode(URLResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("链接地址不能为空");
			return result;
		}
		if (StringUtils.isEmpty(url.getTitle())) {
			result.setErrorCode(URLResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("链接标题不能为空");
			return result;
		}
/*		if (StringUtils.isEmpty(url.getPicFlag())) {
			result.setErrorCode(URLResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("必选选择是否为图片");
			return result;
		}*/
		
		String id = UUIDGenerator.generate();
		url.setId(id);
		urlDAO.insertURL(url);

		result.setURLId(id);
		result.setSuccess(true);
		
		return result;
	}
	
	public URLResult updateUrl(URL url) {
		if (url == null) {
			throw new ServiceException(" URL can't be null");
		}
		URLResult result = new URLResult();
		if (StringUtils.isEmpty(url.getUrl())) {
			result.setErrorCode(URLResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("链接地址不能为空");
			return result;
		}
		if (StringUtils.isEmpty(url.getTitle())) {
			result.setErrorCode(URLResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("链接标题不能为空");
			return result;
		}
/*		if (StringUtils.isEmpty(url.getPicFlag())) {
			result.setErrorCode(URLResult.ERROR_INVALID_PARAMETER);
			result.setErrorMessage("必选选择是否为图片");
			return result;
		}*/

		urlDAO.updateURL(url);
		result.setSuccess(true);
		return result;
	}
	
	public URLResult deleteUrl(String id) {
		if (id == null) {
			throw new ServiceException("URL id can't be null");
		}
		URLResult result = new URLResult();
		urlDAO.deleteURLById(id);
		result.setSuccess(true);
		return result;
	}
	
	public URL getUrlById(String id) {
		return urlDAO.getURLById(id);
	}
	
	public List<URL> getAllUrls() {
		return urlDAO.getAllURLs();
	}

	public URLDAO getUrlDAO() {
		return urlDAO;
	}

	public void setUrlDAO(URLDAO urlDAO) {
		this.urlDAO = urlDAO;
	}

}

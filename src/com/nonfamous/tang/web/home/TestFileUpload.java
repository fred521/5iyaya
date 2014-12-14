package com.nonfamous.tang.web.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.nonfamous.tang.service.upload.UploadService;

/**
 * @author:alan
 * 
 * <pre>
 * 默认的Form实现
 * </pre>
 * 
 * 
 * @version $$Id: TestFileUpload.java,v 1.1 2008/07/11 00:46:54 fred Exp $$
 */
public class TestFileUpload implements Controller {
	private UploadService uploadService;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile f1 = multipartRequest.getFile("f1");

		MultipartFile f2 = multipartRequest.getFile("f2");
		ModelAndView mv = new ModelAndView("home/testFileUpload");
		if (f1 != null && f1.getSize() > 0) {
			mv.addObject("f1", uploadService.uploadGoodsImageWithCompress(f1));
		}
		if (f2 != null && f2.getSize() > 0) {
			mv.addObject("f2", uploadService.uploadShopImageWithCompress(f2));
		}
		
		
		return mv;
	}

	public UploadService getUploadService() {
		return uploadService;
	}

	public void setUploadService(UploadService uploadService) {
		this.uploadService = uploadService;
	}

}

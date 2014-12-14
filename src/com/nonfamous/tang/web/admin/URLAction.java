package com.nonfamous.tang.web.admin;


import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import com.nonfamous.commom.form.Form;
import com.nonfamous.commom.form.FormFactory;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.domain.URL;
import com.nonfamous.tang.domain.result.URLResult;
import com.nonfamous.tang.service.home.URLService;
import com.nonfamous.tang.service.upload.UploadFileException;
import com.nonfamous.tang.service.upload.UploadService;

/**
 * 
 * @author Jason
 * 
 */
public class URLAction extends MultiActionController {

	private FormFactory formFactory;

	private URLService urlService;

	private UploadService uploadService;

	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("admin/urls/urlsInit");
		Form form = formFactory.getForm("editURLs", request);
		request.setAttribute("form", form);

		request.setAttribute("id",request.getParameter("id"));
		if (!form.isValide()) {
			ModelAndView errorMv = new ModelAndView("admin/urls/urlEdit");
			
			return errorMv;
		}
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		String urlId = request.getParameter("id");
		
		URL url = new URL();
		
		String infoShow = null;
		
		if (StringUtils.isBlank(urlId)){
			String picPath = null;
			//
			if("Y".equals(request.getParameter("picFlag"))){
				try{
					picPath = uploadPic(multipartRequest);
				}
				catch(UploadFileException e){
					request.setAttribute("uploadGoodsError", e.getMessage());
					request.setAttribute("form", form);
					return mv;
				}
			}
			
			if(picPath!=null){
				url.setPath(picPath);
			}
			else{
				url.setPath(request.getParameter("path"));
			}
			
			url.setTitle(request.getParameter("title"));
			url.setUrl(request.getParameter("url"));
			
			URLResult result =  this.getUrlService().saveUrl(url);
			if(result.isSuccess()){
				infoShow = "添加链接成功";
			} 
			else{
				infoShow ="添加链接失败，请检查链接地址是否为空";
			}
		}
		else{
			String picPath = null;
			if("Y".equals(request.getParameter("picFlag"))){
				try{
					//
					picPath = uploadPic(multipartRequest);
				}
				catch(UploadFileException e){
					request.setAttribute("uploadGoodsError", e.getMessage());
					request.setAttribute("form", form);
					return mv;
				}
			}
			
			if(picPath!=null){
				url.setPath(picPath);
			}
			else{
				url.setPath(request.getParameter("path"));
			}
			url.setId(urlId);
			url.setTitle(request.getParameter("title"));
			url.setUrl(request.getParameter("url"));
			
			URLResult result = this.getUrlService().updateUrl(url);
			if(result.isSuccess()){
				infoShow = "更新链接成功";
			} 
		}

		List<URL> urls = this.getUrlService().getAllUrls();
		
		mv.addObject("urls", urls);
		mv.addObject("infoShow", infoShow);
		return mv;

	}

	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("admin/urls/urlEdit");
		return mv;
	}
	
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

        String id = request.getParameter("id");
        URL url = this.getUrlService().getUrlById(id);
        
        Form form = formFactory.getForm("editURLs", request);
		form.getField("title").setValue(url.getTitle());
		form.getField("path").setValue(url.getPath());
		form.getField("url").setValue(url.getUrl());
		//form.getField("id").setValue(id);
		ModelAndView mv = new ModelAndView("admin/urls/urlEdit");
		mv.addObject("id", id);
		mv.addObject("form", form);
		return mv;
	}
	
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

        String id = request.getParameter("id");
        
        URLResult result = this.getUrlService().deleteUrl(id);
        ModelAndView mv = new ModelAndView("admin/urls/urlsInit");
        String infoShow = null;
		if(result.isSuccess()){
			infoShow = "删除链接成功";
		}
		else {
			infoShow = "删除链接失败";
		}
		
		List<URL> urls = this.getUrlService().getAllUrls();
		
		mv.addObject("infoShow", infoShow);
		mv.addObject("urls", urls);
		return mv;
	}
	
	public ModelAndView urlsAddInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("admin/urls/urlsInit");
		List<URL> urls = this.getUrlService().getAllUrls();
		mv.addObject("urls", urls);
		return mv;
	}
	
	public ModelAndView cancel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
/*		ModelAndView mv = new ModelAndView("admin/urls/urlsInit");
		List<URL> urls = this.getUrlService().getAllUrls();
		mv.addObject("urls", urls);*/
		return urlsAddInit(request, response);
	}
	
	private String uploadPic(MultipartHttpServletRequest multipartRequest) throws UploadFileException{
		String picPath = null;
		for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
			String key = (String) it.next();
			MultipartFile file = multipartRequest.getFile(key);
			if (file != null && file.getSize() > 0
					&& file.getOriginalFilename().length() > 0) {
				picPath = uploadService.uploadURLImageWithCompress(file);
			}
		}
		return picPath;
	}

	public FormFactory getFormFactory() {
		return formFactory;
	}

	public void setFormFactory(FormFactory formFactory) {
		this.formFactory = formFactory;
	}

	public UploadService getUploadService() {
		return uploadService;
	}

	public void setUploadService(UploadService uploadService) {
		this.uploadService = uploadService;
	}

	public URLService getUrlService() {
		return urlService;
	}

	public void setUrlService(URLService urlService) {
		this.urlService = urlService;
	}
}

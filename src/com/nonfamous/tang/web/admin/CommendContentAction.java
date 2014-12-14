//===================================================================
// Created on Jun 9, 2007
//===================================================================
package com.nonfamous.tang.web.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nonfamous.commom.form.Form;
import com.nonfamous.commom.form.FormFactory;
import com.nonfamous.commom.util.DateUtils;
import com.nonfamous.commom.util.HtmlUtils;
import com.nonfamous.commom.util.PriceUtils;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.commom.util.web.cookyjar.Cookyjar;
import com.nonfamous.tang.dao.query.CommendContentQuery;
import com.nonfamous.tang.dao.query.CommendTextQuery;
import com.nonfamous.tang.domain.CommendContent;
import com.nonfamous.tang.domain.CommendPosition;
import com.nonfamous.tang.service.admin.CommendService;
import com.nonfamous.tang.service.upload.UploadFileException;
import com.nonfamous.tang.service.upload.UploadService;

/**
 * <p>
 * �Ƽ����ݹ���
 * </p>
 * 
 * @author jacky
 * @version $Id: CommendContentAction.java,v 1.1 2008/07/11 00:47:07 fred Exp $
 */

public class CommendContentAction extends MultiActionController {
	private static final Log logger = LogFactory
			.getLog(CommendPositionAction.class);

	private CommendService commendService;

	private UploadService uploadService;

	private FormFactory formFactory;

	/**
	 * ��ʾ�Ƽ�λ���б�
	 */
	public ModelAndView contentList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("pageTitle", "�Ƽ����ݹ���");
		CommendContentQuery query = new CommendContentQuery();
		RequestValueParse rvp = new RequestValueParse(request);
		// �Ƽ�λ��ҳ��
		String commendPage = rvp.getParameter("commendPage").getString();
		if (StringUtils.isNotBlank(commendPage)) {
			query.setCommendPage(Long.valueOf(commendPage));
		}
		String currentPage = rvp.getParameter("currentPage").getString();
		if (StringUtils.isNotBlank(currentPage)) {
			query.setCurrentPage(Integer.valueOf(currentPage));

		}
		List<CommendContent> contentList = commendService
				.getCommendContents(query);
		ModelAndView mav = new ModelAndView("admin/commendContent/contentList");
		mav.addObject("contentList", contentList);
		mav.addObject("commendPage", commendPage);
		mav.addObject("search", query);
		return mav;
	}

	/**
	 * ɾ���Ƽ�����
	 */
	public ModelAndView contentDel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		// �Ƽ�ID
		String contentId = rvp.getParameter("contentId").getString();
		request.setAttribute("contentId", contentId);
		// �Ƽ�λ��ҳ��
		String commendPage = rvp.getParameter("commendPage").getString();
		request.setAttribute("commendPage", commendPage);
		// idΪ���򷵻ص��б�
		if (StringUtils.isBlank(contentId)) {
			return new ModelAndView(
					"forward:/admin/commend_content/contentList.htm");
		}
		Cookyjar cookyjar = rvp.getCookyjar();
		// �û�
		String modifyuser = cookyjar.get("adminUserId");
		if (logger.isInfoEnabled()) {
			logger.info("delete commend content by " + modifyuser
					+ " content id is :" + contentId);
		}
		CommendContent cc = commendService
				.getCommendContentByContentId(contentId);

		// delete
		if (cc != null) {
			this.commendService.deleteCommendContent(contentId);
			// ɾͼƬ
			if (StringUtils.isNotBlank(cc.getPicPath())) {
				uploadService.deleteImageFile(cc.getPicPath());
			}
		}
		return new ModelAndView(
				"forward:/admin/commend_content/contentList.htm");
	}

	/**
	 * �����Ƽ�λ��
	 */

	public ModelAndView conAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Form form = formFactory.getForm("storeContent", request);
		RequestValueParse rvp = new RequestValueParse(request);
		// ������Դ�������Դ�Ǵ��б�ҳ�棬����ֵ����
		String from = rvp.getParameter("from").getString();
		if (StringUtils.isNotBlank(from)) {
			String posId = rvp.getParameter("posId").getString();
			changePosition(rvp, form, request, posId);
			return new ModelAndView("admin/commendContent/contentAdd");
		}
		// �Ƽ�λ��
		String posId = form.getField("posId").getValue();
		String changePos = rvp.getParameter("changePos").getString();
		// ����ı����Ƽ�λ�ã������Ƽ�λ���Ƿ����Ƽ�����
		if (StringUtils.equals(changePos, "Y")) {
			changePosition(rvp, form, request, posId);
			return new ModelAndView("admin/commendContent/contentAdd");
		}
		// �����Ƽ�ҳ��
		String commendPage = form.getField("commendPage").getValue();
		CommendPosition cp = this.commendService
				.getCommendPositionByCommendId(posId);

		CommendContent cc = this.commendService.getCommendContentByPosId(posId);
		if (!preCheckStoreContent(form, request, cp, cc)) {
			List posList = commendService
					.getCommendPositionsByPage(commendPage);
			request.setAttribute("cp", cp);
			request.setAttribute("form", form);
			request.setAttribute("posList", posList);
			return new ModelAndView("admin/commendContent/contentAdd");
		}

		if (cc == null) {
			cc = new CommendContent();
		}

		// ��ʼ����
		String gmtStart = form.getField("gmtStart").getValue();
		// ����ʱ��
		String gmtEnd = form.getField("gmtEnd").getValue();
		// �û�
		String adminuser = rvp.getCookyjar().get("adminUserId");
		// ����Ƽ����Ͳ�Ϊ�����֣��Ƽ����Ͳ�Ϊ��ѯ��Ϣ����Ҫ�ϴ�ͼƬ����дͼƬ·��
		String picUrl = form.getField("picUrl").getValue();

		if (!StringUtils.equals(cp.getCommendContentType(), "3")) {
			MultipartFile replacePic = null;
			String picPath = null;
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			replacePic = multipartRequest.getFile("picPath");
			if (replacePic != null && replacePic.getSize() > 0) {
				try {
					picPath = this.uploadService.uploadFile(replacePic);
					// ѹ��ͼƬ
					if (cp != null && cp.getPicWidth() != null
							&& cp.getPicHeight() != null) {
						this.uploadService.imageCompress(picPath, cp
								.getPicWidth().intValue(), cp.getPicHeight()
								.intValue());
					}
				} catch (UploadFileException e) {
					request.setAttribute("uploadError", e.getMessage());
					List posList = commendService
							.getCommendPositionsByPage(commendPage);
					request.setAttribute("cp", cp);
					request.setAttribute("form", form);
					request.setAttribute("posList", posList);
					return new ModelAndView("admin/commendContent/contentAdd");
				}
				cc.setPicPath(picPath);
			} else {
				cc.setPicUrl(picUrl);
			}

		}

		// �Ƽ�����
		String commendText = form.getField("commendText").getValue();
		// ��������
		String batchNum = form.getField("batchNum").getValue();
		// �����۸�
		String batchPrice = form.getField("batchPrice").getValue();
		// �Ƽ���Ϣurl
		String commendUrl = form.getField("commendUrl").getValue();
		// �Ƽ�˵��
		String commendDesc = form.getField("commendDesc").getValue();
		// ��������
		if (StringUtils.equals(cp.getCommendType(), "1")) {
			if (StringUtils.isNotBlank(batchNum)) {
				cc.setBatchNum(Long.valueOf(batchNum));
			} else {
				cc.setBatchNum(null);
			}
			if (StringUtils.isNotBlank(batchPrice)) {
				Double price = Double.valueOf(batchPrice) * 100;
				cc.setBatchPrice(price.longValue());
			} else {
				cc.setBatchPrice(null);
			}
		}
		cc.setCommendDesc(commendDesc);
		cc.setCommendPage(Long.valueOf(commendPage));
		cc.setCommendPositionId(cp.getCommendId());
		cc.setCommendStatus("N");
		cc.setCommendText(HtmlUtils.escapeScriptTag(commendText));
		cc.setCommendType(Long.valueOf(cp.getCommendType()));
		cc.setCommendUrl(commendUrl);
		cc.setCreator(adminuser);
		cc.setModifier(adminuser);
		cc.setGmtStart(DateUtils.chineseString2Date(gmtStart));
		cc.setGmtEnd(DateUtils.chineseString2Date(gmtEnd));
		// store
		if (cc.getContentId() == null) {
			this.commendService.insertCommendContent(cc);
		} else {
			this.commendService.updateCommendContent(cc);
		}

		return new ModelAndView(
				"forward:/admin/commend_content/contentList.htm");
	}

	private void changePosition(RequestValueParse rvp, Form form,
			HttpServletRequest request, String posId) {
		CommendPosition cp = this.commendService
				.getCommendPositionByCommendId(posId);
		request.setAttribute("cp", cp);
		form.getField("posId").setValue(posId);
		form.getField("commendPage").setValue(
				String.valueOf(cp.getCommendPage()));
		form.getField("commendName").setValue(posId);
		request.setAttribute("form", form);
		List posList = commendService.getCommendPositionsByPage(String
				.valueOf(cp.getCommendPage()));

		request.setAttribute("posList", posList);

		CommendContent cc = this.commendService.getCommendContentByPosId(posId);
		request.setAttribute("cc", cc);
		this.changeFormValue(cc, form);
	}

	private void changeFormValue(CommendContent cc, Form form) {
		if (cc != null) {
			// ��ʼ����
			form.getField("gmtStart").setValue(
					DateUtils.dtSimpleChineseFormat(cc.getGmtStart()));
			// ����ʱ��
			form.getField("gmtEnd").setValue(
					DateUtils.dtSimpleChineseFormat(cc.getGmtEnd()));
			// �Ƽ�����
			form.getField("commendText").setValue(cc.getCommendText());
			// �Ƽ�ͼƬurl
			form.getField("picUrl").setValue(cc.getPicUrl());
			// ��Ʒ�������������������۸�
			if (cc.getCommendType().intValue() == 1) {
				// ��������
				if (cc.getBatchNum() != null)
					form.getField("batchNum").setValue(
							String.valueOf(cc.getBatchNum()));
				// �����۸�
				if (cc.getBatchPrice() != null)
					form.getField("batchPrice").setValue(
							PriceUtils.fenToYuanString(cc.getBatchPrice()));
			}
			// �Ƽ���Ϣurl
			form.getField("commendUrl").setValue(cc.getCommendUrl());
			// �Ƽ�˵��
			form.getField("commendDesc").setValue(cc.getCommendDesc());

		} else {
			form.getField("gmtStart").setValue("");
			form.getField("gmtEnd").setValue("");
			form.getField("commendText").setValue("");
			form.getField("picUrl").setValue("");
			form.getField("batchNum").setValue("");
			form.getField("batchPrice").setValue("");
			form.getField("commendUrl").setValue("");
			form.getField("commendDesc").setValue("");
		}
	}

	/**
	 * �����Ƽ����ݲ���У��
	 * 
	 * @param form
	 */
	private boolean preCheckStoreContent(Form form, HttpServletRequest request,
			CommendPosition cp, CommendContent cc) {
		 
		String commendText = form.getField("commendText").getValue();
		String commendPage = request.getParameter("commendPage");
		String commendId = request.getParameter("posId");
		/*ȡ���ݿ�������Ƽ����Ƶ����ݡ�2008/01/23 by qiujy Modifiy */
		cc = this.commendService.getCommendContentByPosId(commendId);
		String DataCommendText=null;
		if(cc!=null){
			DataCommendText = cc.getCommendText().trim();
		}

		String commendCode = this.commendService.getCommendCode(Integer
				.parseInt(commendId));

		CommendTextQuery query = new CommendTextQuery();
		query.setCommendText(commendText);
		query.setCommendPage(Long.parseLong(commendPage));
		query.setCommendCode(commendCode);
		int count = this.commendService.getCommendCount(query).intValue();
        /*�������������жϡ�2008/01/23 by qiujy Modifiy */
		if (count==0) {
		}
		if(count==1)
		{
			if(commendText.equals(DataCommendText))
			{
			}
			else
			{
				form.getField("commendText").setMessage("���Ƽ������Ѿ�����");
				return false;
			}
		}
		if (!form.isValide()) {
			return false;
		}
		// ��ʼ����
		String gmtStart = form.getField("gmtStart").getValue();
		// ����ʱ��
		String gmtEnd = form.getField("gmtEnd").getValue();
		Date gmtStartDate = DateUtils.chineseString2Date(gmtStart);
		Date gmtEndDate = DateUtils.chineseString2Date(gmtEnd);
		if (gmtStartDate.after(gmtEndDate)) {
			form.getField("gmtStart").setMessage("�Ƽ���ʼ���ڲ��ܴ��ڵ�������");
			return false;
		}
		if (!StringUtils.equals(cp.getCommendContentType(), "3")) {
			// ���Ϊ�޸Ĳ����Ѿ��ϴ���ͼƬ������У��
			if (cc != null && StringUtils.isNotBlank(cc.getPicPath())) {
				return true;
			}
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile replacePic = multipartRequest.getFile("picPath");
			String picUrl = form.getField("picUrl").getValue();
			if ((replacePic == null || replacePic.getSize() == 0)
					&& StringUtils.isBlank(picUrl)) {
				form.getField("picPath").setMessage("���ϴ�ͼƬ����д�Ƽ�ͼƬurl");
				return false;
			}
		}
		return true;
	}

	public void setCommendService(CommendService commendService) {
		this.commendService = commendService;
	}

	public void setFormFactory(FormFactory formFactory) {
		this.formFactory = formFactory;
	}

	public void setUploadService(UploadService uploadService) {
		this.uploadService = uploadService;
	}

}

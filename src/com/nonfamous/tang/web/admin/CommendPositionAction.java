//===================================================================
// Created on Jun 3, 2007
//===================================================================
package com.nonfamous.tang.web.admin;

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
import com.nonfamous.commom.util.HtmlUtils;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.commom.util.web.cookyjar.Cookyjar;
import com.nonfamous.tang.dao.query.CommendPositionQuery;
import com.nonfamous.tang.domain.CommendPosition;
import com.nonfamous.tang.service.admin.CommendService;
import com.nonfamous.tang.service.upload.UploadFileException;
import com.nonfamous.tang.service.upload.UploadService;

/**
 * <p>
 * �Ƽ�λ�ù���
 * </p>
 * 
 * @author jacky
 * @version $Id: CommendPositionAction.java,v 1.1 2008/07/11 00:47:07 fred Exp $
 */

public class CommendPositionAction extends MultiActionController {

	private static final Log logger = LogFactory
			.getLog(CommendPositionAction.class);

	private CommendService commendService;

	private UploadService uploadService;

	private FormFactory formFactory;

	/**
	 * ��ʾ�Ƽ�λ���б�
	 */
	public ModelAndView positionList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("pageTitle", "�Ƽ�λ�ù���");
		CommendPositionQuery query = new CommendPositionQuery();
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
		List positionList = commendService.getCommendPositions(query);
		ModelAndView mav = new ModelAndView(
				"admin/commendPosition/positionList");
		mav.addObject("positionList", positionList);
		mav.addObject("commendPage", commendPage);
		mav.addObject("search", query);
		return mav;
	}

	/**
	 * ��ʾ�Ƽ�λ��
	 */
	public ModelAndView positionView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("pageTitle", "�޸��Ƽ�λ��");

		RequestValueParse rvp = new RequestValueParse(request);
		// �Ƽ�λ��id
		String commendId = rvp.getParameter("commendId").getString();
		// idΪ���򷵻ص��б�
		if (StringUtils.isBlank(commendId)) {
			return new ModelAndView(
					"forward:/admin/commend_position/positionList.htm");
		}
		CommendPosition cp = commendService
				.getCommendPositionByCommendId(commendId);
		if (cp == null) {
			return new ModelAndView(
					"forward:/admin/commend_position/positionList.htm");
		}
		ModelAndView mav = new ModelAndView(
				"admin/commendPosition/positionView");
		Form form = formFactory.getForm("modifyPosition", request);
		// ʶ����
		// form.getField("commendCode").setValue(cp.getCommendCode());
		// ��ʾ��ʽ
		// form.getField("selectview").setValue(cp.getCommendContentType());
		// λ������
		form.getField("commendName").setValue(cp.getCommendName());
		// ҳ��
		form.getField("commendPage").setValue(
				String.valueOf(cp.getCommendPage()));
		// ͼƬ����
		if (cp.getPicHeight() != null)
			form.getField("picHeight").setValue(
					String.valueOf(cp.getPicHeight()));
		// ͼƬ���
		if (cp.getPicWidth() != null)
			form.getField("picWidth")
					.setValue(String.valueOf(cp.getPicWidth()));
		// ���ֳ���
		if (cp.getTextLength() != null)
			form.getField("textLength").setValue(
					String.valueOf(cp.getTextLength()));
		// �滻����
		form.getField("commendPage").setValue(
				String.valueOf(cp.getCommendPage()));
		form.getField("replaceText").setValue(cp.getReplaceText());
		// λ��˳��
		form.getField("positionOrder").setValue(
				String.valueOf(cp.getPositionOrder()));
		request.setAttribute("form", form);
		mav.addObject("commendPosition", cp);
		return mav;
	}

	/**
	 * �����Ƽ�λ��
	 */
	public ModelAndView posAdd(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("pageTitle", "�����Ƽ�λ��");
		Form form = formFactory.getForm("addPosition", request);

		if (!form.isValide() || !preCheckStorePosition(form, request)) {
			request.setAttribute("form", form);
			return new ModelAndView("admin/commendPosition/positionAdd");
		}

		// У��ʶ�����Ƿ��ظ�
		// ʶ����
		String commendCode = form.getField("commendCode").getValue();
		// CommendPosition cp =
		// this.commendService.getCommendPositionByCommendCode(commendCode);
		// if (cp != null) {
		// form.getField("commendCode").setMessage("�Ƽ�ʶ�����ظ�");
		// request.setAttribute("form", form);
		// return new ModelAndView("admin/commendPosition/positionAdd");
		// }

		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		// �û�
		String adminuser = cookyjar.get("adminUserId");
		// ��ʾ��ʽ
		String selectview = form.getField("selectview").getValue();
		// λ������
		String commendName = form.getField("commendName").getValue();
		// ҳ��
		String commendPage = form.getField("commendPage").getValue();
		// ͼƬ����
		String picHeight = form.getField("picHeight").getValue();
		// ͼƬ���
		String picWidth = form.getField("picWidth").getValue();
		// ���ֳ���
		String textLength = form.getField("textLength").getValue();
		// �滻����
		String replaceText = form.getField("replaceText").getValue();
		// ͼƬ
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile replacePic = multipartRequest.getFile("replacePic");

		CommendPosition cp = new CommendPosition();
		// ��������
		cp.setCommendCode(commendCode);
		cp.setCommendContentType(selectview);
		cp.setCommendName(HtmlUtils.escapeScriptTag(commendName));
		cp.setCommendPage(Long.valueOf(commendPage));
		cp.setCreator(adminuser);
		cp.setModifier(adminuser);
		// ����ͼƬ
		if (replacePic != null && replacePic.getSize() > 0
				&& !StringUtils.equals("3", selectview)) {
			try {
				String picPath = this.uploadService.uploadFile(replacePic);
				cp.setPicPath(picPath);
				if (StringUtils.isNotBlank(picHeight)
						&& StringUtils.isNotBlank(picWidth)) {
					cp.setPicHeight(Long.valueOf(picHeight));
					cp.setPicWidth(Long.valueOf(picWidth));
					// ѹ��ͼƬ
					this.uploadService.imageCompress(picPath, Integer
							.valueOf(picWidth), Integer.valueOf(picHeight));
				}
			} catch (UploadFileException ufe) {
				form.getField("replacePic").setMessage(ufe.getMessage());
				request.setAttribute("form", form);
				return new ModelAndView("admin/commendPosition/positionAdd");
			}
		}

		cp.setReplaceText(replaceText);
		if (StringUtils.isNotBlank(textLength))
			cp.setTextLength(Long.valueOf(textLength));
		// ������ ���ͼƬ��Ϣ
		if (StringUtils.equals(selectview, "3")) {
			cp.setPicHeight(null);
			cp.setPicPath(null);
			cp.setPicWidth(null);
		}
		// ��ͼƬ ���������Ϣ
		if (StringUtils.equals(selectview, "2")) {
			cp.setReplaceText(null);
			cp.setTextLength(null);
		}
		// insert
		this.commendService.insertCommendPosition(cp);

		return new ModelAndView(
				"forward:/admin/commend_position/positionList.htm");
	}

	/**
	 * ɾ���Ƽ�λ��
	 */
	public ModelAndView posDel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		// �Ƽ�ID
		String commendId = rvp.getParameter("commendId").getString();
		request.setAttribute("commendId", commendId);
		// �Ƽ�λ��ҳ��
		String commendPage = rvp.getParameter("commendPage").getString();
		request.setAttribute("commendPage", commendPage);
		// idΪ���򷵻ص��б�
		if (StringUtils.isBlank(commendId)) {
			return new ModelAndView(
					"forward:/admin/commend_position/positionList.htm");
		}
		Cookyjar cookyjar = rvp.getCookyjar();
		// �û�
		String modifyuser = cookyjar.get("adminUserId");
		if (logger.isInfoEnabled()) {
			logger.info("delete commend position by " + modifyuser
					+ " position id is :" + commendId);
		}

		// delete
		CommendPosition cp = commendService
				.getCommendPositionByCommendId(commendId);
		if (cp != null) {
			this.commendService.deleteCommendPosition(commendId);
			// ɾͼƬ
			if (StringUtils.isNotBlank(cp.getPicPath()))
				this.uploadService.deleteImageFile(cp.getPicPath());
		}

		return new ModelAndView(
				"forward:/admin/commend_position/positionList.htm");
	}

	/**
	 * �޸��Ƽ�λ��
	 */
	public ModelAndView posModify(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("pageTitle", "�޸��Ƽ�λ��");
		RequestValueParse rvp = new RequestValueParse(request);
		// �Ƽ�ID
		String commendId = rvp.getParameter("commendId").getString();
		request.setAttribute("commendId", commendId);
		CommendPosition cp = this.commendService
				.getCommendPositionByCommendId(commendId);
		Form form = formFactory.getForm("modifyPosition", request);
		if (!form.isValide()) {
			request.setAttribute("form", form);
			request.setAttribute("commendPosition", cp);
			return new ModelAndView("admin/commendposition/positionView");
		}
		// ��ʾ��ʽ �����޸�
		String selectview = form.getField("selectview").getValue();
		// �滻����
		String replaceText = form.getField("replaceText").getValue();
		// ��ʾ��ʽ�а���������ҪУ�������Ƿ�Ϊ��
		if (StringUtils.equals(selectview, "1")
				|| StringUtils.equals(selectview, "3")) {
			if (StringUtils.isBlank(replaceText)) {
				form.getField("replaceText").setMessage("���ֲ���Ϊ��");
				request.setAttribute("form", form);
				request.setAttribute("commendPosition", cp);
				return new ModelAndView("admin/commendposition/positionView");
			}
		}

		/**
		 * ʶ���벻�ܱ��޸� // ʶ���� String commendCode =
		 * form.getField("commendCode").getValue(); // ���ʶ���뱻�޸Ĺ�����ҪУ��Ψһ�� if
		 * (!StringUtils.equals(cp.getCommendCode(), commendCode)) {
		 * CommendPosition temp = this.commendService
		 * .getCommendPositionByCommendCode(commendCode); if (temp != null) {
		 * form.getField("commendCode").setMessage("�Ƽ�ʶ�����ظ�");
		 * request.setAttribute("form", form); return new
		 * ModelAndView("admin/commendposition/positionView"); } }
		 */
		Cookyjar cookyjar = rvp.getCookyjar();
		// �û�
		String modifyuser = cookyjar.get("adminUserId");

		// λ������
		String commendName = form.getField("commendName").getValue();
		// ҳ��
		// String commendPage = form.getField("commendPage").getValue();
		// ͼƬ����
		String picHeight = form.getField("picHeight").getValue();
		// ͼƬ���
		String picWidth = form.getField("picWidth").getValue();
		// ���ֳ���
		String textLength = form.getField("textLength").getValue();
		// ���ֳ���
		String positionOrder = form.getField("positionOrder").getValue();

		// ͼƬ
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile replacePic = multipartRequest.getFile("replacePic");

		// ��������
		// cp.setCommendCode(commendCode);
		// cp.setCommendContentType(selectview);
		cp.setCommendName(HtmlUtils.escapeScriptTag(commendName));
		// cp.setCommendPage(Long.valueOf(commendPage));
		cp.setModifier(modifyuser);
		if (replacePic != null && replacePic.getSize() > 0)
		// && !StringUtils.equals("3", selectview))
		{
			try {
				String picPath = this.uploadService.uploadFile(replacePic);
				cp.setPicPath(picPath);
				if (StringUtils.isNotBlank(picHeight)
						&& StringUtils.isNotBlank(picWidth)) {
					cp.setPicHeight(Long.valueOf(picHeight));
					cp.setPicWidth(Long.valueOf(picWidth));
					// ѹ��ͼƬ
					this.uploadService.imageCompress(picPath, Integer
							.valueOf(picWidth), Integer.valueOf(picHeight));
				}
			} catch (UploadFileException ufe) {
				form.getField("replacePic").setMessage(ufe.getMessage());
				request.setAttribute("form", form);
				request.setAttribute("commendPosition", cp);
				return new ModelAndView("admin/commendposition/positionView");
			}
		}

		cp.setReplaceText(replaceText);
		if (StringUtils.isNotBlank(textLength)) {
			cp.setTextLength(Long.valueOf(textLength));
		}
		cp.setPositionOrder(Long.valueOf(positionOrder));
		// ������ ���ͼƬ��Ϣ
		/*
		 * if (StringUtils.equals(selectview, "3")) { cp.setPicHeight(null);
		 * cp.setPicPath(null); cp.setPicWidth(null); } // ��ͼƬ ���������Ϣ if
		 * (StringUtils.equals(selectview, "2")) { cp.setReplaceText(null);
		 * cp.setTextLength(null); }
		 */
		// update
		this.commendService.updateCommendPosition(cp);

		return new ModelAndView(
				"forward:/admin/commend_position/positionList.htm");
	}

	/**
	 * �����Ƽ�λ�ò���У��
	 * 
	 * @param form
	 */
	private boolean preCheckStorePosition(Form form, HttpServletRequest request) {
		boolean valid = true;
		// ��ʾ��ʽ
		String selectview = form.getField("selectview").getValue();
		request.setAttribute("selectview", selectview);
		// ͼƬ
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		MultipartFile replacePic = multipartRequest.getFile("replacePic");
		// ����
		String replaceText = form.getField("replaceText").getValue();
		// �����ʾ��ʽΪ ͼƬ������
		if (StringUtils.equals(selectview, "1")) {
			if (replacePic == null || replacePic.getSize() == 0) {
				form.getField("replacePic").setMessage("ͼƬ����Ϊ��");
				valid = false;
			}
			if (StringUtils.isBlank(replaceText)) {
				form.getField("replaceText").setMessage("���ֲ���Ϊ��");
				valid = false;
			}
		}
		// ������
		else if (StringUtils.equals(selectview, "3")) {
			if (StringUtils.isBlank(replaceText)) {
				form.getField("replaceText").setMessage("���ֲ���Ϊ��");
				valid = false;
			}
		}
		// ��ͼƬ
		else {
			if (replacePic == null || replacePic.getSize() == 0) {
				form.getField("replacePic").setMessage("ͼƬ����Ϊ��");
				valid = false;
			}
		}
		return valid;
	}

	/**
	 * @param commendService
	 *            The commendService to set.
	 */
	public void setCommendService(CommendService commendService) {
		this.commendService = commendService;
	}

	/**
	 * @param uploadService
	 *            The uploadService to set.
	 */
	public void setUploadService(UploadService uploadService) {
		this.uploadService = uploadService;
	}

	/**
	 * @param formFactory
	 *            The formFactory to set.
	 */
	public void setFormFactory(FormFactory formFactory) {
		this.formFactory = formFactory;
	}

}

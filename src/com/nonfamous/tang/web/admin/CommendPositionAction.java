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
 * 推荐位置管理
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
	 * 显示推荐位置列表
	 */
	public ModelAndView positionList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("pageTitle", "推荐位置管理");
		CommendPositionQuery query = new CommendPositionQuery();
		RequestValueParse rvp = new RequestValueParse(request);
		// 推荐位置页面
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
	 * 显示推荐位置
	 */
	public ModelAndView positionView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("pageTitle", "修改推荐位置");

		RequestValueParse rvp = new RequestValueParse(request);
		// 推荐位置id
		String commendId = rvp.getParameter("commendId").getString();
		// id为空则返回到列表
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
		// 识别码
		// form.getField("commendCode").setValue(cp.getCommendCode());
		// 显示形式
		// form.getField("selectview").setValue(cp.getCommendContentType());
		// 位置名称
		form.getField("commendName").setValue(cp.getCommendName());
		// 页面
		form.getField("commendPage").setValue(
				String.valueOf(cp.getCommendPage()));
		// 图片长度
		if (cp.getPicHeight() != null)
			form.getField("picHeight").setValue(
					String.valueOf(cp.getPicHeight()));
		// 图片宽度
		if (cp.getPicWidth() != null)
			form.getField("picWidth")
					.setValue(String.valueOf(cp.getPicWidth()));
		// 文字长度
		if (cp.getTextLength() != null)
			form.getField("textLength").setValue(
					String.valueOf(cp.getTextLength()));
		// 替换文字
		form.getField("commendPage").setValue(
				String.valueOf(cp.getCommendPage()));
		form.getField("replaceText").setValue(cp.getReplaceText());
		// 位置顺序
		form.getField("positionOrder").setValue(
				String.valueOf(cp.getPositionOrder()));
		request.setAttribute("form", form);
		mav.addObject("commendPosition", cp);
		return mav;
	}

	/**
	 * 新增推荐位置
	 */
	public ModelAndView posAdd(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("pageTitle", "新增推荐位置");
		Form form = formFactory.getForm("addPosition", request);

		if (!form.isValide() || !preCheckStorePosition(form, request)) {
			request.setAttribute("form", form);
			return new ModelAndView("admin/commendPosition/positionAdd");
		}

		// 校验识别码是否重复
		// 识别码
		String commendCode = form.getField("commendCode").getValue();
		// CommendPosition cp =
		// this.commendService.getCommendPositionByCommendCode(commendCode);
		// if (cp != null) {
		// form.getField("commendCode").setMessage("推荐识别码重复");
		// request.setAttribute("form", form);
		// return new ModelAndView("admin/commendPosition/positionAdd");
		// }

		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		// 用户
		String adminuser = cookyjar.get("adminUserId");
		// 显示形式
		String selectview = form.getField("selectview").getValue();
		// 位置名称
		String commendName = form.getField("commendName").getValue();
		// 页面
		String commendPage = form.getField("commendPage").getValue();
		// 图片长度
		String picHeight = form.getField("picHeight").getValue();
		// 图片宽度
		String picWidth = form.getField("picWidth").getValue();
		// 文字长度
		String textLength = form.getField("textLength").getValue();
		// 替换文字
		String replaceText = form.getField("replaceText").getValue();
		// 图片
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile replacePic = multipartRequest.getFile("replacePic");

		CommendPosition cp = new CommendPosition();
		// 设置属性
		cp.setCommendCode(commendCode);
		cp.setCommendContentType(selectview);
		cp.setCommendName(HtmlUtils.escapeScriptTag(commendName));
		cp.setCommendPage(Long.valueOf(commendPage));
		cp.setCreator(adminuser);
		cp.setModifier(adminuser);
		// 处理图片
		if (replacePic != null && replacePic.getSize() > 0
				&& !StringUtils.equals("3", selectview)) {
			try {
				String picPath = this.uploadService.uploadFile(replacePic);
				cp.setPicPath(picPath);
				if (StringUtils.isNotBlank(picHeight)
						&& StringUtils.isNotBlank(picWidth)) {
					cp.setPicHeight(Long.valueOf(picHeight));
					cp.setPicWidth(Long.valueOf(picWidth));
					// 压缩图片
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
		// 仅文字 清除图片信息
		if (StringUtils.equals(selectview, "3")) {
			cp.setPicHeight(null);
			cp.setPicPath(null);
			cp.setPicWidth(null);
		}
		// 仅图片 清除文字信息
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
	 * 删除推荐位置
	 */
	public ModelAndView posDel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		// 推荐ID
		String commendId = rvp.getParameter("commendId").getString();
		request.setAttribute("commendId", commendId);
		// 推荐位置页面
		String commendPage = rvp.getParameter("commendPage").getString();
		request.setAttribute("commendPage", commendPage);
		// id为空则返回到列表
		if (StringUtils.isBlank(commendId)) {
			return new ModelAndView(
					"forward:/admin/commend_position/positionList.htm");
		}
		Cookyjar cookyjar = rvp.getCookyjar();
		// 用户
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
			// 删图片
			if (StringUtils.isNotBlank(cp.getPicPath()))
				this.uploadService.deleteImageFile(cp.getPicPath());
		}

		return new ModelAndView(
				"forward:/admin/commend_position/positionList.htm");
	}

	/**
	 * 修改推荐位置
	 */
	public ModelAndView posModify(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("pageTitle", "修改推荐位置");
		RequestValueParse rvp = new RequestValueParse(request);
		// 推荐ID
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
		// 显示形式 不许修改
		String selectview = form.getField("selectview").getValue();
		// 替换文字
		String replaceText = form.getField("replaceText").getValue();
		// 显示形式中包含文字需要校验文字是否为空
		if (StringUtils.equals(selectview, "1")
				|| StringUtils.equals(selectview, "3")) {
			if (StringUtils.isBlank(replaceText)) {
				form.getField("replaceText").setMessage("文字不能为空");
				request.setAttribute("form", form);
				request.setAttribute("commendPosition", cp);
				return new ModelAndView("admin/commendposition/positionView");
			}
		}

		/**
		 * 识别码不能被修改 // 识别码 String commendCode =
		 * form.getField("commendCode").getValue(); // 如果识别码被修改过，需要校验唯一性 if
		 * (!StringUtils.equals(cp.getCommendCode(), commendCode)) {
		 * CommendPosition temp = this.commendService
		 * .getCommendPositionByCommendCode(commendCode); if (temp != null) {
		 * form.getField("commendCode").setMessage("推荐识别码重复");
		 * request.setAttribute("form", form); return new
		 * ModelAndView("admin/commendposition/positionView"); } }
		 */
		Cookyjar cookyjar = rvp.getCookyjar();
		// 用户
		String modifyuser = cookyjar.get("adminUserId");

		// 位置名称
		String commendName = form.getField("commendName").getValue();
		// 页面
		// String commendPage = form.getField("commendPage").getValue();
		// 图片长度
		String picHeight = form.getField("picHeight").getValue();
		// 图片宽度
		String picWidth = form.getField("picWidth").getValue();
		// 文字长度
		String textLength = form.getField("textLength").getValue();
		// 文字长度
		String positionOrder = form.getField("positionOrder").getValue();

		// 图片
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile replacePic = multipartRequest.getFile("replacePic");

		// 设置属性
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
					// 压缩图片
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
		// 仅文字 清除图片信息
		/*
		 * if (StringUtils.equals(selectview, "3")) { cp.setPicHeight(null);
		 * cp.setPicPath(null); cp.setPicWidth(null); } // 仅图片 清除文字信息 if
		 * (StringUtils.equals(selectview, "2")) { cp.setReplaceText(null);
		 * cp.setTextLength(null); }
		 */
		// update
		this.commendService.updateCommendPosition(cp);

		return new ModelAndView(
				"forward:/admin/commend_position/positionList.htm");
	}

	/**
	 * 新增推荐位置参数校验
	 * 
	 * @param form
	 */
	private boolean preCheckStorePosition(Form form, HttpServletRequest request) {
		boolean valid = true;
		// 显示形式
		String selectview = form.getField("selectview").getValue();
		request.setAttribute("selectview", selectview);
		// 图片
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		MultipartFile replacePic = multipartRequest.getFile("replacePic");
		// 文字
		String replaceText = form.getField("replaceText").getValue();
		// 如果显示形式为 图片加文字
		if (StringUtils.equals(selectview, "1")) {
			if (replacePic == null || replacePic.getSize() == 0) {
				form.getField("replacePic").setMessage("图片不能为空");
				valid = false;
			}
			if (StringUtils.isBlank(replaceText)) {
				form.getField("replaceText").setMessage("文字不能为空");
				valid = false;
			}
		}
		// 仅文字
		else if (StringUtils.equals(selectview, "3")) {
			if (StringUtils.isBlank(replaceText)) {
				form.getField("replaceText").setMessage("文字不能为空");
				valid = false;
			}
		}
		// 仅图片
		else {
			if (replacePic == null || replacePic.getSize() == 0) {
				form.getField("replacePic").setMessage("图片不能为空");
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

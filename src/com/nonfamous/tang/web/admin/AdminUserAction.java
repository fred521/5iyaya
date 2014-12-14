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

import com.nonfamous.commom.form.DefaultFormFactory;
import com.nonfamous.commom.form.Form;
import com.nonfamous.commom.util.HtmlUtils;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.commom.util.web.cookyjar.Cookyjar;
import com.nonfamous.tang.dao.home.MarketTypeDAO;
import com.nonfamous.tang.domain.AdminUser;
import com.nonfamous.tang.domain.Member;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.domain.ShopCommend;
import com.nonfamous.tang.domain.result.ShopResult;
import com.nonfamous.tang.service.admin.AdminUserService;
import com.nonfamous.tang.service.home.MemberService;
import com.nonfamous.tang.service.home.ShopService;
import com.nonfamous.tang.service.upload.UploadFileException;
import com.nonfamous.tang.service.upload.UploadService;
import com.nonfamous.tang.web.common.Constants;

/**
 * @author eyeieye
 * @version $Id: AdminUserAction.java,v 1.1 2008/07/11 00:47:06 fred Exp $
 */
public class AdminUserAction extends MultiActionController {
	private static final Log logger = LogFactory.getLog(AdminUserAction.class);

	private AdminUserService adminUserService;

	private DefaultFormFactory formFactory;

	private ShopService shopService;

	private UploadService uploadService;

	private MarketTypeDAO marketTypeDAO;

	private MemberService memberService;

	public void setFormFactory(DefaultFormFactory formFactory) {
		this.formFactory = formFactory;
	}

	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}

	public void setUploadService(UploadService uploadService) {
		this.uploadService = uploadService;
	}

	public void setMarketTypeDAO(MarketTypeDAO marketTypeDAO) {
		this.marketTypeDAO = marketTypeDAO;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	/*
	 * 进入修改口令页面
	 */
	public ModelAndView cg_pwd_init(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/adminUser/cgPwdInit");
	}

	/*
	 * 修改口令
	 */
	public ModelAndView cg_pwd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean canChangePwd = true;
		RequestValueParse rvp = new RequestValueParse(request);
		String originalPwd = rvp.getParameter("original_pwd").getString();
		String newPwdOne = rvp.getParameter("new_pwd_one").getString();
		String newPwdTwo = rvp.getParameter("new_pwd_two").getString();
		Cookyjar cookyjar = rvp.getCookyjar();
		Long adminUserId = new Long(cookyjar.get(Constants.AdminUserId_Cookie));
		AdminUser adminUser = adminUserService.getAdminUserById(adminUserId);
		if (originalPwd == null) {
			canChangePwd = false;
			request.setAttribute("originalErrorMessage", "原口令不能为空");
		} else {
			adminUser.setUnencryptPassword(originalPwd);
			if (!adminUser.isPasswordCorrect()) {
				canChangePwd = false;
				request.setAttribute("originalErrorMessage", "原口令不正确");
			}
		}
		if (newPwdOne == null) {
			canChangePwd = false;
			request.setAttribute("newPwdOneErrorMessage", "新口令不能为空");
		}
		if (newPwdTwo == null) {
			canChangePwd = false;
			request.setAttribute("newPwdTwoErrorMessage", "确认口令不能为空");
		}
		if (newPwdOne != null && newPwdTwo != null
				&& !newPwdOne.equals(newPwdTwo)) {
			canChangePwd = false;
			request.setAttribute("newPwdTwoErrorMessage", "新口令不一致");
		}
		if (!canChangePwd) {
			return new ModelAndView("admin/adminUser/cgPwdInit");
		}
		adminUser.setNewPassword(newPwdOne);
		adminUser.setModifier(adminUser.getLoginName());
		adminUserService.updatePassword(adminUser);
		if (logger.isInfoEnabled()) {
			logger.info("user with id:" + adminUserId
					+ " update his password success");
		}
		return new ModelAndView("admin/adminUser/cgPwdSuccess");
	}

	/*
	 * 显示所有的管理列表
	 */
	public ModelAndView list_all(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<AdminUser> users = this.adminUserService.findAll();
		return new ModelAndView("admin/adminUser/listAll", "users", users);
	}

	public AdminUserService getAdminUserService() {
		return adminUserService;
	}

	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	/**
	 * 后台新增卖家店铺
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView shop_edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String memberId = rvp.getParameter("memberId").getString();
		ModelAndView mav = new ModelAndView("admin/adminUser/shopEdit");
		if (StringUtils.isNotBlank(memberId)) {
			Shop shop = shopService.shopfullSelectByMemberId(memberId);
			// //拿市场类别，直接拿，等待优化
			List MarketType = (List) marketTypeDAO.getAllMarketType();
			mav.addObject("marketType", MarketType);
			mav.addObject("memberId", memberId);
			if (shop == null) {
				return mav;
			}
			Form form = formFactory.getForm("adminShop", request);

			form.getField("shopName").setValue(shop.getShopName());
			form.getField("commodity").setValue(shop.getCommodity());
			form.getField("shopOwner").setValue(shop.getShopOwner());
			form.getField("belongMarketId").setValue(shop.getBelongMarketId());
			form.getField("address").setValue(shop.getAddress());
			form.getField("phone").setValue(shop.getPhone());
			form.getField("bank").setValue(shop.getBank());
			form.getField("bankAccount").setValue(shop.getBankAccount());
			form.getField("accountName").setValue(shop.getAccountName());

			form.getField("gisAddress").setValue(shop.getGisAddress());

			form.getField("commend").setValue(
					shop.getShopCommend().getCommend());
			mav.addObject("shopId", shop.getShopId());
			mav.addObject("shopImg", shop.getShopImg());
			mav.addObject("logo", shop.getLogo());

			request.setAttribute("form", form);
		}

		return mav;
	}

	/**
	 * 后台编辑卖家店铺
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView shop_update(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		Form form = formFactory.getForm("adminShop", request);
		String memberId = rvp.getParameter("memberId").getString();
		String imgUrl = rvp.getParameter("img").getString();
		String logoUrl = rvp.getParameter("logo").getString();
		ModelAndView mav = new ModelAndView("admin/adminUser/shopEdit");
		boolean isAdd = false;
		if (!form.isValide()) {
			request.setAttribute("memberId", memberId);
			request.setAttribute("form", form);
			// 拿市场类别，直接拿，等待优化
			List MarketType = (List) marketTypeDAO.getAllMarketType();

			mav.addObject("marketType", MarketType);
			mav.addObject("shopImg", imgUrl);
			mav.addObject("logo", logoUrl);
			return mav;
		}
		Cookyjar cookyjar = rvp.getCookyjar();
		String adminUserId = cookyjar.get(Constants.AdminUserId_Cookie);

		if (StringUtils.isNotBlank(memberId)) {
			Shop shop = shopService.shopfullSelectByMemberId(memberId);
			if (shop == null) {
				isAdd = true;
				// 新建操作
				shop = new Shop();
				shop.setMemberId(memberId);
				Member member = memberService.getMemberById(memberId);
				String loginId = member.getLoginId();
				shop.setLoginId(loginId);
			}

			shop.setShopName(HtmlUtils.parseHtml(form.getField("shopName")
					.getValue()));
			shop.setCommodity(form.getField("commodity").getValue());
			shop.setShopOwner(form.getField("shopOwner").getValue());
			shop.setBelongMarketId(form.getField("belongMarketId").getValue());
			shop.setAddress(form.getField("address").getValue());
			shop.setPhone(form.getField("phone").getValue());
			shop.setBank(form.getField("bank").getValue());
			shop.setBankAccount(form.getField("bankAccount").getValue());
			shop.setAccountName(form.getField("accountName").getValue());
			// gis address
			shop.setGisAddress(form.getField("gisAddress").getValue());
			// 上传店铺img和logo

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile shopImg = multipartRequest.getFile("shopImg");
			MultipartFile shopLogo = multipartRequest.getFile("shopLogo");
			try {
				// 判断上传的店铺图标,如果没有上传,则返回原始地址
				if (shopImg != null && shopImg.getSize() > 0) {
					imgUrl = uploadService.uploadShopImageWithCompress(shopImg);
					mav.addObject("shopImg", imgUrl);
				} else {
					imgUrl = rvp.getParameter("img").getString();
				}
				// 判断上传的形象图片,如果没有上传,则返回原始地址
				if (shopLogo != null && shopLogo.getSize() > 0) {
					logoUrl = uploadService.uploadFile(shopLogo);
					uploadService.imageCompress(logoUrl, 120, 60);
					mav.addObject("logo", logoUrl);
				} else {
					logoUrl = rvp.getParameter("logo").getString();
				}
			} catch (UploadFileException e) {
				request.setAttribute("memberId", memberId);
				request.setAttribute("form", form);
				// 拿市场类别，直接拿，等待优化
				List MarketType = (List) marketTypeDAO.getAllMarketType();
				mav.addObject("uploadError", e.getMessage());
				mav.addObject("marketType", MarketType);
				mav.addObject("shopImg", imgUrl);
				mav.addObject("logo", logoUrl);
				return mav;
			}

			shop.setShopImg(imgUrl);
			shop.setLogo(logoUrl);
			shop.setModifier(adminUserId);

			if (!isAdd) {
				// update操作
				// 更新店铺
				try {
					shopService.updateShop(shop);
				} catch (Exception e) {
					request.setAttribute("memberId", memberId);
					request.setAttribute("form", form);
					// 拿市场类别，直接拿，等待优化
					List MarketType = (List) marketTypeDAO.getAllMarketType();
					mav.addObject("error", e.getMessage());
					mav.addObject("marketType", MarketType);
					mav.addObject("shopImg", imgUrl);
					mav.addObject("logo", logoUrl);
					return mav;
				}
				// 更新店铺公告
				ShopCommend commend = new ShopCommend();
				String notes = form.getField("commend").getValue();
				commend.setShopId(shop.getShopId());
				commend.setCommend(HtmlUtils.escapeScriptTag(notes));
				Shop tmpShop = new Shop();
				tmpShop.setShopId(shop.getShopId());

				shopService.updateShopCommend(commend, tmpShop);
			} else {

				// 更新店铺公告
				ShopCommend commend = new ShopCommend();
				String notes = form.getField("commend").getValue();
				commend.setCommend(HtmlUtils.escapeScriptTag(notes));

				shop.setShopCommend(commend);
				shop.setCreator(adminUserId);
				ShopResult result = shopService.insertShop(shop, false);
				if (!result.isSuccess()) {
					request.setAttribute("memberId", memberId);
					request.setAttribute("form", form);
					// 拿市场类别，直接拿，等待优化
					List MarketType = (List) marketTypeDAO.getAllMarketType();
					mav.addObject("error", result.getErrorMessage());
					mav.addObject("marketType", MarketType);
					mav.addObject("shopImg", imgUrl);
					mav.addObject("logo", logoUrl);
					return mav;

				}

			}
		}
		if (isAdd) {
			request.setAttribute("success", "店铺新增成功");
		} else {
			request.setAttribute("success", "店铺修改成功");
		}
		return shop_edit(request, response);
		// return new
		// ModelAndView("forward:/admin/admin_member/seller_list.htm");
	}
}

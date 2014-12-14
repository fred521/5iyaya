/**
 *
 */
package com.nonfamous.tang.web.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.nonfamous.tang.dao.query.GoodsQuery;
import com.nonfamous.tang.domain.GoodsBaseInfo;
import com.nonfamous.tang.domain.Member;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.domain.ShopCommend;
import com.nonfamous.tang.exception.ServiceException;
import com.nonfamous.tang.service.home.GoodsService;
import com.nonfamous.tang.service.home.MemberService;
import com.nonfamous.tang.service.home.ShopService;
import com.nonfamous.tang.service.upload.UploadFileException;
import com.nonfamous.tang.service.upload.UploadService;
import com.nonfamous.tang.web.common.Constants;
import com.nonfamous.tang.web.common.SearchTypeConstants;

/**
 * @author fred
 *
 */
public class ShopAction extends MultiActionController {

	private final String NORMAL_STATUS = "N";

	private static final String SUCCESS_INFO = "success";

	private static final String ERROR_INFO = "error";

	private DefaultFormFactory formFactory;

	private ShopService shopService;

	private UploadService uploadService;

	private GoodsService goodsService;

	private MemberService memberService;

	private MarketTypeDAO marketTypeDAO;

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

	public void setGoodsService(GoodsService goodsService) {
		this.goodsService = goodsService;
	}

	/**
	 * ҳ����ʾ
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addNotes(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		String memberId = cookyjar.get(Constants.MemberId_Cookie);
		ShopCommend commend = shopService.getShopCommendByMemberId(memberId);
		ModelAndView mav = new ModelAndView("home/shop/addNotes");
		Form form = formFactory.getForm("addNotes", request);

		if (commend != null) {
			form.getField("commend").setValue(commend.getCommend());
			mav.addObject("shopId", commend.getShopId());
		} else {
			request.setAttribute(ERROR_INFO, "������Ϣ������");
		}

		request.setAttribute("form", form);
		return mav;
	}

	/**
	 * �޸ĵ��̹���
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView updateNotes(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		// ����ID
		String shopId = rvp.getParameter("shopId").getString();
		if (StringUtils.isBlank(shopId)) {
			request.setAttribute(ERROR_INFO, "������Ϣ������");
			return new ModelAndView("home/shop/addNotes");
		}
		Form form = formFactory.getForm("addNotes", request);
		if (!form.isValide()) {
			request.setAttribute("form", form);
			request.setAttribute("shopId", shopId);
			return new ModelAndView("home/shop/addNotes");
		}

		// �ж��Ƿ����ڸĻ�Ա
		Cookyjar cookyjar = rvp.getCookyjar();
		String memberId = cookyjar.get(Constants.MemberId_Cookie);
		ShopCommend cd = shopService.getShopCommendByMemberId(memberId);
		if (cd != null && cd.getShopId().equals(shopId)) {
			ShopCommend commend = new ShopCommend();
			commend.setShopId(shopId);
			commend.setCommend(HtmlUtils.escapeScriptTag(form.getField(
					"commend").getValue()));
			Shop shop = new Shop();
			shop.setModifier(memberId);
			shop.setShopId(shopId);
			shopService.updateShopCommend(commend, shop);
			request.setAttribute(SUCCESS_INFO, "�����ɹ�");
			return new ModelAndView("forward:/myshop/addNotes.htm");
		} else {
			request.setAttribute("form", form);
			request.setAttribute("shopId", shopId);
			request.setAttribute(ERROR_INFO, "����ʧ��");
			return new ModelAndView("home/shop/addNotes");
		}

	}

	public ModelAndView updateShop(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("home/shop/updateShop");
		// ���г����
		List marketType = (List) marketTypeDAO.getAllMarketType();
		mav.addObject("marketType", marketType);

		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		String memberId = cookyjar.get(Constants.MemberId_Cookie);
		Shop shop = shopService.shopSelectByMemberId(memberId);
		Form form = formFactory.getForm("updateShop", request);
		if (shop != null) {
			form.getField("shopName").setValue(shop.getShopName());
			form.getField("commodity").setValue(shop.getCommodity());
			form.getField("shopOwner").setValue(shop.getShopOwner());
			form.getField("belongMarketId").setValue(shop.getBelongMarketId());
			form.getField("address").setValue(shop.getAddress());
			
			//���̵���λ��
			form.getField("gisAddress").setValue(shop.getGisAddress());
			
			form.getField("phone").setValue(shop.getPhone());  
			form.getField("bank").setValue(shop.getBank());
			form.getField("bankAccount").setValue(shop.getBankAccount());
			form.getField("accountName").setValue(shop.getAccountName());

			mav.addObject("shopId", shop.getShopId());
		} else {
			request.setAttribute(ERROR_INFO, "������Ϣ������");
		}
		request.setAttribute("form", form);
		return mav;
	}

	public ModelAndView saveShop(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		Form form = formFactory.getForm("updateShop", request);
		String shopId = rvp.getParameter("shopId").getString();
		if (!form.isValide()) {
			request.setAttribute("shopId", shopId);
			request.setAttribute("form", form);
			List marketType = (List) marketTypeDAO.getAllMarketType();
			ModelAndView mav = new ModelAndView("home/shop/updateShop");
			mav.addObject("marketType", marketType);
			return mav;
		}

		Cookyjar cookyjar = rvp.getCookyjar();
		String memberId = cookyjar.get(Constants.MemberId_Cookie);
		Shop memberShop = shopService.shopSelectByMemberId(memberId);
		if (memberShop != null && memberShop.getShopId().equals(shopId)) {

			Shop shop = new Shop();
			shop.setShopId(shopId);
			shop.setShopName(HtmlUtils.parseHtml(form.getField("shopName")
					.getValue()));
			shop.setCommodity(form.getField("commodity").getValue());
			shop.setShopOwner(form.getField("shopOwner").getValue());
			shop.setBelongMarketId(form.getField("belongMarketId").getValue());
			shop.setAddress(form.getField("address").getValue());
			
			//���̵���λ��
		    shop.setGisAddress(form.getField("gisAddress").getValue());
			
			shop.setPhone(form.getField("phone").getValue());
			shop.setBank(form.getField("bank").getValue());
			shop.setBankAccount(form.getField("bankAccount").getValue());
			shop.setAccountName(form.getField("accountName").getValue());
			shop.setModifier(memberId);
			shopService.updateShop(shop);
			request.setAttribute(SUCCESS_INFO, "�����ɹ�");

		}
		return new ModelAndView("forward:/myshop/updateShop.htm");
	}

	public ModelAndView updateLogo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		String memberId = cookyjar.get(Constants.MemberId_Cookie);
		Shop shop = shopService.shopSelectByMemberId(memberId);
		ModelAndView mav = new ModelAndView("home/shop/updateLogo");
		if (shop != null) {
			mav.addObject("shopId", shop.getShopId());
			mav.addObject("shopImg", shop.getShopImg());
			mav.addObject("logo", shop.getLogo());
		} else {
			request.setAttribute(ERROR_INFO, "������Ϣ������");
		}
		return mav;
	}

	public ModelAndView saveLogo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// �ж��Ƿ�Ҫ���ݿ����
		boolean needsave = false;
		String imgUrl = "";
		String logoUrl = "";

		RequestValueParse rvp = new RequestValueParse(request);
		String shopId = rvp.getParameter("shopId").getString();

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile shopImg = multipartRequest.getFile("shopImg");
		MultipartFile shopLogo = multipartRequest.getFile("shopLogo");
		ModelAndView mav = new ModelAndView("home/shop/updateLogo");
		// �ж��ϴ��ĵ���ͼ��,���û���ϴ�,�򷵻�ԭʼ��ַ
		if (shopImg != null && shopImg.getSize() > 0) {
			try{
			imgUrl = uploadService.uploadShopImageWithCompress(shopImg);// 2��ͼƬ��1��ԭͼ��һ����.l.jpg
			}catch(UploadFileException e){
				request.setAttribute("uploadShopError", e.getMessage());
				return updateLogo(request,response);
			}
			mav.addObject("shopImg", imgUrl);
			needsave = true;
		} else {
			imgUrl = rvp.getParameter("img").getString();
		}
		// �ж��ϴ�������ͼƬ,���û���ϴ�,�򷵻�ԭʼ��ַ
		if (shopLogo != null && shopLogo.getSize() > 0) {
			try{
			logoUrl = uploadService.uploadFile(shopLogo);// ԭͼ
			uploadService.imageCompress(logoUrl, 120, 60);// .n.jpg
			}catch(UploadFileException e){
				request.setAttribute("uploadLogoError", e.getMessage());
				return updateLogo(request,response);
			}
			mav.addObject("logo", logoUrl);
			needsave = true;
		} else {
			logoUrl = rvp.getParameter("logo").getString();
		}
		// �������ݿ�,update
		if (needsave && StringUtils.isNotEmpty(shopId)) {
			// Shop shop = shopService.shopSelectByShopId(shopId);
			Cookyjar cookyjar = rvp.getCookyjar();
			String memberId = cookyjar.get(Constants.MemberId_Cookie);
			Shop memberShop = shopService.shopSelectByMemberId(memberId);
			if (memberShop != null && memberShop.getShopId().equals(shopId)) {
				Shop shop = new Shop();
				shop.setShopId(shopId);
				shop.setShopImg(imgUrl);
				shop.setLogo(logoUrl);
				shop.setModifier(memberId);
				shopService.updateShop(shop);
				request.setAttribute(SUCCESS_INFO, "�����ɹ�");
			}
		}
		return new ModelAndView("forward:/myshop/updateLogo.htm");
	}

	/**
	 * �鿴����
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);

		String shopId = rvp.getParameterOrAttribute("id").getString();
		String address = rvp.getParameterOrAttribute("address").getString();
		String orderBy = rvp.getParameter("order").getString();
		String page = rvp.getParameter("page").getString();
		String goodsType = rvp.getParameter("type").getString();
		
		ModelAndView mav = new ModelAndView("home/shop/viewShop");
		if (StringUtils.isNotBlank(shopId) || StringUtils.isNotBlank(address)) {
			Shop shop = null;
			Member seller=null;

			if (StringUtils.isNotBlank(address)) {
				shop = shopService.shopfullSelectByShopId(null, shopId);

			}else if (StringUtils.isNotBlank(shopId)) {
				shop = shopService.shopfullSelectByShopId(shopId, null);
			}
			if (shop == null) {
				throw new ServiceException("��Ҫ�ҵĵ�����Ϣ�����ڣ����Ѿ�ɾ��");
			}
			String memberId =shop.getMemberId();
			if(StringUtils.isNotEmpty(memberId)){
			 seller=memberService.getMemberById(memberId);}
			GoodsQuery query = new GoodsQuery();
			query.setCurrentPageString(page);
			query.setGoodsStatus(NORMAL_STATUS);
			query.setShopId(shop.getShopId());
			if (StringUtils.isNotBlank(orderBy)) {
				if (orderBy.equals("0")) {
					query.setOrderBy("DESC");
				}
				if (orderBy.equals("1")) {
					query.setOrderBy("ASC");
				}
				mav.addObject("orderBy", orderBy);
			}
			//�Ź�����������
			if (StringUtils.isNotBlank(goodsType)) {
				query.setGoodsType(goodsType);
			}
			List<GoodsBaseInfo> goods = goodsService.getActiveShopGoods(query)
					.getGoods();
			shop.setGoods(goods);
			mav.addObject("query", query);
			mav.addObject("shop", shop);
			mav.addObject("member", seller);
			// ҳ�����
			request.setAttribute(Constants.PageTitle_Page, shop.getShopName());
		} else {
			throw new ServiceException("��Ҫ�ҵĵ�����Ϣ�����ڣ����Ѿ�ɾ��");
		}
		request.setAttribute("searchType", SearchTypeConstants.Search_Shop);
		request.setAttribute("goodsType", goodsType);
		return mav;
	}

	public ModelAndView info(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("address", request.getParameter("id"));
		return detail(request,response);
	}

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

}
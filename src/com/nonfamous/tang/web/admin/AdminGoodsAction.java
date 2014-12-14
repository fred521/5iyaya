package com.nonfamous.tang.web.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.nonfamous.commom.util.web.RequestValue;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.tang.dao.home.GoodsCatDAO;
import com.nonfamous.tang.dao.home.PictureDAO;
import com.nonfamous.tang.dao.query.GoodsQuery;
import com.nonfamous.tang.domain.GoodsBaseInfo;
import com.nonfamous.tang.domain.GoodsCat;
import com.nonfamous.tang.domain.Member;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.exception.ServiceException;
import com.nonfamous.tang.service.home.GoodsCatalogService;
import com.nonfamous.tang.service.home.GoodsService;
import com.nonfamous.tang.service.home.MemberService;
import com.nonfamous.tang.service.home.ShopService;
import com.nonfamous.tang.service.upload.UploadFileException;
import com.nonfamous.tang.service.upload.UploadService;
import com.nonfamous.tang.web.common.Constants;

/**
 * 
 * @author alien
 * 
 */
public class AdminGoodsAction extends MultiActionController {

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(AdminGoodsAction.class);

	private FormFactory formFactory;

	private GoodsService goodsService;

	private MemberService memberService;

	private ShopService shopService;

	private GoodsCatDAO goodsCatDAO;
	
	private PictureDAO pictureDAO;

	private GoodsCatalogService goodsCatalogService;
	
	private UploadService uploadService;

	/**
	 * 新增商品时初始化
	 */
	public ModelAndView add_init(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("admin/adminGoods/adminGoodsAdd");
		mv.addObject("goodsCats", goodsCatDAO.getAllGoodsCat());
		mv.addObject("rootCats", goodsCatDAO.getRootGoodsCat());
		return mv;
	}

	/**
	 * 新增商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Form form = formFactory.getForm("adminAddGoods", request);
		ModelAndView mv = new ModelAndView("admin/adminGoods/adminGoodsAdd");
		if (!form.isValide()) {
			request.setAttribute("form", form);
			mv.addObject("goodsCats", goodsCatDAO.getAllGoodsCat());
			mv.addObject("rootCats", goodsCatDAO.getRootGoodsCat());
			mv.addObject("memberId", request.getParameter("memberId").trim());
			mv.addObject("shopName", request.getParameter("shopName").trim());
			return mv;
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		RequestValueParse parse = new RequestValueParse(multipartRequest);

		GoodsBaseInfo goods = getParam(multipartRequest);
		GoodsCat gc = this.goodsCatalogService.getGoodsCatById(goods
				.getGoodsCat());
		if (gc == null) {
			form.getField("thirdCat").setMessage("请选择三级分类");
			request.setAttribute("form", form);
			mv.addObject("goodsCats", goodsCatDAO.getAllGoodsCat());
			mv.addObject("rootCats", goodsCatDAO.getRootGoodsCat());
			mv.addObject("memberId", request.getParameter("memberId").trim());
			mv.addObject("shopName", request.getParameter("shopName").trim());
			return mv;
		}

		String admin = parse.getCookyjar().get(Constants.AdminUserId_Cookie);

		Member member = memberService.getMemberById(goods.getMemberId());
		if (member == null) {
			throw new ServiceException("您选择的会员帐号不存在，不允许添加商品");
		}
		Shop shop = shopService.shopSelectByMemberId(goods.getMemberId());
		if (shop == null) {
			throw new ServiceException("您选择的会员当前没有店铺，不允许添加商品");
		}
		goods.setShopId(shop.getShopId());
		goods.setCreator(admin);
		goods.setModifier(admin);
		goods.setGoodsStatus(GoodsBaseInfo.NORMAL_STATUS);
		MultipartFile file = multipartRequest.getFile("goodsPic");
		//判断图片是否合法
		if (file != null && file.getSize() > 0) {
			try {
				goods.setGoodsPic(uploadService
						.uploadGoodsImageWithCompress(file));
			} catch (Exception e) {
				request.setAttribute("uploadGoodsError", e.getMessage());
				request.setAttribute("form", form);
				mv.addObject("goodsCats", goodsCatDAO.getAllGoodsCat());
				mv.addObject("rootCats", goodsCatDAO.getRootGoodsCat());
				mv.addObject("memberId", request.getParameter("memberId").trim());
				mv.addObject("shopName", request.getParameter("shopName").trim());
				return mv;
			}
			
		}
		goodsService.addGoods(goods, null);
		

		/**
		 * 新增成功跳转到新增初始页面，并且保留前次选择的店铺信息
		 */
		request.setAttribute("memberId", request.getParameter("memberId").trim());
		request.setAttribute("shopName", request.getParameter("shopName").trim());
		request.setAttribute("success", "商品新增成功，您可以继续新增商品");
		return add_init(request,response);
	}

	/**
	 * 修改商品时初始化
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView update_init(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("admin/adminGoods/adminGoodsUpdate");
		String goodsId = request.getParameter("goodsId");
		GoodsBaseInfo goods = goodsService.getGoodsWholeInfo(goodsId);
		Form form = formFactory.getForm("adminAddGoods", request);
		form.getField("goodsName").setValue(goods.getGoodsName());
		// 商品目录路径，至少两级，可能有三级
		String[] goodsCatArray = StringUtils.split(goods.getCatPath(),
				GoodsCat.PathSeparator);
		if (goodsCatArray != null) {
			form.getField("firstCat").setValue(goodsCatArray[0]);
			if (goodsCatArray.length > 1) {
				form.getField("secondCat").setValue(goodsCatArray[1]);
			}
			if (goodsCatArray.length == 3) {
				form.getField("thirdCat").setValue(goodsCatArray[2]);
			}
		}
		form.getField("batchPrice")
				.setValue(goods.getGoodsBatchPriceInFormat());
		form.getField("marketPrice")
		.setValue(goods.getGoodsMarketPriceInFormat());
		form.getField("batchNum").setValue(Long.toString(goods.getBatchNum()));
		form.getField("priceDes").setValue(goods.getPriceDes());
		form.getField("goodsNum").setValue(Long.toString(goods.getGoodsNum()));
		form.getField("goodsPic").setValue(goods.getGoodsPic());
		form.getField("abandonDays").setValue(
				Long.toString(goods.getAbandonDays()));
		form.getField("content").setValue(goods.getGoodsContent().getContent());
		Member member = memberService.getMemberById(goods.getMemberId());
		mv.addObject("member", member);
		mv.addObject("goodsId", goodsId);
		mv.addObject("memberId", goods.getMemberId());
		mv.addObject("form", form);
		mv.addObject("goodsCats", goodsCatDAO.getAllGoodsCat());
		mv.addObject("rootCats", goodsCatDAO.getRootGoodsCat());

		return mv;
	}

	/**
	 * 修改商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView update(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Form form = formFactory.getForm("adminAddGoods", request);
		ModelAndView mv = new ModelAndView("admin/adminGoods/adminGoodsUpdate");
		if (!form.isValide()) {
			request.setAttribute("form", form);
			mv.addObject("goodsCats", goodsCatDAO.getAllGoodsCat());
			mv.addObject("rootCats", goodsCatDAO.getRootGoodsCat());
			mv.addObject("goodsId", request.getParameter("goodsId").trim());
			mv.addObject("memberId", request.getParameter("memberId").trim());
			return mv;
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		RequestValueParse parse = new RequestValueParse(multipartRequest);
		GoodsBaseInfo goods = getParam(multipartRequest);
		GoodsCat gc = this.goodsCatalogService.getGoodsCatById(goods
				.getGoodsCat());
		if (gc == null) {
			form.getField("thirdCat").setMessage("请选择三级分类");
			request.setAttribute("form", form);
			mv.addObject("goodsCats", goodsCatDAO.getAllGoodsCat());
			mv.addObject("rootCats", goodsCatDAO.getRootGoodsCat());
			mv.addObject("goodsId", request.getParameter("goodsId").trim());
			mv.addObject("memberId", request.getParameter("memberId").trim());
			return mv;
		}
		Member member = memberService.getMemberById(goods.getMemberId());
		if (member == null) {
			throw new ServiceException("您选择的会员帐号不存在，不允许修改商品");
		}
		Shop shop = shopService.shopSelectByMemberId(goods.getMemberId());
		if (shop == null) {
			throw new ServiceException("您选择的会员帐号当前没有店铺，不允许修改商品");
		}
		goods.setShopId(shop.getShopId());
		goods.setGoodsId(parse.getParameter("goodsId").getString());
		MultipartFile file = multipartRequest.getFile("goodsPic");
		//判断图片是否合法
		if (file != null && file.getSize() > 0) {
			try {
				goods.setGoodsPic(uploadService
						.uploadGoodsImageWithCompress(file));
			} catch (UploadFileException e) {
				request.setAttribute("uploadGoodsError", e.getMessage());
				request.setAttribute("form", form);
				mv.addObject("goodsCats", goodsCatDAO.getAllGoodsCat());
				mv.addObject("rootCats", goodsCatDAO.getRootGoodsCat());
				mv.addObject("goodsId", request.getParameter("goodsId").trim());
				mv.addObject("memberId", request.getParameter("memberId").trim());
				return mv;
			}
		}		
		String admin = parse.getCookyjar().get(Constants.AdminUserId_Cookie);
		goods.setModifier(admin);
		goods.setGoodsStatus(GoodsBaseInfo.NORMAL_STATUS);
		GoodsBaseInfo ori = goodsService.getGoodsBaseInfo(parse.getParameter(
				"goodsId").getString());
		if (!ori.getAbandonDays().equals(goods.getAbandonDays())) {
			Calendar c = Calendar.getInstance();
			c.setTime(ori.getGmtCreate());
			c.add(Calendar.DATE, goods.getAbandonDays().intValue());
			goods.setGmtAbandon(c.getTime());
		}
		goodsService.updateGoods(goods, null);
        request.setAttribute("success", "商品修改成功");
		return update_init(request,response);
		//return this.list_goods(request, response);
	}

	/**
	 * 删除商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String goodsId = request.getParameter("goodsId").trim();
		goodsService.deleteGoods(goodsId);

		return this.list_goods(request, response);
	}

	/**
	 * 组装查询条件
	 * 
	 * @param request
	 * @return
	 */
	private GoodsQuery requestToQuery(HttpServletRequest request) {
		GoodsQuery query = new GoodsQuery();
		RequestValueParse rvp = new RequestValueParse(request);
		String currentPage = rvp.getParameter("currentPage").getString();
		if (StringUtils.isNotBlank(currentPage)) {
			query.setCurrentPage(Integer.valueOf(currentPage));
		}
		if (StringUtils.isNotBlank(rvp.getParameter("shop_id").getString())) {
			query.setShopId(rvp.getParameter("shop_id").getString());
		}
		// 关键字类型
		String keyType = rvp.getParameter("keyType").getString();
		// 关键字
		String keyWord = rvp.getParameter("keyWord").getString();

		if (StringUtils.isBlank(keyWord)) {
			return query;
		}
		request.setAttribute("keyType", keyType);
		request.setAttribute("keyWord", keyWord);

		if (StringUtils.equals(keyType, "goodsName")) {
			query.setGoodsName(keyWord);
		} else if (StringUtils.equals(keyType, "goodsCat")) {
			GoodsCat gc = goodsCatalogService.getGoosCatByName(keyWord);
			if (gc != null) {
				query.setGoodsCat(gc.getTypeId());
			} else {
				query.setGoodsCat("NULL_NULL");
			}
		} else if (StringUtils.equals(keyType, "loginId")) {
			query.setLoginId(keyWord);
		} else if (StringUtils.equals(keyType, "shopOwner")) {
			query.setShopOwner(keyWord);
		} else if (StringUtils.equals(keyType, "nick")) {
			query.setNick(keyWord);
		} else if (StringUtils.equals(keyType, "mobile")) {
			query.setMobile(keyWord);
		}

		return query;
	}

	/**
	 * 商品列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list_goods(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mv = new ModelAndView("admin/adminGoods/adminGoodsList");
		GoodsQuery query = this.requestToQuery(request);

		query = goodsService.getGoodsList(query);
		request.setAttribute("query", query);
		if (query.getTotalItem() == 0) {
			return mv;
		}
		mv.addObject("list", query.getGoods());
		request.setAttribute("member", getMembers(query.getGoods()));

		List<List<GoodsCat>> goodsCats = new ArrayList<List<GoodsCat>>();

		for (GoodsBaseInfo goods : query.getGoods()) {
			List<String> catIds = goods.getCatalogIds();
			List<GoodsCat> cats = new ArrayList<GoodsCat>(catIds.size());
			for (String id : catIds) {
				cats.add(goodsCatalogService.getGoodsCatById(id));
			}
			goodsCats.add(cats);

		}

		mv.addObject("goodsCats", goodsCats);
		return mv;
	}
	
	public ModelAndView goods_to_db(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("admin/adminGoods/adminGoodsList");
		
		
		return mv;
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Member> getMembers(List<GoodsBaseInfo> list) {
		Set<String> memberIds = new HashSet<String>();
		for (GoodsBaseInfo goods : list) {
			memberIds.add(goods.getMemberId());
		}
		return this.memberService.queryMemberMapByIds(memberIds
				.toArray(new String[] {}));
	}

	/**
	 * 新增或修改商品时从form得到参数
	 * 
	 * @param multipartRequest
	 * @return
	 * @throws Exception
	 */
	private GoodsBaseInfo getParam(MultipartHttpServletRequest multipartRequest)
			throws ServiceException {

		RequestValueParse parse = new RequestValueParse(multipartRequest);

		String memberId = parse.getParameter("memberId").getString();
		if (StringUtils.isBlank(memberId)) {
			throw new ServiceException("对不起，会员帐号不能为空");
		}

		GoodsBaseInfo goods = new GoodsBaseInfo();
		goods.setMemberId(memberId);
		goods.setGoodsName(HtmlUtils.parseHtml(parse.getParameter("goodsName")
				.getString()));
		String firstCat = parse.getParameter("firstCat").getString();
		String secondCat = parse.getParameter("secondCat").getString();
		String thirdCat = parse.getParameter("thirdCat").getString();
		if (StringUtils.isEmpty(thirdCat)) {
			goods.setGoodsCat(secondCat);
			goods.setCatPath(firstCat + GoodsCat.PathSeparator + secondCat);
		} else {
			goods.setGoodsCat(thirdCat);
			goods.setCatPath(firstCat + GoodsCat.PathSeparator + secondCat
					+ GoodsCat.PathSeparator + thirdCat);
		}

		double d = parse.getParameter("batchPrice").getDouble();
		goods.setBatchPrice((long) (d * 100));
		double md = parse.getParameter("marketPrice").getDouble();
		goods.setMarketPrice((long) (md * 100));
		goods.setBatchNum(parse.getParameter("batchNum").getLong());
		RequestValue priceDes = parse.getParameter("priceDes");
		if (priceDes.isNotNull()) {
			goods.setPriceDes(HtmlUtils.parseHtml(priceDes.getString()));
		}
		RequestValue goodsNum = parse.getParameter("goodsNum");
		if (goodsNum.isNotNull()) {
			goods.setGoodsNum(goodsNum.getLong());
		}

		goods.setAbandonDays(new Long(parse.getParameter("abandonDays")
				.getLong()));
		goods.getGoodsContent().setContent(
				HtmlUtils.escapeScriptTag(parse.getParameter("content")
						.getString()));

		return goods;
	}

	public void setFormFactory(FormFactory formFactory) {
		this.formFactory = formFactory;
	}

	public void setGoodsCatDAO(GoodsCatDAO goodsCatDAO) {
		this.goodsCatDAO = goodsCatDAO;
	}

	public void setGoodsCatalogService(GoodsCatalogService goodsCatalogService) {
		this.goodsCatalogService = goodsCatalogService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}

	public void setGoodsService(GoodsService goodsService) {
		this.goodsService = goodsService;
	}
	
	public void setUploadService(UploadService uploadService) {
		this.uploadService = uploadService;
	}

	public PictureDAO getPictureDAO() {
	    return pictureDAO;
	}

	public void setPictureDAO(PictureDAO pictureDAO) {
	    this.pictureDAO = pictureDAO;
	}
}

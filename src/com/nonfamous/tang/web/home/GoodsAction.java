package com.nonfamous.tang.web.home;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

import com.nonfamous.commom.form.Field;
import com.nonfamous.commom.form.FieldConfig;
import com.nonfamous.commom.form.Form;
import com.nonfamous.commom.form.FormFactory;
import com.nonfamous.commom.util.HtmlUtils;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.RequestValue;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.tang.dao.query.GoodsQuery;
import com.nonfamous.tang.dao.query.OrderItemQuery;
import com.nonfamous.tang.domain.GoodsBaseInfo;
import com.nonfamous.tang.domain.GoodsCat;
import com.nonfamous.tang.domain.GoodsPropertyType;
import com.nonfamous.tang.domain.Member;
import com.nonfamous.tang.domain.PictureInfo;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.domain.trade.TradeOrderItem;
import com.nonfamous.tang.exception.ServiceException;
import com.nonfamous.tang.service.home.GoodsCatalogService;
import com.nonfamous.tang.service.home.GoodsService;
import com.nonfamous.tang.service.home.MemberService;
import com.nonfamous.tang.service.home.ShopService;
import com.nonfamous.tang.service.home.TradeOrderService;
import com.nonfamous.tang.service.upload.UploadFileException;
import com.nonfamous.tang.service.upload.UploadService;
import com.nonfamous.tang.web.common.Constants;
import com.nonfamous.tang.web.common.SearchTypeConstants;
import com.nonfamous.tang.web.validator.AddGoodsValidator;

/**
 * 
 * @author fred
 * 
 */
public class GoodsAction extends MultiActionController {

	private FormFactory formFactory;

	private GoodsService goodsService;

	private ShopService shopService;

	private MemberService memberService;

	private GoodsCatalogService goodsCatalogService;

	private UploadService uploadService;

	private TradeOrderService tradeOrderService;

	/**
	 * 新增商品初始化
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView add_init(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("home/goods/goodsAdd");
		mv.addObject("goodsCats", goodsCatalogService.getAllGoodsCat());
		mv.addObject("rootCats", goodsCatalogService.getRootGoodsCat());
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

		ModelAndView mv = new ModelAndView("home/goods/goodsAdd");
		AddGoodsValidator validator = new AddGoodsValidator();
		validator.setFactory(formFactory);
		validator.setRequest(request);
		boolean isValid = validator.validate();
		Form form = validator.getForm();
//		Form form = formFactory.getForm("addGoods", request);
//		if (!form.isValide()) {
		if(!isValid){
//			request.setAttribute("form", form);
			request.setAttribute("form", validator.getForm());
			mv.addObject("goodsCats", goodsCatalogService.getAllGoodsCat());
			mv.addObject("rootCats", goodsCatalogService.getRootGoodsCat());
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
			mv.addObject("goodsCats", goodsCatalogService.getAllGoodsCat());
			mv.addObject("rootCats", goodsCatalogService.getRootGoodsCat());
			return mv;
		}
//		MultipartFile file = multipartRequest.getFile("goodsPic");
//		if (file != null && file.getSize() > 0) {
//			try {
//				goods.setGoodsPic(uploadService
//						.uploadGoodsImageWithCompress(file));
//			} catch (UploadFileException e) {
//				request.setAttribute("uploadGoodsError", e.getMessage());
//				request.setAttribute("form", form);
//				mv.addObject("goodsCats", goodsCatalogService.getAllGoodsCat());
//				mv.addObject("rootCats", goodsCatalogService.getRootGoodsCat());
//				return mv;
//			}
//		}
		List<PictureInfo> pictures = new ArrayList<PictureInfo>();
		String path = "";
		for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
		    String key = (String) it.next();
		    MultipartFile file = multipartRequest.getFile(key);
		    if (file != null && file.getSize() > 0 && file.getOriginalFilename().length() > 0) {
			try{
        			path = uploadService.uploadGoodsImageWithCompress(file);
        			PictureInfo pi = new PictureInfo();
        			pi.setPath(path);
        			pictures.add(pi);
        		} catch (UploadFileException e) {
				request.setAttribute("uploadGoodsError", e.getMessage());
				request.setAttribute("form", form);
				mv.addObject("goodsCats", goodsCatalogService.getAllGoodsCat());
				mv.addObject("rootCats", goodsCatalogService.getRootGoodsCat());
				return mv;
        		}
		    }
		}
		if(path.length()>0) goods.setGoodsPic(path);
		goods.setPictures(pictures);

		String memberId = parse.getCookyjar().get(Constants.MemberId_Cookie);
		goods.setMemberId(memberId);
		goods.setCreator(memberId);
		goods.setModifier(memberId);
		goods.setGoodsStatus(GoodsBaseInfo.NORMAL_STATUS);
		Shop shop = shopService.shopSelectByMemberId(memberId);
		if (shop == null) {
			throw new ServiceException("该会员当前没有店铺，添加商品不成功！");
		}
		goods.setShopId(shop.getShopId());
		String colors = ServletRequestUtils.getStringParameter(
				multipartRequest, "colors", "");
		String size = ServletRequestUtils.getStringParameter(
				multipartRequest, "size", "");
		goods.setColors(colors);
		goods.setSize(size);
		// check if the goodsType = 'M'(Mixed:批发+团购)
		String goodsType = ServletRequestUtils.getStringParameter(
				multipartRequest, "goodsType", GoodsBaseInfo.NORMAL_TYPE);
		if(GoodsBaseInfo.NORMAL_TYPE.equals(goodsType)){
			goodsService.addGoods(goods, null);
		}
		else if(GoodsBaseInfo.TEAM_TYPE.equals(goodsType)){
			goods.setGoodsType(GoodsBaseInfo.TEAM_TYPE);
			double groupPrice = ServletRequestUtils
					.getDoubleParameter(multipartRequest, "groupPrice", goods
							.getBatchPrice() / 100);
			long groupNum = ServletRequestUtils.getLongParameter(
					multipartRequest, "groupDefaultNum", 0);
			goods.setBatchPrice((long) groupPrice * 100);
			goods.setBatchNum(groupNum);
			goodsService.addGoods(goods, null);
		}
		else if (GoodsBaseInfo.MIXED_TYPE.equals(goodsType)) { // mixed，批发+团购，需要插入两条记录在数据库里
			GoodsBaseInfo goods2 = (GoodsBaseInfo)SerializationUtils.clone(goods);
			
			// 保存第一条记录：批发
			goods.setGoodsType(GoodsBaseInfo.NORMAL_TYPE);
			goodsService.addGoods(goods, null);
			// 保存第二条记录：团购
			goods2.setGoodsType(GoodsBaseInfo.TEAM_TYPE);
			double groupPrice = ServletRequestUtils
					.getDoubleParameter(multipartRequest, "groupPrice", goods
							.getBatchPrice() / 100);
			long groupNum = ServletRequestUtils.getLongParameter(
					multipartRequest, "groupDefaultNum", goods.getBatchNum());
			goods2.setBatchPrice((long) groupPrice * 100);
			goods2.setBatchNum(groupNum);
			goodsService.addGoods(goods2, null);
		}
		request.setAttribute("success", "商品添加成功");
		form.getField("goodsPic").setValue(goods.getGoodsPic());
		request.setAttribute("form", form);
		request.setAttribute("goodsPics", goods.getPictures());
		mv.addObject("goodsCats", goodsCatalogService.getAllGoodsCat());
		mv.addObject("rootCats", goodsCatalogService.getRootGoodsCat());
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

		ModelAndView mv = new ModelAndView("home/goods/goodsUpdate");
		Form form = formFactory.getForm("addGoods", request);
		//remove the group buy fields validation
		form.getField("groupPrice").setFieldConfigs(new ArrayList<FieldConfig>());
		form.getField("groupDefaultNum").setFieldConfigs(new ArrayList<FieldConfig>());
		if (!form.isValide()) {
			// 得到图片地址并压到模板上
			String goodsId = request.getParameter("goodsId");
			GoodsBaseInfo goods = goodsService.getGoodsBaseInfo(goodsId);
			form.getField("goodsPic").setValue(goods.getGoodsPic());

			request.setAttribute("form", form);
			mv.addObject("goodsCats", goodsCatalogService.getAllGoodsCat());
			mv.addObject("rootCats", goodsCatalogService.getRootGoodsCat());
			mv.addObject("goodsId", request.getParameter("goodsId").trim());
			return mv;
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		RequestValueParse parse = new RequestValueParse(multipartRequest);
		GoodsBaseInfo goods = getParam(multipartRequest);
		GoodsCat gc = this.goodsCatalogService.getGoodsCatById(goods
				.getGoodsCat());
		if (gc == null) {
			form.getField("thirdCat").setMessage("请选择三级分类");
			// 得到图片地址并压到模板上
			String goodsId = request.getParameter("goodsId");
			GoodsBaseInfo gs = goodsService.getGoodsBaseInfo(goodsId);
			form.getField("goodsPic").setValue(gs.getGoodsPic());
			request.setAttribute("form", form);

			mv.addObject("goodsCats", goodsCatalogService.getAllGoodsCat());
			mv.addObject("rootCats", goodsCatalogService.getRootGoodsCat());
			mv.addObject("goodsId", request.getParameter("goodsId").trim());
			return mv;
		}
		String goodsId = parse.getParameter("goodsId").getString();
		goods.setGoodsId(goodsId);
		MultipartFile file = multipartRequest.getFile("goodsPic");
		if (file != null && file.getSize() > 0) {
			try {
				goods.setGoodsPic(uploadService
						.uploadGoodsImageWithCompress(file));
			} catch (UploadFileException e) {
				// 得到图片地址并压到模板上
				GoodsBaseInfo gs = goodsService.getGoodsBaseInfo(goodsId);
				form.getField("goodsPic").setValue(gs.getGoodsPic());

				request.setAttribute("uploadGoodsError", e.getMessage());
				request.setAttribute("form", form);
				mv.addObject("goodsCats", goodsCatalogService.getAllGoodsCat());
				mv.addObject("rootCats", goodsCatalogService.getRootGoodsCat());
				mv.addObject("goodsId", request.getParameter("goodsId").trim());
				return mv;
			}
		}
		GoodsBaseInfo gbi = goodsService.getGoodsBaseInfo(parse.getParameter(
				"goodsId").getString());
		String memberId = parse.getCookyjar().get(Constants.MemberId_Cookie);
		if (!memberId.equals(gbi.getMemberId())) {
			throw new ServiceException("对不起，您不能修改其他会员的商品");
		}
		Shop shop = shopService.shopSelectByMemberId(memberId);
		if (shop == null) {
			throw new ServiceException("该会员当前没有店铺，不允许修改商品");
		}
		goods.setShopId(shop.getShopId());
		goods.setMemberId(memberId);
		goods.setModifier(memberId);
		goods.setGoodsStatus(GoodsBaseInfo.NORMAL_STATUS);

		GoodsBaseInfo ori = goodsService.getGoodsBaseInfo(goodsId);
		if (!ori.getAbandonDays().equals(goods.getAbandonDays())) {
			Calendar c = Calendar.getInstance();
			c.setTime(ori.getGmtCreate());
			c.add(Calendar.DATE, goods.getAbandonDays().intValue());
			goods.setGmtAbandon(c.getTime());
		}
		goodsService.updateGoods(goods, null);
		request.setAttribute("success", "商品修改成功");

		// 修改成功后返回信息
		GoodsBaseInfo gs = goodsService.getGoodsBaseInfo(goodsId);
		form.getField("goodsPic").setValue(gs.getGoodsPic());
		form.getField("batchPrice")
				.setValue(goods.getGoodsBatchPriceInFormat());
		form.getField("marketPrice").setValue(
				goods.getGoodsMarketPriceInFormat());

		request.setAttribute("form", form);
		mv.addObject("goodsType", goods.getGoodsType());
		mv.addObject("goodsCats", goodsCatalogService.getAllGoodsCat());
		mv.addObject("rootCats", goodsCatalogService.getRootGoodsCat());
		mv.addObject("goodsId", request.getParameter("goodsId").trim());

		return mv;
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
		ModelAndView mv = new ModelAndView("home/goods/goodsDown");
		RequestValueParse parse = new RequestValueParse(request);
		String memberId = parse.getCookyjar().get(Constants.MemberId_Cookie);
		String goodsId = request.getParameter("goodsId");

		GoodsBaseInfo goods = goodsService.getGoodsBaseInfo(goodsId);
		if (!memberId.equals(goods.getCreator())) {
			throw new ServiceException("对不起，您不是商品的创建者，不能删除商品");
		}
		goodsService.deleteGoods(goodsId);

		GoodsQuery query = new GoodsQuery();
		query.setMemberId(parse.getCookyjar().get(Constants.MemberId_Cookie));
		//query.setCurrentPageString(parse.getParameter("current").getString());
		GoodsQuery downList = goodsService.getDownGoods(query);
		request.setAttribute("query", downList);
		if (downList.getTotalItem() == 0) {
			return mv;
		}

		mv.addObject("downList", downList.getGoods());
		return mv;
	}

	/**
	 * 上架商品初始化列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list_init(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("home/goods/goodsList");
		RequestValueParse parse = new RequestValueParse(request);
		GoodsQuery query = new GoodsQuery();
		query.setMemberId(parse.getCookyjar().get(Constants.MemberId_Cookie));
		query.setCurrentPageString(parse.getParameter("current").getString());

		GoodsQuery upList = goodsService.getActiveGoods(query);
		request.setAttribute("query", upList);
		if (upList.getTotalItem() == 0) {
			return mv;
		}
		request.setAttribute("my_name", "list_init");
		mv.addObject("upList", upList.getGoods());

		return mv;
	}

	/**
	 * 下架商品初始化列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView down_init(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse parse = new RequestValueParse(request);
		GoodsQuery query = new GoodsQuery();
		query.setMemberId(parse.getCookyjar().get(Constants.MemberId_Cookie));
		query.setCurrentPageString(parse.getParameter("current").getString());
		ModelAndView mv = new ModelAndView("home/goods/goodsDown");

		GoodsQuery downList = goodsService.getDownGoods(query);
		request.setAttribute("query", downList);
		if (downList.getTotalItem() == 0) {
			return mv;
		}
		mv.addObject("downList", downList.getGoods());
		request.setAttribute("my_name", "down_init");

		return mv;
	}

	/**
	 * 下架商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView down(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("home/goods/goodsDown");
		RequestValueParse parse = new RequestValueParse(request);
		String goodsId = request.getParameter("goodsId");
		GoodsBaseInfo goods = goodsService.getGoodsBaseInfo(goodsId);
		String memberId = parse.getCookyjar().get(Constants.MemberId_Cookie);
		if (!memberId.equals(goods.getMemberId())) {
			throw new ServiceException("对不起，您不能下架其他会员的商品");
		}
		GoodsBaseInfo gbi = new GoodsBaseInfo();
		gbi.setGoodsId(goodsId);
		gbi.setGoodsStatus(GoodsBaseInfo.DOWN_STATUS);
		gbi.setModifier(memberId);
		goodsService.downFromShelves(gbi);
		GoodsQuery query = new GoodsQuery();
		query.setMemberId(memberId);
		GoodsQuery downList = goodsService.getDownGoods(query);
		request.setAttribute("query", downList);
		if (downList.getTotalItem() == 0) {
			return mv;
		}
		mv.addObject("downList", downList.getGoods());
		request.setAttribute("my_name", "down_init");

		return mv;
	}

	/**
	 * 上架商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView up(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("home/goods/goodsList");
		RequestValueParse parse = new RequestValueParse(request);
		String goodsId = request.getParameter("goodsId");
		GoodsBaseInfo goods = goodsService.getGoodsBaseInfo(goodsId);
		String memberId = parse.getCookyjar().get(Constants.MemberId_Cookie);
		if (!memberId.equals(goods.getMemberId())) {
			throw new ServiceException("对不起，您不能上架其他会员的商品");
		}
		GoodsBaseInfo gbi = new GoodsBaseInfo();
		gbi.setGoodsId(goodsId);
		gbi.setAbandonDays(goods.getAbandonDays());
		gbi.setGoodsStatus(GoodsBaseInfo.NORMAL_STATUS);
		gbi.setModifier(memberId);
		goodsService.add2Shelves(gbi);
		GoodsQuery query = new GoodsQuery();
		query.setMemberId(memberId);
		GoodsQuery upList = goodsService.getActiveGoods(query);
		request.setAttribute("query", upList);
		if (upList.getTotalItem() == 0) {
			return mv;
		}
		mv.addObject("upList", upList.getGoods());
		request.setAttribute("my_name", "list_init");

		return mv;
	}

	/**
	 * 修改商品时的初始化
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView update_init(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("home/goods/goodsUpdate");
		String goodsId = request.getParameter("goodsId");
		GoodsBaseInfo goods = goodsService.getGoodsWholeInfo(goodsId);

		Form form = formFactory.getForm("addGoods", request);
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
		form.getField("marketPrice").setValue(
				goods.getGoodsMarketPriceInFormat());
		form.getField("batchNum").setValue(Long.toString(goods.getBatchNum()));
		form.getField("priceDes").setValue(goods.getPriceDes());
		form.getField("goodsNum").setValue(Long.toString(goods.getGoodsNum()));
		form.getField("goodsPic").setValue(goods.getGoodsPic());
		form.getField("abandonDays").setValue(
				Long.toString(goods.getAbandonDays()));
		form.getField("content").setValue(goods.getGoodsContent().getContent());
		mv.addObject("form", form);
		mv.addObject("goodsType", goods.getGoodsType());
		mv.addObject("goodsId", goodsId);
		mv.addObject("goodsCats", goodsCatalogService.getAllGoodsCat());
		mv.addObject("rootCats", goodsCatalogService.getRootGoodsCat());
		return mv;
	}

	/**
	 * 商品详情页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse parse = new RequestValueParse(request);
		String goodsId = parse.getParameterOrAttribute("id").getString();
		// String memberId = parse.getCookyjar().get(Constants.MemberId_Cookie);
		if (StringUtils.isBlank(goodsId)) {
			throw new Exception("goodsId is null");
		}

		GoodsBaseInfo goods = goodsService.getGoodsWholeInfo(goodsId);
		if (goods == null) {
			request.setAttribute("errorInfo", "您要找的商品信息不存在，或已经删除");
			throw new ServiceException("您要找的商品信息不存在，或已经删除");
		}

		String goodsPrice = goods.getGoodsBatchPriceInFormat();
		String marketPrice = goods.getGoodsMarketPriceInFormat();
		ModelAndView mv = new ModelAndView("home/goods/goodsDetail");
		mv.addObject("goodsPrice", goodsPrice);
		mv.addObject("marketPrice", marketPrice);
		long remainTime = goods.getGmtAbandon().getTime()
				- new Date().getTime();
		if (remainTime < 0) {
			mv.addObject("remainDay", 0);
			mv.addObject("remainHour", 0);
			mv.addObject("remainMin", 0);
			mv.addObject("remainSec", 0);
		} else {
			remainTime /= 1000L;
			long remainDay = remainTime / (3600 * 24);
			long remainHour = (remainTime / 3600) % 24;
			long remainMin = (remainTime / 60) % 60;
			long remainSec = remainTime % 60;
			mv.addObject("remainDay", remainDay);
			mv.addObject("remainHour", remainHour);
			mv.addObject("remainMin", remainMin);
			mv.addObject("remainSec", remainSec);
		}
		mv.addObject("goods", goods);
		// 处理团购的商品
		if (goods.getGoodsType() != null && goods.getGoodsType().equals("T")) {
			OrderItemQuery query = new OrderItemQuery();
			query.setGoodsId(goodsId);
			query.setCurrentPageString(parse.getParameter("current")
					.getString());
			query = tradeOrderService.findOrderItems(query);
			request.setAttribute("query", query);
		}
		Member member = memberService.getMemberById(goods.getMemberId());
		Shop shop = shopService.shopSelectByMemberId(member.getMemberId());
		mv.addObject("member", member);
		mv.addObject("shop", shop);

		// 处理分类导航
		List<String> catIds = goods.getCatalogIds();
		List<GoodsCat> cats = new ArrayList<GoodsCat>(catIds.size());
		for (String id : catIds) {
			cats.add(goodsCatalogService.getGoodsCatById(id));
		}
		mv.addObject("cats", cats);
		// 浏览量加一
		this.goodsService.addViewCount(goodsId);
		request.setAttribute("searchType", SearchTypeConstants.Search_Goods);
		// 页面标题
		request.setAttribute(Constants.PageTitle_Page, goods.getGoodsName());
		return mv;
	}

	/**
	 * 预览商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView preview(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse parse = new RequestValueParse(request);
		String goodsId = parse.getParameterOrAttribute("id").getString();
		if (StringUtils.isBlank(goodsId)) {
			throw new Exception("goodsId is null");
		}
		// 处理批量价显示,很多要修改,先放着
		GoodsBaseInfo goods = goodsService.getGoodsWholeInfo(goodsId);
		if (goods == null) {
			throw new ServiceException("您预览的商品不存在，或已经删除");
		}

		String goodsPrice = goods.getGoodsBatchPriceInFormat();
		String marketPrice = goods.getGoodsMarketPriceInFormat();

		ModelAndView mv = new ModelAndView("home/goods/preview");
		mv.addObject("goodsPrice", goodsPrice);
		mv.addObject("marketPrice", marketPrice);
		long remainTime = goods.getGmtAbandon().getTime()
				- new Date().getTime();
		if (remainTime < 0) {
			mv.addObject("remainDay", 0);
			mv.addObject("remainHour", 0);
			mv.addObject("remainMin", 0);
			mv.addObject("remainSec", 0);
		} else {
			remainTime /= 1000L;
			long remainDay = remainTime / (3600 * 24);
			long remainHour = (remainTime / 3600) % 24;
			long remainMin = (remainTime / 60) % 60;
			long remainSec = remainTime % 60;
			mv.addObject("remainDay", remainDay);
			mv.addObject("remainHour", remainHour);
			mv.addObject("remainMin", remainMin);
			mv.addObject("remainSec", remainSec);
		}
		mv.addObject("goods", goods);

		// 处理分类导航
		List<String> catIds = goods.getCatalogIds();
		List<GoodsCat> cats = new ArrayList<GoodsCat>(catIds.size());
		for (String id : catIds) {
			cats.add(goodsCatalogService.getGoodsCatById(id));
		}
		mv.addObject("cats", cats);
		// 浏览量加一
		this.goodsService.addViewCount(goodsId);
		return mv;
	}

	/**
	 * 从前台取得form的参数
	 * 
	 * @param multipartRequest
	 * @return
	 */
	private GoodsBaseInfo getParam(MultipartHttpServletRequest multipartRequest) {

		RequestValueParse parse = new RequestValueParse(multipartRequest);

		GoodsBaseInfo goods = new GoodsBaseInfo();
		goods.setGoodsName(HtmlUtils.parseHtml(parse.getParameter("goodsName")
				.getString()));
		String firstCat = parse.getParameter("firstCat").getString();
		String secondCat = parse.getParameter("secondCat").getString();
		String thirdCat = parse.getParameter("thirdCat").getString();
		String goodsType = parse.getParameter("goodsType").getString();
		String ageRange = parse.getParameter("ageRange").getString();
		String brand = parse.getParameter("brand").getString();
		if(StringUtils.isNotBlank(brand))
			goods.getPropertiesMap().put("brand",brand);
		if(StringUtils.isNotBlank(ageRange))
			goods.getPropertiesMap().put("ageRange", ageRange);
		goods.setGoodsType(goodsType);
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
		} else {
			goods.setGoodsNum(new Long(0));
		}

		goods.setAbandonDays(new Long(parse.getParameter("abandonDays")
				.getLong()));
		goods.getGoodsContent().setContent(
				HtmlUtils.escapeScriptTag(parse.getParameter("content")
						.getString()));

		return goods;
	}

	public void setGoodsService(GoodsService goodsService) {
		this.goodsService = goodsService;
	}

	public void setFormFactory(FormFactory formFactory) {
		this.formFactory = formFactory;
	}

	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}

	public void setGoodsCatalogService(GoodsCatalogService goodsCatalogService) {
		this.goodsCatalogService = goodsCatalogService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setUploadService(UploadService uploadService) {
		this.uploadService = uploadService;
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	
	/**
	 * 通过属性ID或者属性名称查找改属性类型下的所有可选值
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView load_propertys(HttpServletRequest request,
			HttpServletResponse response){
		ModelAndView mv = new ModelAndView("home/goods/goodsProperty");
		String[] propertyTypeIds = request.getParameterValues("propertyId");
		boolean isValid = false;
		ArrayList<Integer>pIds=new ArrayList<Integer>();
		String columnLength = request.getParameter("columnLength");
		if(StringUtils.isBlank(columnLength)){
			columnLength="4";
		}
		String onClickMethodName = request.getParameter("onclick");
		if(propertyTypeIds!=null&&propertyTypeIds.length>0){
			int pId = -1;
			for(int i=0;i<propertyTypeIds.length;i++)
			{
				try{
					pId = Integer.parseInt(propertyTypeIds[i]);
					pIds.add(pId);
					isValid=true;
				}
				catch(Exception e){
					isValid=false;
					break;
				}
			}
		}
		if(!isValid){
			request.setAttribute("Errors", "parameter is not valid.");
			return mv;
		}
		List<GoodsPropertyType> propertyTypeList = null;
		HashMap parameterMap=new HashMap();
		parameterMap.put("idList", pIds);
		propertyTypeList=this.goodsService.getGoodsPropertyTypeList(parameterMap);
		request.setAttribute("onClickMethodName", onClickMethodName);
		request.setAttribute("columnLength", columnLength);
		request.setAttribute("propertyTypeList", propertyTypeList);
		return mv;
	}
}

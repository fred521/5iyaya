package com.nonfamous.tang.web.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;

import com.nonfamous.commom.form.DefaultFormFactory;
import com.nonfamous.commom.form.Form;
import com.nonfamous.commom.util.AddressHelper;
import com.nonfamous.commom.util.AddressInfo;
import com.nonfamous.commom.util.PriceUtils;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.alipay.CheckURL;
import com.nonfamous.commom.util.alipay.Payment;
import com.nonfamous.commom.util.alipay.SignatureHelper;
import com.nonfamous.commom.util.alipay.SignatureHelper_return;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.tang.dao.query.OrderQuery;
import com.nonfamous.tang.domain.Address;
import com.nonfamous.tang.domain.GoodsBaseInfo;
import com.nonfamous.tang.domain.Member;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.domain.mail.MailInfo;
import com.nonfamous.tang.domain.result.PayResult;
import com.nonfamous.tang.domain.trade.TradeOrder;
import com.nonfamous.tang.domain.trade.TradeOrderItem;
import com.nonfamous.tang.domain.trade.TradeOrderLog;
import com.nonfamous.tang.domain.trade.TradeOrderNote;
import com.nonfamous.tang.service.home.AddressService;
import com.nonfamous.tang.service.home.GoodsService;
import com.nonfamous.tang.service.home.MemberService;
import com.nonfamous.tang.service.home.ShopService;
import com.nonfamous.tang.service.home.TradeOrderService;
import com.nonfamous.tang.service.home.TradePayService;
import com.nonfamous.tang.service.mail.IMailEngine;
import com.nonfamous.tang.web.common.Constants;
import com.nonfamous.tang.web.common.netpay.NetPayConstants;

/**
 * 
 * @author fish
 * 
 */
public class OrderAction extends MultiActionController {

	private TradeOrderService tradeOrderService;

	private MemberService memberService;

	private ShopService shopService;

	private GoodsService goodsService;

	private TradePayService tradePayService;
	
	private AddressService addressService;

	private AddressHelper addressHelper;
	
	private IMailEngine mailEngine;
	
	private DefaultFormFactory formFactory;
	
	public void setTradePayService(TradePayService tradePayService) {
		this.tradePayService = tradePayService;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public TradeOrderService getTradeOrderService() {
		return tradeOrderService;
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}

	public void setGoodsService(GoodsService goodsService) {
		this.goodsService = goodsService;
	}

	/**
	 * orderId 安全检查
	 * 
	 * @param orderId
	 * @param memberId
	 * @return TradeOrder
	 */
	private TradeOrder orderIdCheck(HttpServletRequest request, String orderId,
			String memberId) {
		if (memberId == null) {
			request.setAttribute("error_message", "抱歉，没有找到该订单数据");
			throw new IllegalArgumentException("member id is null");
		}
		TradeOrder order = this.tradeOrderService.getTradeOrder(orderId);
		// 安全检查
		if (!order.isBuyerOrSeller(memberId)) {
			request.setAttribute("error_message", "抱歉，没有找到该订单数据");
			throw new IllegalArgumentException("invalid order id");
		}
		return order;
	}

	/**
	 * 显示会员所有的交易订单列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/home/trade/orderList");

		RequestValueParse rvp = new RequestValueParse(request);
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		request.setAttribute("memberId", memberId);

		OrderQuery query = new OrderQuery();
		query.setMemberId(memberId);

		String page = rvp.getParameter("page").getString();
		query.setCurrentPageString(page);
		int status = 0;
		try {
			status = rvp.getParameter("status").getInt();
			if (status > 5 || status < 0) {
				mv.addObject("errorMessage", "查询状态错误");
				return mv;
			}
		} catch (NumberFormatException e) {
			mv.addObject("errorMessage", "查询状态错误");
			return mv;
		}
		query.setSearchStatus(status);
		query.setType(TradeOrder.TypeTrade);
		// query.setPageSize(5);
		query = this.tradeOrderService.findOrders(query);
		request.setAttribute("query", query);

		return mv;
	}

	/**
	 * 显示一个订单的详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*RequestValueParse rvp = new RequestValueParse(request);
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		request.setAttribute("memberId", memberId);

		String orderId = rvp.getParameter("id").getString();
		if (orderId == null) {
			throw new NullPointerException("id can't be null");
		}

		TradeOrder order = this.orderIdCheck(request, orderId, memberId);
		String sellerId=order.getSellerId();
		String buyerId=order.getBuyerId();
		Member seller=memberService.getMemberById(sellerId);
		Member buyer=memberService.getMemberById(buyerId);
		request.setAttribute("order", order);
		request.setAttribute("seller", seller);
		request.setAttribute("buyer", buyer);
		
		
		//populate address information
		List<Address> addressList = new ArrayList<Address>();
		if(order.getAddressId()==null){
			List<Address> addrList = addressService.getAddressListByMemberId(memberId);
			for(Address address : addrList){
				AddressInfo addressInfo1 = addressHelper.findAddressByAreaCode(address.getAreaCode());
				AddressInfo addressInfo2 = addressHelper.findAddressByAreaCode(addressInfo1.getParentAreaCode());
				AddressInfo addressInfo3 = addressHelper.findAddressByAreaCode(addressInfo2.getParentAreaCode());
				address.setAreaCodeStr(addressInfo3.getName() + " " + addressInfo2.getName() + " " + addressInfo1.getName());
			
				addressList.add(address);
			}
		}
		else{
			Address addr = addressService.getAddressById(order.getAddressId());
			AddressInfo addressInfo1 = addressHelper.findAddressByAreaCode(addr.getAreaCode());
			AddressInfo addressInfo2 = addressHelper.findAddressByAreaCode(addressInfo1.getParentAreaCode());
			AddressInfo addressInfo3 = addressHelper.findAddressByAreaCode(addressInfo2.getParentAreaCode());
			addr.setAreaCodeStr(addressInfo3.getName() + " " + addressInfo2.getName() + " " + addressInfo1.getName());

			addressList.add(addr);
		}
		
		//Member member = this.memberService.getMemberById(memberId);
		//request.setAttribute("member", member);

		List<TradeOrderNote> notes = this.tradeOrderService
				.findOrderNotesByOrderId(orderId);
		request.setAttribute("notes", notes);

		List<TradeOrderItem> items = this.tradeOrderService
				.findOrderItems(orderId);
		request.setAttribute("items", items);
		
		Long totalFee = getTotalFree(items);
		request.setAttribute("totalFee",  totalFee);

		List<TradeOrderLog> logs = this.tradeOrderService
				.findOrderLogByOrderNo(orderId);
		request.setAttribute("logs", logs);
		
		ModelAndView mv = new ModelAndView("/home/trade/orderDetail");
		mv.addObject("addressList", addressList);
		return mv;*/
		ModelAndView mv = new ModelAndView("/home/trade/orderDetail");
		return listOrderDetail(request, response, mv);
	}
	
	private Long getTotalFree(List<TradeOrderItem> items) {
	    Long totalFee = 0L;
	    for(TradeOrderItem item : items) {
		totalFee += item.getTotalFree();
	    }
	    return totalFee;
	}

	public ModelAndView update(HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
		ModelAndView mv = new ModelAndView("/home/trade/orderUpdate");
		return listOrderDetail(request, response, mv);
	}

	/**
	 * 生成订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		String loginId = rvp.getCookyjar().get(Constants.MemberLoinName_Cookie);
		String shopId = rvp.getParameter("shopId").getString();
		String goodsIds[] = request.getParameterValues("sgoods");
		if (StringUtils.isBlank(shopId) || goodsIds == null
				|| goodsIds.length == 0) {
			ModelAndView mv = new ModelAndView("forward:../tradecar/select.htm");
			request.setAttribute("addMessage", "生成订单失败，请选择商品");
			return mv;
		}
		
		List<GoodsBaseInfo> goods = goodsService.findGoodsBaseInfos(goodsIds);
		for(GoodsBaseInfo goodBaseInfo : goods){
			if (goodBaseInfo.getBatchPrice()==0){
				request.setAttribute("addMessage", "您选择的商品中有部分商品价格未确定，请联系客服再下单");
				ModelAndView mv = new ModelAndView("forward:../tradecar/select.htm");
				return mv;
			}
			goodBaseInfo.getBatchPrice();
		}
		TradeOrder order = this.getNewOrder(shopId, memberId, loginId);
		List<TradeOrderItem> items = this.getTradeItems(goodsIds, memberId);
		if (order == null || items == null || items.size() == 0) {
			ModelAndView mv = new ModelAndView("/home/trade/orderAdd");
			request.setAttribute("addMessage", "生成订单失败");
			return mv;
		}
		this.tradeOrderService.createOrder(order, items);
		
		sendOrderReminderMail(order, items);

		ModelAndView mv = new ModelAndView("redirect:update.htm?id="
				+ order.getOrderNo());
		return mv;
	}

	/**
	 * 确认订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView confirm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		String orderId = rvp.getParameter("id").getString();
		String address = rvp.getParameter("addressGroup").getString();
		
		if (orderId == null) {
			throw new NullPointerException("id can't be null");
		}
		
		if (address == null) {
			throw new NullPointerException("address can't be null");
		}
		
		//如果一个订单被确认，必须要有address的Id
		this.tradeOrderService.confirmOrder(orderId, memberId, address);
		ModelAndView mv = new ModelAndView("redirect:detail.htm?id=" + orderId);
		
		TradeOrder order = this.tradeOrderService.getTradeOrder(orderId);
		
		if(memberId.equals(order.getSellerId())){
			Member buyer = this.memberService.getMemberById(order.getBuyerId());
			sendOrderConfirmMail(buyer.getLoginId(), orderId, buyer.getEmail());
		}
		return mv;
	}

	/**
	 * 发货
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView ship(HttpServletRequest request, HttpServletResponse response) throws Exception{
		RequestValueParse rvp = new RequestValueParse(request);
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		String orderId = rvp.getParameter("id").getString();
		if (orderId == null) {
			throw new NullPointerException("id can't be null");
		}
		this.tradeOrderService.updateOrderSetConfirm(orderId, memberId);
		ModelAndView mv = new ModelAndView("redirect:detail.htm?id=" + orderId);
		return mv;
	}
	
	/**
	 * 订单做废
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView close(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		String orderId = rvp.getParameter("id").getString();
		if (orderId == null) {
			throw new NullPointerException("id can't be null");
		}
		this.tradeOrderService.updateOrderSetClosed(orderId, memberId);
		ModelAndView mv = new ModelAndView("redirect:detail.htm?id=" + orderId);
		
		TradeOrder order = this.tradeOrderService.getTradeOrder(orderId);
		
		if(memberId.equals(order.getSellerId())){
			Member buyer = this.memberService.getMemberById(order.getBuyerId());
			sendOrderCancelMail(buyer.getLoginId(), orderId, buyer.getEmail(), order.getShopId());
		}
		return mv;
	}

	/**
	 * 删除一个商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView removeGoods(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		String orderId = rvp.getParameter("id").getString();
		if (orderId == null) {
			throw new NullPointerException("id can't be null");
		}
		Long itemId = rvp.getParameter("item").getLong();
		if (itemId == null) {
			throw new NullPointerException("itemId can't be null");
		}

		this.tradeOrderService.updateOrderRemoveItem(orderId, itemId, memberId);
		ModelAndView mv = new ModelAndView("redirect:update.htm?id=" + orderId);
		return mv;
	}

	public ModelAndView modify(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		String orderId = rvp.getParameter("id").getString();
		String addressId = rvp.getParameter("addressGroup").getString();
		ModelAndView mv = new ModelAndView("redirect:detail.htm?id=" + orderId);
		
		TradeOrder order = orderIdCheck(request, orderId, memberId);
		
		//如果order里已经有地址信息，那么这个order里的地址就不会在被修改。否则会选择一个已经存着的或者添加一个新地址
		if(!StringUtils.isNotEmpty(order.getAddress())){
			String detailAddress = null;
			Address address = null;
			//如果addressId为New或者页面里返回的addressId为null，说明修改订单的同时，用户在添加新的邮寄地址
			//此处的New是页面orderUpdate里addressGroup元素的value。在页面里点击添加新地址的radio button时，该element就会被设为New
			if("New".equals(addressId)||addressId ==null){
				ModelAndView mv2 = new ModelAndView("/home/trade/orderUpdate");
				Form form = formFactory.getForm("addressInOperateForm", request);
				if(!form.isValide()){
						prepareAddresses(mv2, memberId);
						mv2.addObject("form", form);
						mv2.addObject("errorMessage", "添加地址失败!");
						return listOrderDetail(request, response,mv2);
					}
					address = new Address();
					bind(request, address);
					//在此处组装Address对象
					address.setMemberId(memberId);
					address.setStatus(Address.TEMP_STATUS);
					address.setAreaCode(rvp.getParameter("areaCode").getString());
					addressService.addAddress(address);
					
			}
			else{
				address = addressService.getAddressById(addressId);
			}
			
			setAreaCodeStreetForAddress(address);
			detailAddress = address.convertAddressToOrderFormat();
			order.setAddress(detailAddress);
		}

		if (orderId == null) {
			throw new NullPointerException("id can't be null");
		}
		
		String[] totalNums = request.getParameterValues("totalNum");
		String[] batchPrices = request.getParameterValues("batchPrice");
		String[] totalFrees = request.getParameterValues("totalFree");
		
		Long logisticsFee = null;
		String logisticsFeeStr = request.getParameter("logisticsFee");
		logisticsFeeStr = logisticsFeeStr==null?"":logisticsFeeStr.trim();
		if(!logisticsFeeStr.equals("")){
			logisticsFee = PriceUtils.yuanToFen(logisticsFeeStr);
		}
		if (totalNums == null || totalNums.length == 0 || batchPrices == null
				|| batchPrices.length == 0 || totalFrees == null
				|| totalFrees.length == 0) {
			throw new IllegalArgumentException("提交参数不正确,商品不能为空");
		}
		List<TradeOrderItem> items = this.tradeOrderService
				.findOrderItems(orderId);
		int len = items.size();
		if (totalNums.length != len || totalFrees.length != len
				|| batchPrices.length != len) {
			throw new IllegalArgumentException("提交参数不正确,缺少商品数据");
		}

		List<TradeOrderItem> changedItems = new ArrayList<TradeOrderItem>();
		StringBuilder log = new StringBuilder();
		long payFee = 0;
		for (int i = 0; i < totalNums.length; i++) {
			TradeOrderItem item = items.get(i);
			long itemFree = PriceUtils.yuanToFen(totalFrees[i]);
			String change = item.getLog(Long.parseLong(totalNums[i]),
					PriceUtils.yuanToFen(batchPrices[i]), itemFree);
			if (change != null) {
				log.append(change);
				changedItems.add(item);
			}
			item.setModifier(memberId);
			payFee += itemFree;
		}
	
		if(logisticsFee!=null){
			payFee += logisticsFee;
			String orderLog = order.getLog(logisticsFee);
			if(orderLog!=null){
				log.append(orderLog);
			}
		}
		
		order.setPayFee(payFee);
		order.setModifier(memberId);
		
		String note = rvp.getParameter("note").getString();
		
		this.tradeOrderService.updateOrderSetItems(order, memberId,
				changedItems, log.toString(), note);

		return mv;
	}

	public ModelAndView note(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		String orderId = rvp.getParameter("id").getString();
		if (orderId == null) {
			throw new NullPointerException("id can't be null");
		}
		TradeOrder order = orderIdCheck(request, orderId, memberId);
		String note = rvp.getParameter("note").getString();
		if (StringUtils.isNotBlank(note)) {
			this.tradeOrderService.createOrderNote(order, memberId, note);
		}
		ModelAndView mv = new ModelAndView("redirect:detail.htm?id=" + orderId);
		return mv;
	}

	/**
	 * 订单支付
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView pay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		String orderId = rvp.getParameter("id").getString();
		if (orderId == null) {
			throw new NullPointerException("id can't be null");
		}
		PayResult result = tradePayService.orderPay(orderId, memberId);
		if (result.isSuccess()) {
			ModelAndView mv = new ModelAndView("redirect:/pay/select_bank.htm");
			mv.addObject("orderid",orderId);
			return mv;
		} else {
			request.setAttribute("errorMessage", result.getErrorMessage());
			return detail(request, response);
		}
	}
	private TradeOrder getNewOrder(String shopId, String creator, String loginId) {

		Shop shop = this.shopService.shopSelectByShopId(shopId);
		if (shop == null) {
			return null;
		}
		TradeOrder order = new TradeOrder();
		order.setShopId(shopId);
		order.setShopName(shop.getShopName());
		order.setSellerId(shop.getMemberId());
		order.setSellerLoginId(shop.getLoginId());
		order.setBuyerLoginId(loginId);
		order.setBuyerId(creator);
		order.setPayFee(0L);
		// ============================初始属性============================
		order.setStatus(TradeOrder.StatusInit);
		order.setCreator(creator);
		order.setModifier(creator);
		order.setOrderType(TradeOrder.TypeTrade);
		return order;
	}

	private List<TradeOrderItem> getTradeItems(String[] goodsIds, String creator) {
		if (goodsIds == null || goodsIds.length == 0) {
			return null;
		}
		List<GoodsBaseInfo> goods = this.goodsService
				.findGoodsBaseInfos(goodsIds);
		List<TradeOrderItem> items = new ArrayList<TradeOrderItem>(goods.size());
		for (GoodsBaseInfo good : goods) {
			TradeOrderItem item = new TradeOrderItem();
			item.setCreator(creator);
			item.setModifier(creator);
			item.setGoodsId(good.getGoodsId());
			item.setGoodsName(good.getGoodsName());
			item.setShopId(good.getShopId());
			item.setBatchPrice(good.getBatchPrice());
			items.add(item);
		}
		return items;
	}
	
    private void sendOrderReminderMail(TradeOrder order, List<TradeOrderItem> items){
		String subject = "爱丫恭喜您：有新的订单。";
		MailInfo mailInfo = new MailInfo();
		mailInfo.setTo(Constants.Order_Reminder_List);
		mailInfo.setSubject(subject);
		
		Member buyer = this.memberService.getMemberById(order.getBuyerId());
		Member seller = this.memberService.getMemberById(order.getSellerId());
		
		Map model = new HashMap();
		model.put("buyerId", buyer.getLoginId());
		model.put("buyerEmail", buyer.getEmail());
		model.put("buyerMobile", buyer.getMobile());
		
		model.put("sellerId", seller.getLoginId());
		model.put("shopName", order.getShopName());
		model.put("sellerMobile", seller.getMobile());
		
		model.put("orderId", order.getOrderNo());
		model.put("items",items);
		
		mailEngine.sendVelocityMessage(mailInfo,
				"conf/love/mail/templates/orderReminder.vm", model);
    }
    
	private ModelAndView listOrderDetail(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		request.setAttribute("memberId", memberId);

		String orderId = rvp.getParameter("id").getString();
		if (orderId == null) {
			throw new NullPointerException("id can't be null");
		}

		TradeOrder order = this.orderIdCheck(request, orderId, memberId);
		String sellerId=order.getSellerId();
		String buyerId=order.getBuyerId();
		Member seller=memberService.getMemberById(sellerId);
		Member buyer=memberService.getMemberById(buyerId);
		request.setAttribute("order", order);
		request.setAttribute("seller", seller);
		request.setAttribute("buyer", buyer);
		
		
		//populate address information
		List<Address> addressList = new ArrayList<Address>();
		if(order.getAddress()==null){
			List<Address> addrList = addressService.getAddressListByMemberId(memberId);
			for(Address address : addrList){
				setAreaCodeStreetForAddress(address);
				addressList.add(address);
			}
		}
		else {
			String selectedAddr = AddressHelper.convertAddressFormatBackToNormal(order.getAddress());
			mv.addObject("selectedAddr", selectedAddr);
		}

		List<TradeOrderNote> notes = this.tradeOrderService
				.findOrderNotesByOrderId(orderId);
		request.setAttribute("notes", notes);

		List<TradeOrderItem> items = this.tradeOrderService
				.findOrderItems(orderId);
		request.setAttribute("items", items);
		
		Long totalFee = getTotalFree(items);
		request.setAttribute("totalFee",  totalFee);

		List<TradeOrderLog> logs = this.tradeOrderService
				.findOrderLogByOrderNo(orderId);
		request.setAttribute("logs", logs);

		mv.addObject("addressList", addressList);
		return mv;
	}
	
	private void prepareAddresses(ModelAndView mv, String memberId) {
		List<Address> addressList = addressService.getAddressListByMemberId(memberId);
		for(Address address : addressList){
			setAreaCodeStreetForAddress(address);
		}
		mv.addObject("addressList", addressList);
	}
	
	private void setAreaCodeStreetForAddress(Address address){
		AddressInfo addressInfo1 = addressHelper.findAddressByAreaCode(address.getAreaCode());
		AddressInfo addressInfo2 = addressHelper.findAddressByAreaCode(addressInfo1.getParentAreaCode());
		AddressInfo addressInfo3 = addressHelper.findAddressByAreaCode(addressInfo2.getParentAreaCode());
		address.setAreaCodeStr(addressInfo3.getName() + " " + addressInfo2.getName() + " " + addressInfo1.getName());
	}
	
    private void sendOrderConfirmMail(String userName, String orderNumber, String mail){
		String subject = "您的订单已被确认";
		MailInfo mailInfo = new MailInfo();
		mailInfo.setTo(new String[]{mail});
		mailInfo.setSubject(subject);
		
		Map model = new HashMap();
		model.put("username", userName);
		model.put("orderNumber", orderNumber);
		
		mailEngine.sendVelocityMessage(mailInfo,
				"conf/love/mail/templates/orderConfirm.vm", model);
    }
	
    private void sendOrderCancelMail(String userName, String orderNumber, String mail, String shopId){
		String subject = "您的订单已被取消";
		MailInfo mailInfo = new MailInfo();
		mailInfo.setTo(new String[]{mail});
		mailInfo.setSubject(subject);
		
		Map model = new HashMap();
		model.put("username", userName);
		model.put("orderNumber", orderNumber);
		model.put("shopId", shopId);
		
		mailEngine.sendVelocityMessage(mailInfo,
				"conf/love/mail/templates/orderCancel.vm", model);
    }
    
	public void setMailEngine(IMailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}

	public AddressService getAddressService() {
		return addressService;
	}

	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}

	public AddressHelper getAddressHelper() {
		return addressHelper;
	}

	public void setAddressHelper(AddressHelper addressHelper) {
		this.addressHelper = addressHelper;
	}
	
	public void setFormFactory(DefaultFormFactory formFactory) {
		this.formFactory = formFactory;
	}
}

package com.nonfamous.tang.web.netpay;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;

import com.nonfamous.commom.util.alipay.CheckURL;
import com.nonfamous.commom.util.alipay.Payment;
import com.nonfamous.commom.util.alipay.SignatureHelper;
import com.nonfamous.commom.util.alipay.SignatureHelper_return;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.tang.domain.GoodsBaseInfo;
import com.nonfamous.tang.domain.Member;
import com.nonfamous.tang.domain.mail.MailInfo;
import com.nonfamous.tang.domain.result.PayResult;
import com.nonfamous.tang.domain.trade.TradeOrder;
import com.nonfamous.tang.domain.trade.TradeOrderItem;
import com.nonfamous.tang.service.home.GoodsService;
import com.nonfamous.tang.service.home.MemberService;
import com.nonfamous.tang.service.home.TradeOrderService;
import com.nonfamous.tang.service.home.TradePayService;
import com.nonfamous.tang.service.mail.IMailEngine;
import com.nonfamous.tang.service.mail.MailException;
import com.nonfamous.tang.web.common.Constants;
import com.nonfamous.tang.web.common.netpay.NetPayConstants;

/**
 * 支付宝在线支付实现.
 * 
 * 注意：为了保证交易时的数据被实时记录，请将日志级别跳至INFO.
 * 
 * @author frank.liu
 *
 */
public class AlipayAction extends MultiActionController {
	
	private final Log logger = LogFactory.getLog(AlipayAction.class);
	
	private String payGateway = NetPayConstants.ALIPAY_GATEWAY; // 支付接口
	
	private String payReturn = NetPayConstants.RETURN_URL; // 支付完成后跳转返回的网址URL
	
	private String payNotify = NetPayConstants.NOTIFY_URL; //支付完成后通知接口 
	
	private String payNotifyQuery = NetPayConstants.ALIPAY_NOTIFY_QUERY_GATEWAY;
	
	private String inputCharset = NetPayConstants.ALIPAY_INPUT_CHARSET;
	
	private String signType = NetPayConstants.ALIPAY_SIGN_TYPE;

	private String emailSubject = "交易状态已改变为：买家已付款，等待卖家发货";
	
	/**
	 * 订单支付宝支付
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView alipay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		String orderId = rvp.getParameter("id").getString();
		if (orderId == null) {
			throw new NullPointerException("id can't be null");
		}
		
		// 取得订单
		TradeOrder tradeOrder = tradeOrderService.getTradeOrder(orderId);
		
		
		String service = NetPayConstants.ALIPAY_SERVICE;// create_direct_pay_by_user
		String partner = NetPayConstants.PARTNER_ID; // 支付宝合作伙伴id (账户内提取)test data
		String input_charset = getInputCharset();
		String paygateway = getPayGateway();
		
		//======== 用于支付宝URL验证相关数据 ==============
		String key = NetPayConstants.ALIPAY_KEY; // 支付宝安全校验码(账户内提取)test
		String sign_type = getSignType();
		
		//======== 提供给支付宝的交易相关数据 ==============
		String out_trade_no = orderId.trim(); // 商户网站订单号
		String seller_email = "daizhixia@gmail.com"; // 卖家支付宝帐户

		// ******以上是账户信息，以下是商品信息**************************
		String subject = "(订单编号："+orderId+")";; // 商品名称
		String price = tradeOrder.getMoney(tradeOrder.getPayFee()).toString(); // 订单总价
		String quantity = "1";
		// 用于查看订单的明细
		String show_url = "http://www.5iyya.com/order/detail.htm?id="+orderId+"&page_encoding=UTF-8";
		String payment_type = "1"; //商品购买
		
		// XXX: 物流性息是必须的
		// ******物流信息和支付宝通知，一般商城不需要通知，请删除此参数，并且在payment.java里面相应删除参数********
		String logistics_type = "EXPRESS";  //其他快递公司
		String logistics_fee = "0";
		String logistics_payment = "SELLER_PAY"; //卖家支付
		
		// 在我们的应用中是非不需的，可以删除
		String notify_url = getPayNotify();
		// 提供给支付宝的回调URL
		String return_url = getPayReturn();
		String itemUrl = Payment.CreateUrl(paygateway, service, sign_type,
				out_trade_no, input_charset, partner, key, seller_email,
				subject, price, quantity, show_url, payment_type, 
				logistics_type, logistics_fee, logistics_payment, return_url,
				notify_url);
		
		request.setAttribute("itemUrl", itemUrl);
		return new ModelAndView(new RedirectView(itemUrl, false));

	}

	public ModelAndView alipay_return(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		String orderId = rvp.getParameter("out_trade_no").getString();
		String partner = NetPayConstants.PARTNER_ID; // partner合作伙伴id（必须填写）
		String privateKey = NetPayConstants.PRIVATE_KEY; // partner
		// 的对应交易安全校验码（必须填写）
		String alipayNotifyURL = getPayNotifyQuery()
				+ "partner="
				+ partner
				+ "&notify_id="
				+ request.getParameter("notify_id")
				;
		String sign = request.getParameter("sign");
		// 获取支付宝ATN返回结果，true是正确的订单信息，false 是无效的
		String responseTxt = CheckURL.check(alipayNotifyURL);

		Map<String, String> params = new HashMap<String, String>();
		// 获得POST 过来参数设置到新的params中
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			valueStr = new String(valueStr.getBytes("iso8859_1"),"UTF-8");     
			params.put(name, valueStr);
		}

		String mysign = SignatureHelper_return.sign(params, privateKey);

		// 打印，收到消息比对sign的计算结果和传递来的sign是否匹配
		logger.info(mysign+"--------------------"+sign);

		if (mysign.equals(request.getParameter("sign"))
				&& responseTxt.equals("true")) {

			logger.info("支付成功");
			logger.info(params.get("body"));// 测试时候用，可以删除
			logger.info("显示订单信息");
			logger.info(responseTxt);
			
			PayResult result=getTradePayService().toolsPay(orderId, memberId);
			if(result.isSuccess()){
				//TODO 应该取出一条, 而且应该只有一条
				List<TradeOrderItem> items=tradeOrderService.findOrderItems(orderId);
				TradeOrderItem item=items.get(0);
				String goodsId=item.getGoodsId();
				GoodsBaseInfo good=goodsService.getGoodsBaseInfo(goodsId);
				if(good.getGoodsType()!=null&&good.getGoodsType().equals("T")){
					Long num=good.getGroupNum();
					num=item.getTotalNum()+num;
					good.setGroupNum(num);
					goodsService.updateGoodGroupNum(goodsId,num);
				}
					
				
				sendEmail(memberId, orderId);	
			}
			
			
			
		} else {
			logger.info("支付失败");
		}

		return new ModelAndView("home/trade/quickPaySucc");
	}

	private void sendEmail(String memberId, String orderId) {
		try{
			TradeOrder tradeOrder = this.tradeOrderService.getTradeOrder(orderId);
			Member member = this.memberService.getMemberById(tradeOrder.getSellerId());
			MailInfo info = new MailInfo();

		    info.setTo(new String[] { member.getEmail() });
		    info.setSubject(getEmailSubject());

		    Map<String, Object> model = new HashMap<String, Object>();
		    model.put("username", member.getName());
		    model.put("tradeNo", orderId);
		    
		    mailService.sendVelocityMessage(info, "conf/love/mail/templates/paySucceed.vm", model);
		    if(logger.isInfoEnabled()){
		    	logger.info("发送邮件成功!");
		    }
		}
		catch(MailException ex){
			logger.error("发送邮件失败!!" + ex.getMessage());
		}
		catch(Exception e){
			logger.error("发送邮件失败!!" + e.getMessage());
		}
	}

	public ModelAndView alipay_notify(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String partner = NetPayConstants.PARTNER_ID; // partner合作伙伴id（必须填写）
		String privateKey =NetPayConstants.PRIVATE_KEY ; // partner 的对应交易安全校验码（必须填写）
		String alipayNotifyURL = getPayNotifyQuery()
				+ "partner="
				+ partner
				+ "&notify_id="
				+ request.getParameter("notify_id");

		// 获取支付宝ATN返回结果，true是正确的订单信息，false 是无效的
		String responseTxt = CheckURL.check(alipayNotifyURL);

		Map<String, String> params = new HashMap<String, String>();
		// 获得POST 过来参数设置到新的params中
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			valueStr = new String(valueStr.getBytes("iso8859_1"),"UTF-8"); 
			params.put(name, valueStr);
		}

		String mysign = SignatureHelper.sign(params, privateKey);

		if (mysign.equals(request.getParameter("sign"))
				&& responseTxt.equals("true")) {

			logger.info("支付成功");
		} else {
			logger.info("支付失败");
		}
		return new ModelAndView("home/trade/quickPaySucc");
	}
	
	public String getPayGateway() {
		return payGateway;
	}

	public void setPayGateway(String payGateway) {
		this.payGateway = payGateway;
	}

	public String getPayReturn() {
		return payReturn;
	}

	public void setPayReturn(String payReturn) {
		this.payReturn = payReturn;
	}

	public String getPayNotify() {
		return payNotify;
	}

	public void setPayNotify(String payNotify) {
		this.payNotify = payNotify;
	}

	public String getPayNotifyQuery() {
		return payNotifyQuery;
	}

	public void setPayNotifyQuery(String payNotifyQuery) {
		this.payNotifyQuery = payNotifyQuery;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	//==========================================================dependencies
	
	private GoodsService goodsService;
	
	private TradePayService tradePayService;
	
	private TradeOrderService tradeOrderService;
	
	private MemberService memberService;
	
	private IMailEngine mailService;
	
    public TradeOrderService getTradeOrderService() {
        return tradeOrderService;
    }
    
    public void setTradeOrderService(TradeOrderService tradeOrderService) {
        this.tradeOrderService = tradeOrderService;
    }

    public TradePayService getTradePayService() {
		return tradePayService;
	}

	public void setTradePayService(TradePayService tradePayService) {
		this.tradePayService = tradePayService;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public IMailEngine getMailService() {
		return mailService;
	}

	public void setMailService(IMailEngine mailService) {
		this.mailService = mailService;
	}

	public void setGoodsService(GoodsService goodsService) {
		this.goodsService = goodsService;
	}		
}

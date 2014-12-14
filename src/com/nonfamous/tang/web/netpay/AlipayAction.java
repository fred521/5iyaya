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
 * ֧��������֧��ʵ��.
 * 
 * ע�⣺Ϊ�˱�֤����ʱ�����ݱ�ʵʱ��¼���뽫��־��������INFO.
 * 
 * @author frank.liu
 *
 */
public class AlipayAction extends MultiActionController {
	
	private final Log logger = LogFactory.getLog(AlipayAction.class);
	
	private String payGateway = NetPayConstants.ALIPAY_GATEWAY; // ֧���ӿ�
	
	private String payReturn = NetPayConstants.RETURN_URL; // ֧����ɺ���ת���ص���ַURL
	
	private String payNotify = NetPayConstants.NOTIFY_URL; //֧����ɺ�֪ͨ�ӿ� 
	
	private String payNotifyQuery = NetPayConstants.ALIPAY_NOTIFY_QUERY_GATEWAY;
	
	private String inputCharset = NetPayConstants.ALIPAY_INPUT_CHARSET;
	
	private String signType = NetPayConstants.ALIPAY_SIGN_TYPE;

	private String emailSubject = "����״̬�Ѹı�Ϊ������Ѹ���ȴ����ҷ���";
	
	/**
	 * ����֧����֧��
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
		
		// ȡ�ö���
		TradeOrder tradeOrder = tradeOrderService.getTradeOrder(orderId);
		
		
		String service = NetPayConstants.ALIPAY_SERVICE;// create_direct_pay_by_user
		String partner = NetPayConstants.PARTNER_ID; // ֧�����������id (�˻�����ȡ)test data
		String input_charset = getInputCharset();
		String paygateway = getPayGateway();
		
		//======== ����֧����URL��֤������� ==============
		String key = NetPayConstants.ALIPAY_KEY; // ֧������ȫУ����(�˻�����ȡ)test
		String sign_type = getSignType();
		
		//======== �ṩ��֧�����Ľ���������� ==============
		String out_trade_no = orderId.trim(); // �̻���վ������
		String seller_email = "daizhixia@gmail.com"; // ����֧�����ʻ�

		// ******�������˻���Ϣ����������Ʒ��Ϣ**************************
		String subject = "(������ţ�"+orderId+")";; // ��Ʒ����
		String price = tradeOrder.getMoney(tradeOrder.getPayFee()).toString(); // �����ܼ�
		String quantity = "1";
		// ���ڲ鿴��������ϸ
		String show_url = "http://www.5iyya.com/order/detail.htm?id="+orderId+"&page_encoding=UTF-8";
		String payment_type = "1"; //��Ʒ����
		
		// XXX: ������Ϣ�Ǳ����
		// ******������Ϣ��֧����֪ͨ��һ���̳ǲ���Ҫ֪ͨ����ɾ���˲�����������payment.java������Ӧɾ������********
		String logistics_type = "EXPRESS";  //������ݹ�˾
		String logistics_fee = "0";
		String logistics_payment = "SELLER_PAY"; //����֧��
		
		// �����ǵ�Ӧ�����Ƿǲ���ģ�����ɾ��
		String notify_url = getPayNotify();
		// �ṩ��֧�����Ļص�URL
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
		String partner = NetPayConstants.PARTNER_ID; // partner�������id��������д��
		String privateKey = NetPayConstants.PRIVATE_KEY; // partner
		// �Ķ�Ӧ���װ�ȫУ���루������д��
		String alipayNotifyURL = getPayNotifyQuery()
				+ "partner="
				+ partner
				+ "&notify_id="
				+ request.getParameter("notify_id")
				;
		String sign = request.getParameter("sign");
		// ��ȡ֧����ATN���ؽ����true����ȷ�Ķ�����Ϣ��false ����Ч��
		String responseTxt = CheckURL.check(alipayNotifyURL);

		Map<String, String> params = new HashMap<String, String>();
		// ���POST �����������õ��µ�params��
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

		// ��ӡ���յ���Ϣ�ȶ�sign�ļ������ʹ�������sign�Ƿ�ƥ��
		logger.info(mysign+"--------------------"+sign);

		if (mysign.equals(request.getParameter("sign"))
				&& responseTxt.equals("true")) {

			logger.info("֧���ɹ�");
			logger.info(params.get("body"));// ����ʱ���ã�����ɾ��
			logger.info("��ʾ������Ϣ");
			logger.info(responseTxt);
			
			PayResult result=getTradePayService().toolsPay(orderId, memberId);
			if(result.isSuccess()){
				//TODO Ӧ��ȡ��һ��, ����Ӧ��ֻ��һ��
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
			logger.info("֧��ʧ��");
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
		    	logger.info("�����ʼ��ɹ�!");
		    }
		}
		catch(MailException ex){
			logger.error("�����ʼ�ʧ��!!" + ex.getMessage());
		}
		catch(Exception e){
			logger.error("�����ʼ�ʧ��!!" + e.getMessage());
		}
	}

	public ModelAndView alipay_notify(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String partner = NetPayConstants.PARTNER_ID; // partner�������id��������д��
		String privateKey =NetPayConstants.PRIVATE_KEY ; // partner �Ķ�Ӧ���װ�ȫУ���루������д��
		String alipayNotifyURL = getPayNotifyQuery()
				+ "partner="
				+ partner
				+ "&notify_id="
				+ request.getParameter("notify_id");

		// ��ȡ֧����ATN���ؽ����true����ȷ�Ķ�����Ϣ��false ����Ч��
		String responseTxt = CheckURL.check(alipayNotifyURL);

		Map<String, String> params = new HashMap<String, String>();
		// ���POST �����������õ��µ�params��
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

			logger.info("֧���ɹ�");
		} else {
			logger.info("֧��ʧ��");
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

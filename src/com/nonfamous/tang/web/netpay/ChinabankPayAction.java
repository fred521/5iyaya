package com.nonfamous.tang.web.netpay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import beartool.MD5;

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
import com.nonfamous.tang.web.common.netpay.ChinabankPayConstants;

/**
 * 网银在线支付实现.
 * 
 * 注意：为了保证交易时的数据被实时记录，请将日志级别跳至INFO.
 * 
 * @author frank.liu
 * 
 */
public class ChinabankPayAction extends MultiActionController {

	private final Log logger = LogFactory.getLog(ChinabankPayAction.class);

	private String payGateway = ChinabankPayConstants.PAY_GATEWAY; // 支付接口

	private String payReturn = ChinabankPayConstants.PAY_RETURN; // 接收接口

	private String emailSubject = "交易状态已改变为：买家已付款，等待卖家发货";

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

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	/**
	 * 网银在线支付
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView chinabank(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		String orderId = rvp.getParameter("id").getString();
		if (orderId == null) {
			throw new NullPointerException("id can't be null");
		}
		/* TODO */
		String payGateway = getPayGateway(); // 支付接口

		String v_mid = "1001"; // 商户号，这里为测试商户号1001，替换为自己的商户号(老版商户号为4位或5位,新版为8位)即可
		String key = "test"; // 如果您还没有设置MD5密钥请登陆我们为您提供商户后台，地址：https://merchant3.
		// chinabank.com.cn/
		String v_url = getPayReturn(); // 接收接口
		String v_oid = orderId; // 订单号
		// if (request.getParameter("v_oid") != null
		// && !request.getParameter("v_oid").equals("")) // 判断是否有传递订单号
		// {
		// v_oid = request.getParameter("v_oid");
		// } else {
		// Date currTime = new Date();
		// SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd-" + v_mid
		// + "-hhmmss", Locale.US);
		// v_oid = sf.format(currTime); // 推荐订单号构成格式为 年月日-商户号-小时分钟秒
		// }
		String v_amount = "0.01"; // request.getParameter("v_amount"); // 订单金额
		String v_moneytype = "CNY"; // 币种
		String v_md5info = ""; // 对拼凑串MD5私钥加密后的值
		String text = v_amount + v_moneytype + v_oid + v_mid + v_url + key; // 拼凑加密串
		v_md5info = new MD5().getMD5ofStr(text); // 网银支付平台对MD5值只认大写字符串，
		// 所以小写的MD5值得转换为大写

		// ******以上是必填信息，以下是非必填信息**************************
		String v_rcvname = "张三"; // 收货人姓名
		String v_rcvaddr = "杭州市滨江区"; // 收货人地址
		String v_rcvtel = "15858156661"; // 收货人电话
		String v_rcvpost = "1000891a"; // 收货人邮编
		String v_rcvemail = "test1@test.com"; // 收货人邮件
		String v_rcvmobile = "1311311311311"; // 收货人手机号
		String remark1 = "备注1"; // 备注1

		String v_ordername = "李四"; // 订货人姓名
		String v_orderaddr = "杭州市滨江区"; // 订货人地址
		String v_ordertel = "588156662"; // 订货人电话
		String v_orderpost = "1000892b"; // 订货人邮编
		String v_orderemail = "test2@test.com"; // 订货人邮件
		String v_ordermobile = "1311311311312"; // 订货人手机号
		String remark2 = "备注2"; // 备注2

		Map<String, String> args = new HashMap<String, String>();
		args.put("v_mid", v_mid);
		args.put("key", key);
		args.put("v_url", v_url);
		args.put("v_oid", v_oid);
		args.put("v_amount", v_amount);
		args.put("v_moneytype", v_moneytype);
		args.put("v_md5info", v_md5info);

		return new ModelAndView("home/pay/chinabank", args);
	}

	/**
	 * 网银在线支付返回url
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView chinabank_return(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		String key = "test"; // 登陆后在上面的导航栏里可能找到“资料管理”，在资料管理的二级导航栏里有“MD5密钥设置”
		// 建议您设置一个16位以上的密钥或更高，密钥最多64位，但设置16位已经足够了
		// ****************************************

		// 获取参数
		String v_oid = request.getParameter("v_oid"); // 订单号
		String v_pmode = request.getParameter("v_pmode"); // 支付方式中文说明，如"中行长城信用卡"
		String v_pstatus = request.getParameter("v_pstatus"); // 支付结果，20支付完成；30
		// 支付失败；
		String v_pstring = request.getParameter("v_pstring"); // 对支付结果的说明，成功时（
		// v_pstatus
		// =20）为
		// "支付成功"，支付失败时
		// （v_pstatus
		// =30）为"支付失败"
		String v_amount = request.getParameter("v_amount"); // 订单实际支付金额
		String v_moneytype = request.getParameter("v_moneytype"); // 币种
		String v_md5str = request.getParameter("v_md5str"); // MD5校验码
		String remark1 = request.getParameter("remark1"); // 备注1

		String text = v_oid + v_pstatus + v_amount + v_moneytype + key;
		String v_md5text = new MD5().getMD5ofStr(text).toUpperCase();
		// 打印，收到消息比对v_md5text的计算结果和传递来的v_md5str是否匹配
		if (logger.isInfoEnabled()) {
			logger.info(v_md5str + "--------------------" + v_md5text);
		}
		if (v_md5text.equals(v_md5str)) {
			if (v_pstatus.equals("20")) {
				logger.info("支付成功");
				logger.info("MD5校验码:" + v_md5str);
				logger.info("订单号:" + v_oid);
				logger.info("支付卡种:" + v_pmode);
				logger.info("支付结果:" + v_pstring);
				logger.info("支付金额:" + v_amount);
				logger.info("支付币种:" + v_moneytype);
				logger.info("备注:" + remark1);

				// TODO:业务逻辑
				PayResult result = getTradePayService().toolsPay(v_oid,
						memberId);
				if (result.isSuccess()) {
					// TODO 应该取出一条, 而且应该只有一条
					List<TradeOrderItem> items = tradeOrderService
							.findOrderItems(v_oid);
					TradeOrderItem item = items.get(0);
					String goodsId = item.getGoodsId();
					GoodsBaseInfo good = goodsService.getGoodsBaseInfo(goodsId);
					if (good.getGoodsType() != null
							&& good.getGoodsType().equals("T")) {
						Long num = good.getGroupNum();
						num = item.getTotalNum() + num;
						good.setGroupNum(num);
						goodsService.updateGoodGroupNum(goodsId, num);
					}

					sendEmail(memberId, v_oid);
				}

			} else {
				logger.info("支付失败!");
			}
		} else {
			logger.info("校验失败,数据可疑");
		}
		return new ModelAndView("home/trade/quickPaySucc");
	}

	/**
	 * 网银在线自动对账接口.
	 * 
	 * 本功能需要通知网银在线客服部门.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView pay_notify(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String key = "test"; // 登陆后在上面的导航栏里可能找到“资料管理”，在资料管理的二级导航栏里有“MD5密钥设置”
		// 建议您设置一个16位以上的密钥或更高，密钥最多64位，但设置16位已经足够了
		// ****************************************

		// 获取参数
		String v_oid = request.getParameter("v_oid"); // 订单号
		String v_pmode = request.getParameter("v_pmode"); // 支付方式中文说明，如"中行长城信用卡"
		String v_pstatus = request.getParameter("v_pstatus"); // 支付结果，20支付完成；30
		// 支付失败；
		String v_pstring = request.getParameter("v_pstring"); // 对支付结果的说明，成功时（
		// v_pstatus
		// =20）为
		// "支付成功"，支付失败时
		// （v_pstatus
		// =30）为"支付失败"
		String v_amount = request.getParameter("v_amount"); // 订单实际支付金额
		String v_moneytype = request.getParameter("v_moneytype"); // 币种
		String v_md5str = request.getParameter("v_md5str"); // MD5校验码
		String remark1 = request.getParameter("remark1"); // 备注1

		String text = v_oid + v_pstatus + v_amount + v_moneytype + key;
		String v_md5text = new MD5().getMD5ofStr(text).toUpperCase();
		// 打印，收到消息比对v_md5text的计算结果和传递来的v_md5str是否匹配
		if (logger.isInfoEnabled()) {
			logger.info(v_md5str + "--------------------" + v_md5text);
		}
		if (v_md5text.equals(v_md5str)) {
			if (v_pstatus.equals("20")) {
				logger.info("支付成功");
				logger.info("MD5校验码:" + v_md5str);
				logger.info("订单号:" + v_oid);
				logger.info("支付卡种:" + v_pmode);
				logger.info("支付结果:" + v_pstring);
				logger.info("支付金额:" + v_amount);
				logger.info("支付币种:" + v_moneytype);
				logger.info("备注:" + remark1);

				// TODO:业务逻辑

			} else {
				logger.info("支付失败!");
			}
		} else {
			logger.info("校验失败,数据可疑");
		}
		return new ModelAndView("home/trade/quickPaySucc");
	}

	private void sendEmail(String memberId, String orderId) {
		try {
			TradeOrder tradeOrder = this.tradeOrderService
					.getTradeOrder(orderId);
			Member member = this.memberService.getMemberById(tradeOrder
					.getSellerId());
			MailInfo info = new MailInfo();

			info.setTo(new String[] { member.getEmail() });
			info.setSubject(getEmailSubject());

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("username", member.getName());
			model.put("tradeNo", orderId);

			mailService.sendVelocityMessage(info,
					"conf/love/mail/templates/paySucceed.vm", model);
			if (logger.isInfoEnabled()) {
				logger.info("发送邮件成功!");
			}
		} catch (MailException ex) {
			logger.error("发送邮件失败!!" + ex.getMessage());
		} catch (Exception e) {
			logger.error("发送邮件失败!!" + e.getMessage());
		}
	}

	// ==========================================================dependencies
	
	private GoodsService goodsService;
	
	private TradeOrderService tradeOrderService;

	private TradePayService tradePayService;

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

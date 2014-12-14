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
 * ��������֧��ʵ��.
 * 
 * ע�⣺Ϊ�˱�֤����ʱ�����ݱ�ʵʱ��¼���뽫��־��������INFO.
 * 
 * @author frank.liu
 * 
 */
public class ChinabankPayAction extends MultiActionController {

	private final Log logger = LogFactory.getLog(ChinabankPayAction.class);

	private String payGateway = ChinabankPayConstants.PAY_GATEWAY; // ֧���ӿ�

	private String payReturn = ChinabankPayConstants.PAY_RETURN; // ���սӿ�

	private String emailSubject = "����״̬�Ѹı�Ϊ������Ѹ���ȴ����ҷ���";

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
	 * ��������֧��
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
		String payGateway = getPayGateway(); // ֧���ӿ�

		String v_mid = "1001"; // �̻��ţ�����Ϊ�����̻���1001���滻Ϊ�Լ����̻���(�ϰ��̻���Ϊ4λ��5λ,�°�Ϊ8λ)����
		String key = "test"; // �������û������MD5��Կ���½����Ϊ���ṩ�̻���̨����ַ��https://merchant3.
		// chinabank.com.cn/
		String v_url = getPayReturn(); // ���սӿ�
		String v_oid = orderId; // ������
		// if (request.getParameter("v_oid") != null
		// && !request.getParameter("v_oid").equals("")) // �ж��Ƿ��д��ݶ�����
		// {
		// v_oid = request.getParameter("v_oid");
		// } else {
		// Date currTime = new Date();
		// SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd-" + v_mid
		// + "-hhmmss", Locale.US);
		// v_oid = sf.format(currTime); // �Ƽ������Ź��ɸ�ʽΪ ������-�̻���-Сʱ������
		// }
		String v_amount = "0.01"; // request.getParameter("v_amount"); // �������
		String v_moneytype = "CNY"; // ����
		String v_md5info = ""; // ��ƴ�մ�MD5˽Կ���ܺ��ֵ
		String text = v_amount + v_moneytype + v_oid + v_mid + v_url + key; // ƴ�ռ��ܴ�
		v_md5info = new MD5().getMD5ofStr(text); // ����֧��ƽ̨��MD5ֵֻ�ϴ�д�ַ�����
		// ����Сд��MD5ֵ��ת��Ϊ��д

		// ******�����Ǳ�����Ϣ�������ǷǱ�����Ϣ**************************
		String v_rcvname = "����"; // �ջ�������
		String v_rcvaddr = "�����б�����"; // �ջ��˵�ַ
		String v_rcvtel = "15858156661"; // �ջ��˵绰
		String v_rcvpost = "1000891a"; // �ջ����ʱ�
		String v_rcvemail = "test1@test.com"; // �ջ����ʼ�
		String v_rcvmobile = "1311311311311"; // �ջ����ֻ���
		String remark1 = "��ע1"; // ��ע1

		String v_ordername = "����"; // ����������
		String v_orderaddr = "�����б�����"; // �����˵�ַ
		String v_ordertel = "588156662"; // �����˵绰
		String v_orderpost = "1000892b"; // �������ʱ�
		String v_orderemail = "test2@test.com"; // �������ʼ�
		String v_ordermobile = "1311311311312"; // �������ֻ���
		String remark2 = "��ע2"; // ��ע2

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
	 * ��������֧������url
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
		String key = "test"; // ��½��������ĵ�����������ҵ������Ϲ����������Ϲ���Ķ������������С�MD5��Կ���á�
		// ����������һ��16λ���ϵ���Կ����ߣ���Կ���64λ��������16λ�Ѿ��㹻��
		// ****************************************

		// ��ȡ����
		String v_oid = request.getParameter("v_oid"); // ������
		String v_pmode = request.getParameter("v_pmode"); // ֧����ʽ����˵������"���г������ÿ�"
		String v_pstatus = request.getParameter("v_pstatus"); // ֧�������20֧����ɣ�30
		// ֧��ʧ�ܣ�
		String v_pstring = request.getParameter("v_pstring"); // ��֧�������˵�����ɹ�ʱ��
		// v_pstatus
		// =20��Ϊ
		// "֧���ɹ�"��֧��ʧ��ʱ
		// ��v_pstatus
		// =30��Ϊ"֧��ʧ��"
		String v_amount = request.getParameter("v_amount"); // ����ʵ��֧�����
		String v_moneytype = request.getParameter("v_moneytype"); // ����
		String v_md5str = request.getParameter("v_md5str"); // MD5У����
		String remark1 = request.getParameter("remark1"); // ��ע1

		String text = v_oid + v_pstatus + v_amount + v_moneytype + key;
		String v_md5text = new MD5().getMD5ofStr(text).toUpperCase();
		// ��ӡ���յ���Ϣ�ȶ�v_md5text�ļ������ʹ�������v_md5str�Ƿ�ƥ��
		if (logger.isInfoEnabled()) {
			logger.info(v_md5str + "--------------------" + v_md5text);
		}
		if (v_md5text.equals(v_md5str)) {
			if (v_pstatus.equals("20")) {
				logger.info("֧���ɹ�");
				logger.info("MD5У����:" + v_md5str);
				logger.info("������:" + v_oid);
				logger.info("֧������:" + v_pmode);
				logger.info("֧�����:" + v_pstring);
				logger.info("֧�����:" + v_amount);
				logger.info("֧������:" + v_moneytype);
				logger.info("��ע:" + remark1);

				// TODO:ҵ���߼�
				PayResult result = getTradePayService().toolsPay(v_oid,
						memberId);
				if (result.isSuccess()) {
					// TODO Ӧ��ȡ��һ��, ����Ӧ��ֻ��һ��
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
				logger.info("֧��ʧ��!");
			}
		} else {
			logger.info("У��ʧ��,���ݿ���");
		}
		return new ModelAndView("home/trade/quickPaySucc");
	}

	/**
	 * ���������Զ����˽ӿ�.
	 * 
	 * ��������Ҫ֪ͨ�������߿ͷ�����.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView pay_notify(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String key = "test"; // ��½��������ĵ�����������ҵ������Ϲ����������Ϲ���Ķ������������С�MD5��Կ���á�
		// ����������һ��16λ���ϵ���Կ����ߣ���Կ���64λ��������16λ�Ѿ��㹻��
		// ****************************************

		// ��ȡ����
		String v_oid = request.getParameter("v_oid"); // ������
		String v_pmode = request.getParameter("v_pmode"); // ֧����ʽ����˵������"���г������ÿ�"
		String v_pstatus = request.getParameter("v_pstatus"); // ֧�������20֧����ɣ�30
		// ֧��ʧ�ܣ�
		String v_pstring = request.getParameter("v_pstring"); // ��֧�������˵�����ɹ�ʱ��
		// v_pstatus
		// =20��Ϊ
		// "֧���ɹ�"��֧��ʧ��ʱ
		// ��v_pstatus
		// =30��Ϊ"֧��ʧ��"
		String v_amount = request.getParameter("v_amount"); // ����ʵ��֧�����
		String v_moneytype = request.getParameter("v_moneytype"); // ����
		String v_md5str = request.getParameter("v_md5str"); // MD5У����
		String remark1 = request.getParameter("remark1"); // ��ע1

		String text = v_oid + v_pstatus + v_amount + v_moneytype + key;
		String v_md5text = new MD5().getMD5ofStr(text).toUpperCase();
		// ��ӡ���յ���Ϣ�ȶ�v_md5text�ļ������ʹ�������v_md5str�Ƿ�ƥ��
		if (logger.isInfoEnabled()) {
			logger.info(v_md5str + "--------------------" + v_md5text);
		}
		if (v_md5text.equals(v_md5str)) {
			if (v_pstatus.equals("20")) {
				logger.info("֧���ɹ�");
				logger.info("MD5У����:" + v_md5str);
				logger.info("������:" + v_oid);
				logger.info("֧������:" + v_pmode);
				logger.info("֧�����:" + v_pstring);
				logger.info("֧�����:" + v_amount);
				logger.info("֧������:" + v_moneytype);
				logger.info("��ע:" + remark1);

				// TODO:ҵ���߼�

			} else {
				logger.info("֧��ʧ��!");
			}
		} else {
			logger.info("У��ʧ��,���ݿ���");
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
				logger.info("�����ʼ��ɹ�!");
			}
		} catch (MailException ex) {
			logger.error("�����ʼ�ʧ��!!" + ex.getMessage());
		} catch (Exception e) {
			logger.error("�����ʼ�ʧ��!!" + e.getMessage());
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

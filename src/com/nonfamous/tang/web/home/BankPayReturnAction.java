package com.nonfamous.tang.web.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.tang.bank.BankNameEnum;
import com.nonfamous.tang.bank.comm.BankCommReturnData;
import com.nonfamous.tang.bank.comm.BankCommSignHelper;
import com.nonfamous.tang.domain.trade.TradePay;
import com.nonfamous.tang.service.home.TradePayService;

/**
 * <p>
 * ֧��action
 * </p>
 * 
 * @author:alan
 * @version $Id: BankPayReturnAction.java,v 1.1 2008/07/11 00:46:54 fred Exp $
 */
public class BankPayReturnAction extends MultiActionController {
	private static Log logger = LogFactory.getLog(BankPayReturnAction.class);

	private TradePayService tradePayService;

	private BankCommSignHelper bankcommSignHelper;

	/**
	 * ��ͨ���ж���֧�����лص�����
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView e_b_comm_success(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			RequestValueParse rvp = new RequestValueParse(request);

			// ���з����ַ���
			String notifyMsg = rvp.getParameter("notifyMsg").getString();

			if (logger.isDebugEnabled()) {
				logger.debug("���з����ַ���:" + notifyMsg);
			}

			if (StringUtils.isBlank(notifyMsg)) {
				response
						.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);// 500����
				return null;
			}

			BankCommReturnData returnData = new BankCommReturnData(notifyMsg);
			// У�鷵����Ϣ
			if (!bankcommSignHelper.verify(returnData)) {
				logger.debug("���з����ַ���У��δͨ��");
				response
						.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);// 500����
				return null;
			}

			// ��ȡ֧����ˮ
			TradePay tradePay = tradePayService.getTradePayByOrderNo(returnData
					.getOrderNo());

			if (tradePay == null) {
				response
						.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);// 500����
				logger.error("���ݶ������Ҳ���ָ���Ķ�����Ϣ�������ţ�" + returnData.getOrderNo());
				return null;
			}
			if (StringUtils.equals(tradePay.getPayStatus(),
					TradePay.PAY_STATUS_SUCCESS)) {
				response
						.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);// 500����
				logger.error("�ö����Ѿ��ɹ�֧���������ţ�" + returnData.getOrderNo());
				return null;
			}

			tradePayService.changePayStatus(tradePay.getId(),
					TradePay.PAY_STATUS_SUCCESS, "8888",
					BankNameEnum.Bank_Comm, returnData.getSerialNo());

		} catch (Exception e) {
			logger.error(e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);// 500����
			return null;
		}
		
		ModelAndView mv = new ModelAndView("/home/bank/paySuccess");
		return mv;
	}

	public void setTradePayService(TradePayService tradePayService) {
		this.tradePayService = tradePayService;
	}

	public void setBankcommSignHelper(BankCommSignHelper bankcommSignHelper) {
		this.bankcommSignHelper = bankcommSignHelper;
	}

}

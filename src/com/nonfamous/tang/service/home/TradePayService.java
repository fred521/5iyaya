package com.nonfamous.tang.service.home;

import com.nonfamous.tang.dao.query.TradePayQuery;
import com.nonfamous.tang.domain.result.PayResult;
import com.nonfamous.tang.domain.trade.TradeOrder;
import com.nonfamous.tang.domain.trade.TradePay;

/**
 * <p>
 * ����֧������ӿ�
 * </p>
 * 
 * @author:daodao
 * @version $Id: TradePayService.java,v 1.1 2008/07/11 00:46:58 fred Exp $
 */
public interface TradePayService {
	/**
	 * ����֧��
	 * 
	 * @param tradeOrderNo
	 * @return
	 */
	public PayResult orderPay(String tradeOrderNo, String memberId);
	
	/**
	 * ֧������֧��
	 * @param tradeOrderNo
	 * @param memberId
	 * @return
	 */
	public PayResult toolsPay(String tradeOrderNo, String memberId);

	/**
	 * ����֧��
	 * 
	 * @param order
	 * @param note
	 * @param operator
	 * @return
	 */
	public PayResult quickPay(TradeOrder order, String note);

	/**
	 * ��ȡ�����б�
	 * 
	 * @param memberId
	 * @return
	 */
	public TradePayQuery queryTradePayList(TradePayQuery query);

	/**
	 * �޸�֧��״̬
	 * 
	 * @param payId
	 * @param status
	 * @param modifier
	 * @param payBank  ֧������
	 * @param serialNo ���з�����ˮ��
	 */
	public PayResult changePayStatus(Long payId, String payStatus,
			String modifier,String payBank,String serialNo);

	/**
	 * �޸�ת��״̬��ĿǰΪ��̨ʹ�ã������κ����ƣ�
	 * 
	 * @param payId
	 * @param transStatus
	 * @param modifier
	 */
	public PayResult changeTransStatus(Long payId, String transStatus,
			String modifier);
	
	/**
	 * ���ݶ����Ż�ȡ֧����ˮ
	 * @param orderNo
	 * @return TradePay
	 */
	public TradePay getTradePayByOrderNo(String orderNo);
	
	
}

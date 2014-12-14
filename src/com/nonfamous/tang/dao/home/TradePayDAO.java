package com.nonfamous.tang.dao.home;

import com.nonfamous.tang.dao.query.TradePayQuery;
import com.nonfamous.tang.domain.trade.TradePay;

/**
 * <p>
 * ����֧������DAO�ӿ�
 * </p>
 * 
 * @author:daodao
 * @version $Id: TradePayDAO.java,v 1.1 2008/07/11 00:46:41 fred Exp $
 */
public interface TradePayDAO {
	/**
	 * ����֧������
	 * 
	 * @param tradePay
	 * @return
	 */
	public Long insertTradePay(TradePay tradePay);

	/**
	 * ����֧������
	 * 
	 * @param query
	 * @return
	 */
	public TradePayQuery findTradePayList(TradePayQuery query);

	/**
	 * ��ȡ֧������
	 * 
	 * @param payId
	 * @return
	 */
	public TradePay findTradePayById(Long payId);

	/**
	 * �޸�֧��״̬
	 * 
	 * @param payId
	 * @param status
	 * @param modifier
	 * @param payBank  ֧������
	 * @param serialNo ���з�����ˮ��
	 */
	public void updatePayStatus(Long payId, String status, String modifier,String payBank,String serialNo);

	/**
	 * �޸�ת��״̬
	 * 
	 * @param payId
	 * @param status
	 * @param modifier
	 */
	public void updateTransStatus(Long payId, String status, String modifier);

	/**
	 * ���ݽ��׶����Ż�ȡ֧����Ϣ
	 * 
	 * @param tradeOrderNo
	 * @return
	 */
	public TradePay findTradePayByOrderNo(String tradeOrderNo);

	/**
	 * �޸�֧��ʱ��
	 * 
	 * @param id
	 * @param memberId
	 */
	public void changePayDate(Long id, String modifier);
}

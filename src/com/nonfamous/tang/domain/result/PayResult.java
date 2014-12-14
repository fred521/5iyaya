package com.nonfamous.tang.domain.result;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.tang.domain.trade.TradePay;

/**
 * <p>
 * ����֧���������
 * </p>
 * 
 * @author:daodao
 * @version $Id: PayResult.java,v 1.1 2008/07/11 00:47:06 fred Exp $
 */
public class PayResult extends ResultBase {

	private static final long serialVersionUID = -4804101301915154246L;

	/** ����Ľ��׶���״̬�����������֧�� */
	public static final String ERROR_TRADE_ORDER_STATUS = "ERROR_TRADE_ORDER_STATUS";

	/** ֧���Ѿ��ɹ��������ٴν���֧�� */
	public static final String ERROR_PAY_HAS_CREATED = "ERROR_PAY_HAS_CREATED";

	/** û�����������˺ţ����������֧�� */
	public static final String ERROR_NO_SELLER_ACCOUNT = "ERROR_NO_SELLER_ACCOUNT";

	/** �޷��ҵ���Ӧ������Ϣ�����������֧�� */
	public static final String ERROR_NO_SELLER_INFO = "ERROR_NO_SELLER_INFO";

	/** ���������Լ�֧�� */
	public static final String ERROR_SAME_BUYER_SELLER = "ERROR_SAME_BUYER_SELLER";

	// �޸�֧��״̬���
	/** �����֧��״̬������������״̬�޸� */
	public static final String ERROR_PAY_STATUS = "ERROR_PAY_STATUS";

	/** ����Ķ���״̬���������޸�֧��״̬ */
	public static final String ERROR_ORDER_STATUS = "ERROR_ORDER_STATUS";

	private TradePay tradePay;

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @return
	 */
	public String getErrorMessage() {
		if (StringUtils.equals(getErrorCode(), ERROR_TRADE_ORDER_STATUS)) {
			return "����Ľ��׶���״̬�����������֧��!";
		} else if (StringUtils.equals(getErrorCode(), ERROR_PAY_HAS_CREATED)) {
			return "֧���Ѿ��ɹ��������ٴν���֧��!";
		} else if (StringUtils.equals(getErrorCode(), ERROR_NO_SELLER_ACCOUNT)) {
			return "���������˺Ų����ڣ����������֧��!";
		} else if (StringUtils.equals(getErrorCode(), ERROR_NO_SELLER_INFO)) {
			return "�޷��ҵ���Ӧ������Ϣ�����������֧��!";
		} else if (StringUtils.equals(getErrorCode(), ERROR_SAME_BUYER_SELLER)) {
			return "���������Լ�֧��!";
		} else if (StringUtils.equals(getErrorCode(), ERROR_PAY_STATUS)) {
			return "�����֧��״̬������������״̬�޸�!";
		} else if (StringUtils.equals(getErrorCode(), ERROR_ORDER_STATUS)) {
			return "����Ķ���״̬������������״̬�޸� !";
		}
		return super.getErrorMessage();
	}

	public TradePay getTradePay() {
		return tradePay;
	}

	public void setTradePay(TradePay tradePay) {
		this.tradePay = tradePay;
	}

}

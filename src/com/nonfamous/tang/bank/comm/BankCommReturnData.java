package com.nonfamous.tang.bank.comm;

import com.nonfamous.tang.domain.base.DomainBase;

public class BankCommReturnData extends DomainBase {

	private static final long serialVersionUID = 8369671542465361307L;

	private static final String Separator = "|";

	private static final String SeparatorRegular = "\\|";

	// �ͻ���
	private String merchNo;

	// ������
	private String orderNo;

	// ���׽��
	private String tranaMount;

	// ���ױ���
	private String trancurrType;

	// ƽ̨���κ�
	private String paybatNo;

	// �̻����κ�
	private String merchbatchNo;

	// ��������
	private String tranDate;

	// ����ʱ��
	private String tranTime;

	// ������ˮ��
	private String serialNo;

	// ���׽��
	private String tranrst;

	// �������ܶ�
	private String feesum;

	// ���п�����
	private String cardType;

	// ���б�ע
	private String backMoNo;

	// ������Ϣ
	private String errDis;

	// ����ǩ��
	private String signMsg;

	private String returnString;

	public BankCommReturnData() {
		super();
	}

	/**
	 * ���з��صĽ����һ���ַ���
	 * ����301310053110018|99990001|100.00|CNY|14124|412444|20060729|120902|0000052|1|10.21|2|||QWR+QWRQWEQD+G\RQWRFVBNJKFSFAF
	 * 
	 * @param msg
	 */
	public BankCommReturnData(String msg) {
		returnString = msg;
		String[] strings = returnString.split(SeparatorRegular);
		if (strings == null || strings.length != 15) {
			throw new IllegalArgumentException("��ͨ���з��ص�֧���������ȷ [" + msg + "]");
		}
		merchNo = strings[0];
		orderNo = strings[1];
		tranaMount = strings[2];
		trancurrType = strings[3];
		paybatNo = strings[4];
		merchbatchNo = strings[5];
		tranDate = strings[6];
		tranTime = strings[7];
		serialNo = strings[8];
		tranrst = strings[9];
		feesum = strings[10];
		cardType = strings[11];
		backMoNo = strings[12];
		errDis = strings[13];
		signMsg = strings[14];
	}

	/**
	 * �õ����ص������ַ���(��У��)
	 * 
	 * @return
	 */
	public String getReturnDataString() {
		return this.returnString.substring(0, returnString
				.lastIndexOf(Separator) + 1);
	}

	/**
	 * �õ�ǩ����
	 * 
	 * @return
	 */
	public String getSignString() {
		return this.returnString
				.substring(returnString.lastIndexOf(Separator) + 1);
	}

	public String getBackMoNo() {
		return backMoNo;
	}

	public void setBackMoNo(String backMoNo) {
		this.backMoNo = backMoNo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getErrDis() {
		return errDis;
	}

	public void setErrDis(String errDis) {
		this.errDis = errDis;
	}

	public String getFeesum() {
		return feesum;
	}

	public void setFeesum(String feesum) {
		this.feesum = feesum;
	}

	public String getMerchbatchNo() {
		return merchbatchNo;
	}

	public void setMerchbatchNo(String merchbatchNo) {
		this.merchbatchNo = merchbatchNo;
	}

	public String getMerchNo() {
		return merchNo;
	}

	public void setMerchNo(String merchNo) {
		this.merchNo = merchNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPaybatNo() {
		return paybatNo;
	}

	public void setPaybatNo(String paybatNo) {
		this.paybatNo = paybatNo;
	}

	public String getReturnString() {
		return returnString;
	}

	public void setReturnString(String returnString) {
		this.returnString = returnString;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String getTranaMount() {
		return tranaMount;
	}

	public void setTranaMount(String tranaMount) {
		this.tranaMount = tranaMount;
	}

	public String getTrancurrType() {
		return trancurrType;
	}

	public void setTrancurrType(String trancurrType) {
		this.trancurrType = trancurrType;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getTranrst() {
		return tranrst;
	}

	public void setTranrst(String tranrst) {
		this.tranrst = tranrst;
	}

	public String getTranTime() {
		return tranTime;
	}

	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}

	// public static void main(String[] args) {
	// String s =
	// "301310053110018|99990001|100.00|CNY|14124|412444|20060729|120902|0000052|1|10.21|2|||QWR+QWRQWEQD+G\\RQWRFVBNJKFSFAF";
	// BankCommReturnData d = new BankCommReturnData(s);
	// System.out.println(d.getReturnDataString());
	// System.out.println(d.getSignString());
	// }

}

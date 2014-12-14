package com.nonfamous.tang.domain.netpay;

/**
 * Payment model used for chianbank(��������). 
 * 
 * For detail, please refer to http://www.chinabank.com.cn 
 * 
 * @author frank.liu
 *
 */
public class ChinabankPayment extends NetPayBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -459706128942566287L;

	//�̻����
	private String vMid;
	//�������
	private String vOid;
	//�����ܽ��
	private double vAmount;
	//����
	private String vMoneytype;
	//URL��ַ,��������ɹ���󷵻ص��̻�ҳ��
	private String vUrl;
	//MD5У����
	private String vMd5info;
	//��ע1
	private String remark1;
	//��ע2
	private String remark2;
	//�ջ�������
	private String vRcvname;
	//�ջ��˵�ַ
	private String vRcvaddr;
	//�ջ��˵绰
	private String vRcvtel;
	//�ջ����ʱ�
	private String vRcvpost;
	//�ջ���Email
	private String vRcvemail;
	//�ջ����ֻ���
	private String vRcvmobile;
	//����������
	private String vOrdername;
	//�����˵�ַ
	private String vOrderaddr;
	//�����˵绰
	private String vOrdertel;
	//�������ʱ�
	private String vOrderpost;
	//������Email
	private String vOrderemail;
	//�������ֻ���
	private String vOrdermobile;
	public String getVMid() {
		return vMid;
	}
	public void setVMid(String mid) {
		vMid = mid;
	}
	public String getVOid() {
		return vOid;
	}
	public void setVOid(String oid) {
		vOid = oid;
	}
	public double getVAmount() {
		return vAmount;
	}
	public void setVAmount(double amount) {
		vAmount = amount;
	}
	public String getVMoneytype() {
		return vMoneytype;
	}
	public void setVMoneytype(String moneytype) {
		vMoneytype = moneytype;
	}
	public String getVUrl() {
		return vUrl;
	}
	public void setVUrl(String url) {
		vUrl = url;
	}
	public String getVMd5info() {
		return vMd5info;
	}
	public void setVMd5info(String md5info) {
		vMd5info = md5info;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getVRcvname() {
		return vRcvname;
	}
	public void setVRcvname(String rcvname) {
		vRcvname = rcvname;
	}
	public String getVRcvaddr() {
		return vRcvaddr;
	}
	public void setVRcvaddr(String rcvaddr) {
		vRcvaddr = rcvaddr;
	}
	public String getVRcvtel() {
		return vRcvtel;
	}
	public void setVRcvtel(String rcvtel) {
		vRcvtel = rcvtel;
	}
	public String getVRcvpost() {
		return vRcvpost;
	}
	public void setVRcvpost(String rcvpost) {
		vRcvpost = rcvpost;
	}
	public String getVRcvemail() {
		return vRcvemail;
	}
	public void setVRcvemail(String rcvemail) {
		vRcvemail = rcvemail;
	}
	public String getVRcvmobile() {
		return vRcvmobile;
	}
	public void setVRcvmobile(String rcvmobile) {
		vRcvmobile = rcvmobile;
	}
	public String getVOrdername() {
		return vOrdername;
	}
	public void setVOrdername(String ordername) {
		vOrdername = ordername;
	}
	public String getVOrderaddr() {
		return vOrderaddr;
	}
	public void setVOrderaddr(String orderaddr) {
		vOrderaddr = orderaddr;
	}
	public String getVOrdertel() {
		return vOrdertel;
	}
	public void setVOrdertel(String ordertel) {
		vOrdertel = ordertel;
	}
	public String getVOrderpost() {
		return vOrderpost;
	}
	public void setVOrderpost(String orderpost) {
		vOrderpost = orderpost;
	}
	public String getVOrderemail() {
		return vOrderemail;
	}
	public void setVOrderemail(String orderemail) {
		vOrderemail = orderemail;
	}
	public String getVOrdermobile() {
		return vOrdermobile;
	}
	public void setVOrdermobile(String ordermobile) {
		vOrdermobile = ordermobile;
	}
	
}

package com.nonfamous.tang.bank.comm;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bocom.netpay.b2cAPI.BOCOMB2CClient;
import com.bocom.netpay.b2cAPI.BOCOMSetting;
import com.bocom.netpay.b2cAPI.NetSignServer;

/**
 * ��ͨ����
 * 
 * @author fish
 * 
 */
public class BankCommSignHelper {

	private static Log logger = LogFactory.getLog(BankCommSignHelper.class);

	private BankCommConfig config;

	private String signEncoding = "GBK";

	private String merchantId;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getSignEncoding() {
		return signEncoding;
	}

	public void setSignEncoding(String signEncoding) {
		this.signEncoding = signEncoding;
	}

	public BankCommConfig getConfig() {
		return config;
	}

	public void setConfig(BankCommConfig config) {
		this.config = config;
	}

	public void init() throws Exception {
		this.init(config.getConfigStream());
	}

	public void init(InputStream is) throws Exception {
		BOCOMB2CClient client = new BOCOMB2CClient();
		int ret;
		try {
			ret = client.initialize(is);
			if (ret != 0) { // ��ʼ��ʧ��
				throw new IllegalStateException("��ʼ��ʧ��,������Ϣ��"
						+ client.getLastErr());
			}
		} catch (Exception e) {
			logger.debug("error then init BankcommSignHelper", e);
			throw e;
		}
	}

	/**
	 * �Խ�ͨ���е��ύ���ݽ���ǩ�� TODO:�����ݵ������Խ���У��
	 * 
	 * @param data
	 * @return
	 */
	public String sign(BankCommData data) {
		if (data == null) {
			throw new NullPointerException("BankcommData can't be null");
		}
		data.setMerID(this.merchantId);
		String msg = data.getSourceMsg();
		if (logger.isDebugEnabled()) {
			logger.debug("get SourceMsg:" + msg);
		}
		try {
			byte[] bytes = msg.getBytes(signEncoding);
			NetSignServer nss = new NetSignServer();
			String merchantDN = BOCOMSetting.MerchantCertDN;
			nss.NSSetPlainText(bytes);
			byte bSignMsg[] = nss.NSDetachedSign(merchantDN); // �Զ�������ǩ��
			if (nss.getLastErrnum() < 0) {
				throw new IllegalStateException("ERROR:�̻���ǩ��ʧ��");
			}
			return new String(bSignMsg, signEncoding);
		} catch (UnsupportedEncodingException e) {
			logger.error("error then sign data", e);
		}
		return null;
	}

	/**
	 * �����з��ص����ݽ���У��
	 * 
	 * @param data
	 * @return �Ƿ���ȷ true ����ǩ����ȷ
	 */
	public boolean verify(BankCommReturnData data) {
		String dataString = data.getReturnDataString();
		String sign = data.getSignString();
		NetSignServer nss = new NetSignServer();
		try {
			nss.NSDetachedVerify(sign.getBytes(signEncoding), dataString
					.getBytes(signEncoding));
			int veriyCode = nss.getLastErrnum();
			if (logger.isDebugEnabled()) {
				logger.debug("verify [" + dataString + "] with sign [" + sign
						+ "].result code [" + veriyCode + "]");
			}
			return veriyCode >= 0;
		} catch (UnsupportedEncodingException e) {
			logger.error("error then verify data", e);
			return false;
		}
	}
}

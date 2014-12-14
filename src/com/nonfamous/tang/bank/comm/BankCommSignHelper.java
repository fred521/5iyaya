package com.nonfamous.tang.bank.comm;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bocom.netpay.b2cAPI.BOCOMB2CClient;
import com.bocom.netpay.b2cAPI.BOCOMSetting;
import com.bocom.netpay.b2cAPI.NetSignServer;

/**
 * 交通银行
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
			if (ret != 0) { // 初始化失败
				throw new IllegalStateException("初始化失败,错误信息："
						+ client.getLastErr());
			}
		} catch (Exception e) {
			logger.debug("error then init BankcommSignHelper", e);
			throw e;
		}
	}

	/**
	 * 对交通银行的提交数据进行签名 TODO:对数据的完整性进行校验
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
			byte bSignMsg[] = nss.NSDetachedSign(merchantDN); // 对订单进行签名
			if (nss.getLastErrnum() < 0) {
				throw new IllegalStateException("ERROR:商户端签名失败");
			}
			return new String(bSignMsg, signEncoding);
		} catch (UnsupportedEncodingException e) {
			logger.error("error then sign data", e);
		}
		return null;
	}

	/**
	 * 对银行返回的数据进行校验
	 * 
	 * @param data
	 * @return 是否正确 true 代码签名正确
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

package com.nonfamous.tang.bank.comm;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.view.velocity.VelocityConfig;

public class BankCommConfig implements ResourceLoaderAware {
	private static Log logger = LogFactory.getLog(BankCommConfig.class);

	private String ApiURL;

	private String OrderURL;

	private String EnableLog;

	private String LogPath;

	private String SettlementFilePath;

	private String MerchantCertFile;

	private String MerchantCertPassword;

	private String RootCertFile;

	private VelocityEngine velocityEngine;

	private String templateName = "bank/comm/config.vm";

	private String vmencoding = "GB18030";

	private String xmlencoding = "gb2312";

	private ResourceLoader resourceLoader;

	public InputStream getConfigStream() throws Exception {
		VelocityContext context = new VelocityContext();
		context.put("cfg", this);
		context.put("encoding", xmlencoding);
		context.put("resourceLoader", resourceLoader);
		StringWriter writer = new StringWriter();
		this.velocityEngine.mergeTemplate(templateName, vmencoding, context,
				writer);
		if (logger.isDebugEnabled()) {
			logger.debug("get config xml :" + writer.toString());
		}
		return new ByteArrayInputStream(writer.toString().getBytes(xmlencoding));
	}

	public String getApiURL() {
		return ApiURL;
	}

	public void setApiURL(String apiURL) {
		ApiURL = apiURL;
	}

	public String getEnableLog() {
		return EnableLog;
	}

	public void setEnableLog(String enableLog) {
		EnableLog = enableLog;
	}

	public String getLogPath() {
		return LogPath;
	}

	public void setLogPath(String logPath) {
		LogPath = logPath;
	}

	public String getMerchantCertFile() {
		return MerchantCertFile;
	}

	public void setMerchantCertFile(String merchantCertFile) {
		MerchantCertFile = merchantCertFile;
	}

	public String getMerchantCertPassword() {
		return MerchantCertPassword;
	}

	public void setMerchantCertPassword(String merchantCertPassword) {
		MerchantCertPassword = merchantCertPassword;
	}

	public String getOrderURL() {
		return OrderURL;
	}

	public void setOrderURL(String orderURL) {
		OrderURL = orderURL;
	}

	public String getRootCertFile() {
		return RootCertFile;
	}

	public void setRootCertFile(String rootCertFile) {
		RootCertFile = rootCertFile;
	}

	public String getSettlementFilePath() {
		return SettlementFilePath;
	}

	public void setSettlementFilePath(String settlementFilePath) {
		SettlementFilePath = settlementFilePath;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public String getVmencoding() {
		return vmencoding;
	}

	public void setVmencoding(String vmencoding) {
		this.vmencoding = vmencoding;
	}

	public String getXmlencoding() {
		return xmlencoding;
	}

	public void setXmlencoding(String xmlencoding) {
		this.xmlencoding = xmlencoding;
	}

	public void setVelocityConfig(VelocityConfig vcf) {
		this.velocityEngine = vcf.getVelocityEngine();
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;

	}
}

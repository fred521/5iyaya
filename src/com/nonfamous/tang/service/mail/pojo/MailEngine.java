package com.nonfamous.tang.service.mail.pojo;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.StringUtils;

import com.nonfamous.tang.domain.mail.MailInfo;
import com.nonfamous.tang.service.mail.IMailEngine;
import com.nonfamous.tang.service.mail.MailException;

/**
 * Class for sending e-mail messages based on Velocity templates or with attachments.
 * 
 * <p>
 * <a href="MailEngine.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Frankiee Wang
 */
public class MailEngine implements IMailEngine {
    protected static final Log logger = LogFactory.getLog(MailEngine.class);

    private JavaMailSender mailSender;

    private VelocityEngine velocityEngine;
    
	private static final String NORMAL_KEY = "default";

	private static final String IMPORTMANT_KEY = "important";

	private Map transports;
   
    private String from = "admin@aiya.com";

    private String encoding = "utf-8";
    
	private String vmencoding = "GB18030";

	private String xmlencoding = "gb2312";

	private ResourceLoader resourceLoader;

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    /**
     * Send a mime message based on a Velocity template.
     * 
     * @param msg
     * @param templateName
     * @param model
     * @throws MailException
     */
    public void sendVelocityMessage(MailInfo info, String templateName, Map model) throws MailException {

        try {

            MimeMessageHelper mmmHelper = new MimeMessageHelper(getMailSender().createMimeMessage(), getEncoding());
            String result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, getEncoding(), model);
            String from = StringUtils.hasText(info.getFrom()) ? info.getFrom() : this.getFrom();
            mmmHelper.setFrom(from);
            mmmHelper.setTo(info.getTo());
            mmmHelper.setSubject(info.getSubject());
            mmmHelper.setText(result, true);
            mailSender.send(mmmHelper.getMimeMessage());
        } catch (VelocityException e) {
            logger.error(e.getMessage());
            throw new MailException(e);

        } catch (MessagingException e) {
            logger.error(e.getMessage());
            throw new MailException(e);

        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            throw new MailException(e);
        }
    }

    public void sendMessage(MailInfo info) throws MailException {
        try {

            MimeMessageHelper mmmHelper = new MimeMessageHelper(getMailSender().createMimeMessage(), getEncoding());
            String from = StringUtils.hasText(info.getFrom()) ? info.getFrom() : this.getFrom();
            mmmHelper.setFrom(from);
            mmmHelper.setTo(info.getTo());
            mmmHelper.setSubject(info.getSubject());
            mmmHelper.setText(info.getContent(), true);
            mailSender.send(mmmHelper.getMimeMessage());
        } catch (MessagingException e) {
            logger.error(e.getMessage());
            throw new MailException(e);

        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            throw new MailException(e);
        }
    }
	
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
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

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public Map getTransports() {
		return transports;
	}

	public void setTransports(Map transports) {
		this.transports = transports;
	}

	public void sendImportantAttach(MailInfo info, String templateID,
			Map model, File[] attachFiles, String[] fileNames)
			throws MailException {
		send(info,templateID,model, attachFiles, fileNames, true);
		
	}

	public void sendImportmant(MailInfo info, String templateID, Map model)
			throws MailException {
		send(info,templateID, model, true);
		
	}

	public void sendNormalAttatch(MailInfo info, String templateID, Map model,
			File[] attachFiles, String[] fileNames) throws MailException {
		send(info, templateID, model, attachFiles, fileNames,false);
		
	}

	

	public void sendNorml(MailInfo info, String templateID, Map model)
			throws MailException {
		send(info,templateID, model, false);
		
	}
	/**send normal or important email with attachment**/
	/**
	 * 发送邮件(带附件)
	 * 
	 * @param templateID/templateName
	 *            邮件模板ID
	 * @param Address
	 *            发送人地址
	 * @param Address
	 *            接收人地址
	 * @param replyToAddress
	 *            回复地址
	 * @param subject
	 *            主题
	 * @param mailAttrs
	 *            模板解析内容
	 * @param attachFiles
	 *            邮件发送的附件的文件
	 * @param fileNames
	 *            发送附件时显示的文件名称
	 * @param important
	 *            是否重要邮件
	 * @throws MailException
	 */
	
    

	private void send(MailInfo info, String templateID, Map model,
			File[] attachFiles, String[] fileNames, boolean important) {
		String transportID = null;
		if (important) {
			transportID = (String) transports.get(IMPORTMANT_KEY);
		} else {
			transportID = (String) transports.get(NORMAL_KEY);
		}

		/*MailTransport mailTransport = (transportID == null) ? mailService
				.getMailTransport() : mailService.getMailTransport(transportID);

		mailTransport.send(templateID, new DefaultMailTransportHandler() {
			public void prepareMessage(MailBuilder builder)
					throws MailException {

				if (StringUtil.isNotBlank(fromAddress)) {
					builder.setAddress(MailAddressType.FROM, fromAddress);
				}

				builder.addAddress(MailAddressType.TO, toAddress);

				if (StringUtil.isNotBlank(replyToAddress)) {
					builder
							.setAddress(MailAddressType.REPLY_TO,
									replyToAddress);
				}

				if (StringUtil.isNotBlank(subject)) {
					builder.setSubject(subject);
				}
				builder.setAttributes(mailAttrs);

				// 添加附件信息
				if (attachFiles != null && attachFiles.length > 0) {
					// 获取content
					MixedMultipartContent content = (MixedMultipartContent) builder
							.getContent();
					AttachmentContent fileContent = null;
					// 逐个添加附件content
					for (int i = 0; i < attachFiles.length; i++) {
						if (fileNames != null && i < fileNames.length
								&& StringUtil.isNotBlank(fileNames[i])) {
							fileContent = new AttachmentContent(attachFiles[i],
									fileNames[i]);
						} else {
							fileContent = new AttachmentContent(attachFiles[i]);
						}
						fileContent.setMailBuilder(builder);
						content.addContent(fileContent);
					}
				}
			}
		});*/
		
	}
	/**send normal or important email without attachment**/
	/**
	 * 发送邮件
	 * 
	 * @param templateID/templateName
	 *            邮件模板ID
	 * @param fromAddress
	 *            发送人地址
	 * @param toAddress
	 *            接收人地址
	 * @param replyToAddress
	 *            回复地址
	 * @param subject
	 *            主题
	 * @param mailAttrs
	 *            模板解析内容
	 * @param importmant
	 *            是否重要邮件
	 * @throws MailException
	 */
	/**
	 * 发送带附件的普通邮件<code>注意：模板配置时<alternative-content>要改成<mixed-content></code>
	 * 
	 * @param templateID
	 *            邮件模板ID
	 * @param fromAddress
	 *            发送人地址
	 * @param toAddress
	 *            接收人地址
	 * @param replyToAddress
	 *            回复地址
	 * @param subject
	 *            主题
	 * @param mailAttrs
	 *            模板解析内容
	 * @param attachFiles
	 *            邮件发送的附件的文件
	 * @param fileNames
	 *            发送附件时显示的文件名称
	 * @throws MailException
	 */

	/**
	 * 发送带附件的重要邮件<code>注意：模板配置时<alternative-content>要改成<mixed-content></code>
	 * 
	 * @param templateID
	 *            邮件模板ID
	 * @param fromAddress
	 *            发送人地址
	 * @param toAddress
	 *            接收人地址
	 * @param replyToAddress
	 *            回复地址
	 * @param subject
	 *            主题
	 * @param mailAttrs
	 *            模板解析内容
	 * @param attachFiles
	 *            邮件发送的附件的文件
	 * @param fileNames
	 *            发送附件时显示的文件名称
	 * @throws MailException
	 */

	private void send(MailInfo info, String templateName, Map model,boolean important)
	{
        try {

            MimeMessageHelper mmmHelper = new MimeMessageHelper(getMailSender().createMimeMessage(), getEncoding());
            String result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, model);
            String from = StringUtils.hasText(info.getFrom()) ? info.getFrom() : this.getFrom();

            mmmHelper.setFrom(from);
            mmmHelper.setTo(info.getTo());
            mmmHelper.setSubject(info.getSubject());
            mmmHelper.setText(result, true);
            mailSender.send(mmmHelper.getMimeMessage());

        } catch (VelocityException e) {
            logger.error(e.getMessage());
            throw new MailException(e);

        } catch (MessagingException e) {
            logger.error(e.getMessage());
            throw new MailException(e);

        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            throw new MailException(e);
        }
	}
}

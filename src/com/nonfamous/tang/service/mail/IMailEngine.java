package com.nonfamous.tang.service.mail;

import java.io.File;
import java.util.Map;

import com.nonfamous.tang.domain.mail.MailInfo;

public interface IMailEngine {

	/**
	 * Send a mime message based on a Velocity template.
	 * 
	 * @param msg
	 * @param templateName
	 * @param model
	 * @throws MailException
	 */
	void sendVelocityMessage(MailInfo info, String templateName, Map model)
			throws MailException;

	/**
	 * send a mime message
	 * 
	 * @param info
	 * @throws MailException
	 */
	void sendMessage(MailInfo info) throws MailException;

	/**
	 * 发送普通邮件
	 * 
	 * @param info
	 * @param templateID
	 * @param model
	 * @throws MailException
	 */

	public void sendNorml(MailInfo info, String templateID, Map model)
			throws MailException;

	/**
	 * 发送重要邮件
	 * 
	 * @param info
	 * @param templateID
	 * @param model
	 * @throws MailException
	 */
	public void sendImportmant(MailInfo info, String templateID, Map model)
			throws MailException;

	/**
	 * 发送带附件的普通邮件<code>注意：</code>
	 * 
	 * @param templateID
	 *            邮件模板ID
	 * @param model
	 *            模板解析内容
	 * @param attachFiles
	 *            邮件发送的附件的文件
	 * @param fileNames
	 *            发送附件时显示的文件名称
	 * @throws MailException
	 */
	
	public void sendNormalAttatch(MailInfo info, String templateID, Map model, File[] attachFiles, String[] fileNames) throws MailException;


	/**
	 * 发送带附件的重要邮件<code>注意：模板配置时<alternative-content>要改成<mixed-content></code>
	 * 
	 * @param templateID
	 *            邮件模板ID
	 * @param model
	 *            模板解析内容
	 * @param attachFiles
	 *            邮件发送的附件的文件
	 * @param fileNames
	 *            发送附件时显示的文件名称
	 * @throws MailException
	 */
	public void sendImportantAttach(MailInfo info,String templateID, Map model,File[] attachFiles, String[] fileNames )throws MailException;
}

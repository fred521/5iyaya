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
	 * ������ͨ�ʼ�
	 * 
	 * @param info
	 * @param templateID
	 * @param model
	 * @throws MailException
	 */

	public void sendNorml(MailInfo info, String templateID, Map model)
			throws MailException;

	/**
	 * ������Ҫ�ʼ�
	 * 
	 * @param info
	 * @param templateID
	 * @param model
	 * @throws MailException
	 */
	public void sendImportmant(MailInfo info, String templateID, Map model)
			throws MailException;

	/**
	 * ���ʹ���������ͨ�ʼ�<code>ע�⣺</code>
	 * 
	 * @param templateID
	 *            �ʼ�ģ��ID
	 * @param model
	 *            ģ���������
	 * @param attachFiles
	 *            �ʼ����͵ĸ������ļ�
	 * @param fileNames
	 *            ���͸���ʱ��ʾ���ļ�����
	 * @throws MailException
	 */
	
	public void sendNormalAttatch(MailInfo info, String templateID, Map model, File[] attachFiles, String[] fileNames) throws MailException;


	/**
	 * ���ʹ���������Ҫ�ʼ�<code>ע�⣺ģ������ʱ<alternative-content>Ҫ�ĳ�<mixed-content></code>
	 * 
	 * @param templateID
	 *            �ʼ�ģ��ID
	 * @param model
	 *            ģ���������
	 * @param attachFiles
	 *            �ʼ����͵ĸ������ļ�
	 * @param fileNames
	 *            ���͸���ʱ��ʾ���ļ�����
	 * @throws MailException
	 */
	public void sendImportantAttach(MailInfo info,String templateID, Map model,File[] attachFiles, String[] fileNames )throws MailException;
}

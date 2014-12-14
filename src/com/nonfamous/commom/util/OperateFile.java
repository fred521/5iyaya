package com.nonfamous.commom.util;


/**************************************************************************
 * Copyright (C) 2007 ���ݱ�ͨ�Ƽ����޹�˾ ��������Ȩ��
 * 
 * ��ϵͳ���������,δ����Ȩ���Ը��ƻ򴫲�������Ĳ��ֻ�ȫ�����ǷǷ���
 *  
 * bizyi Project
 * 
 * @author:�� �� ��
 * @version:CVS $Revision: 1.1 $ $Date: 2008/07/11 00:47:11 $ 
 *
 * Module:  OperateFile.java
 * Purpose: ��ȡ�����ĸ���ʱ�� 
 * History
 * Date               Author             Description 
 * 2007-11-27         ������              �����ļ�
 * 2007-11-27         ������ 	        ���� ��������ʱ��Ĵ�ȡ ����
 **************************************************************************/

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OperateFile {

	private static final Log logger = LogFactory.getLog(OperateFile.class);
	
	/**
	 * ��������:���������ĸ���ʱ�䵽�ļ���
	 * @param file				�ļ�·��
	 * @param date				��������ʱ��
	 * @author �� �� ��
	 */
	public static void setTimestamp(File file,Date date)
	{
		RandomAccessFile raf=null;
		try{
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			byte[] buf = time.getBytes();
			raf = new RandomAccessFile(file,"rw");   

	        FileChannel fc = raf.getChannel();  
	        FileLock fl = fc.tryLock();
	        raf.write(buf);
		}
		catch (IOException e) {
			logger.error("������������ʱ��ʧ�ܣ�"+e.getMessage());
		}
		finally
		{
			if(raf != null)
			{
				try {
					raf.close();
				} catch (IOException e) {
					logger.error("�ر��ļ�ʧ�ܣ�"+e.getMessage());
				}
			}
		}
	}
	
	/**
	 * ��������:���ļ��ж�ȡ�������ϴθ���ʱ��
	 * @param file			�ļ�·��
	 * @return				�������ϴθ���ʱ��
	 * @author �� �� ��
	 */
	public static Date getTimestamp(File file)
	{
		RandomAccessFile raf = null;
		Date date = null;
		try
		{
			raf = new RandomAccessFile(file,"rw");
			FileChannel fc = raf.getChannel();  
	        FileLock fl = fc.tryLock();
	        byte[] buf=new byte[50];
			String time=null;
			int n;
			while((n=raf.read(buf))!=-1)
			{
				time=new String(buf,0,n);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			 date= sdf.parse(time);
		}
		catch (IOException e) {
			logger.error("��ȡ�����ϴθ���ʱ��ʧ�ܣ�"+e.getMessage());
		}
		catch (ParseException e) {
			logger.error("ת��ʱ��ʧ�ܣ�"+e.getMessage());
		}
		finally
		{
			if(raf != null)
			{
				try {
					raf.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("�ر��ļ�ʧ�ܣ�"+e.getMessage());
					return date;
				}
			}
			return date;
		}
	}
	
}

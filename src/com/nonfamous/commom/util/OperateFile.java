package com.nonfamous.commom.util;


/**************************************************************************
 * Copyright (C) 2007 杭州贝通科技有限公司 保留所有权利
 * 
 * 本系统是商用软件,未经授权擅自复制或传播本程序的部分或全部将是非法的
 *  
 * bizyi Project
 * 
 * @author:王 熙 斌
 * @version:CVS $Revision: 1.1 $ $Date: 2008/07/11 00:47:11 $ 
 *
 * Module:  OperateFile.java
 * Purpose: 存取索引的更新时间 
 * History
 * Date               Author             Description 
 * 2007-11-27         王熙斌              创建文件
 * 2007-11-27         王熙斌 	        增加 索引更新时间的存取 功能
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
	 * 功能描述:保存索引的更新时间到文件中
	 * @param file				文件路径
	 * @param date				索引更新时间
	 * @author 王 熙 斌
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
			logger.error("保存索引更新时间失败："+e.getMessage());
		}
		finally
		{
			if(raf != null)
			{
				try {
					raf.close();
				} catch (IOException e) {
					logger.error("关闭文件失败："+e.getMessage());
				}
			}
		}
	}
	
	/**
	 * 功能描述:从文件中读取索引的上次更新时间
	 * @param file			文件路径
	 * @return				索引的上次更新时间
	 * @author 王 熙 斌
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
			logger.error("读取索引上次更新时间失败："+e.getMessage());
		}
		catch (ParseException e) {
			logger.error("转换时间失败："+e.getMessage());
		}
		finally
		{
			if(raf != null)
			{
				try {
					raf.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("关闭文件失败："+e.getMessage());
					return date;
				}
			}
			return date;
		}
	}
	
}

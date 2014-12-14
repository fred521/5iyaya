package com.nonfamous.tang.service.upload;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: fred
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: UploadService.java,v 1.3 2009/04/12 09:09:00 jason Exp $
 */
public interface UploadService {

	/**
	 * Ĭ�Ͻ��ļ��ϴ�����Ŀ¼��o
	 * 
	 * @param multipartFile
	 * @return
	 * @throws UploadFileException
	 */
	public String uploadFile(MultipartFile multipartFile)
			throws UploadFileException;

	/**
	 * ����ָ�����ļ��Ŀ�ȡ����Ƚ����ļ�ѹ����ѹ��ͼƬĬ����.n.jpg��β
	 * ��ԭʼ�ϴ�ͼƬ����Ϊ1233455.jpg��ѹ��ͼƬΪ1233455.jpg.n.jpg
	 * 
	 * @param filePath
	 *            һ��ָ�ϴ��󷵻ص�·�������ݿ��д洢��·��
	 * @param width
	 * @param height
	 * @throws UploadFileException
	 */
	public void imageCompress(String filePath, int width, int height)
			throws UploadFileException;

	/**
	 * �ϴ�����ͼƬ������ѹ�������ļ���ŵ�ָ����sĿ¼��
	 * 
	 * @param multipartFile
	 * @return
	 * @throws UploadFileException
	 */
	public String uploadShopImage(MultipartFile multipartFile)
			throws UploadFileException;

	/**
	 * �ϴ��������ӵ�ͼƬ��ѹ�����ϴ���ָ����Ŀ¼u����
	 * ��ԭʼ�ϴ�ͼƬ����Ϊ1233455.jpg��ѹ��ͼƬΪ1233455.jpg.l.jpg(210*188)
	 * 1233455.jpg.s.jpg��80*80��
	 * @param multipartFile
	 * @return
	 * @throws UploadFileException
	 */
	public String uploadURLImageWithCompress(MultipartFile multipartFile)
			throws UploadFileException;
	
	/**
	 * �ϴ���ƷͼƬ�����ļ����õ�ָ����gĿ¼��
	 * @param multipartFile
	 * @return
	 * @throws UploadFileException
	 */
	public String uploadGoodsImage(MultipartFile multipartFile)
			throws UploadFileException;

	/**
	 * �ϴ���ƷͼƬ��ѹ�����ϴ���ָ����Ŀ¼g����
	 * ��ԭʼ�ϴ�ͼƬ����Ϊ1233455.jpg��ѹ��ͼƬΪ1233455.jpg.l.jpg(210*188)
	 * 1233455.jpg.s.jpg��80*80��
	 * @param multipartFile
	 * @return
	 * @throws UploadFileException
	 */
	public String uploadGoodsImageWithCompress(MultipartFile multipartFile)
			throws UploadFileException;

	/**
	 * �ϴ�����ͼƬ��ѹ�����ϴ���ָ����Ŀ¼s����
	 * ��ԭʼ�ϴ�ͼƬ����Ϊ1233455.jpg��ѹ��ͼƬΪ1233455.jpg.l.jpg(219*221)
	 * @return
	 * @throws UploadFileException
	 */
	public String uploadShopImageWithCompress(MultipartFile multipartFile)
			throws UploadFileException;
	
	/**
	 * ɾ���ļ�
	 * @param filePath
	 */
	public void deleteImageFile(String filePath);
}

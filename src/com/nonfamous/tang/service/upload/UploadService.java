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
	 * 默认将文件上传其他目录下o
	 * 
	 * @param multipartFile
	 * @return
	 * @throws UploadFileException
	 */
	public String uploadFile(MultipartFile multipartFile)
			throws UploadFileException;

	/**
	 * 按照指定的文件的宽度、长度进行文件压缩、压缩图片默认以.n.jpg结尾
	 * 如原始上传图片名称为1233455.jpg则压缩图片为1233455.jpg.n.jpg
	 * 
	 * @param filePath
	 *            一般指上传后返回的路径或数据库中存储的路径
	 * @param width
	 * @param height
	 * @throws UploadFileException
	 */
	public void imageCompress(String filePath, int width, int height)
			throws UploadFileException;

	/**
	 * 上传店铺图片，但不压缩，将文件存放到指定的s目录下
	 * 
	 * @param multipartFile
	 * @return
	 * @throws UploadFileException
	 */
	public String uploadShopImage(MultipartFile multipartFile)
			throws UploadFileException;

	/**
	 * 上传友情链接的图片并压缩，上传到指定的目录u下面
	 * 如原始上传图片名称为1233455.jpg则压缩图片为1233455.jpg.l.jpg(210*188)
	 * 1233455.jpg.s.jpg（80*80）
	 * @param multipartFile
	 * @return
	 * @throws UploadFileException
	 */
	public String uploadURLImageWithCompress(MultipartFile multipartFile)
			throws UploadFileException;
	
	/**
	 * 上传商品图片，将文件放置到指定的g目录下
	 * @param multipartFile
	 * @return
	 * @throws UploadFileException
	 */
	public String uploadGoodsImage(MultipartFile multipartFile)
			throws UploadFileException;

	/**
	 * 上传商品图片并压缩，上传到指定的目录g下面
	 * 如原始上传图片名称为1233455.jpg则压缩图片为1233455.jpg.l.jpg(210*188)
	 * 1233455.jpg.s.jpg（80*80）
	 * @param multipartFile
	 * @return
	 * @throws UploadFileException
	 */
	public String uploadGoodsImageWithCompress(MultipartFile multipartFile)
			throws UploadFileException;

	/**
	 * 上传店铺图片并压缩，上传到指定的目录s下面
	 * 如原始上传图片名称为1233455.jpg则压缩图片为1233455.jpg.l.jpg(219*221)
	 * @return
	 * @throws UploadFileException
	 */
	public String uploadShopImageWithCompress(MultipartFile multipartFile)
			throws UploadFileException;
	
	/**
	 * 删除文件
	 * @param filePath
	 */
	public void deleteImageFile(String filePath);
}

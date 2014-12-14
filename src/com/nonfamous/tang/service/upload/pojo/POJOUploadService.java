package com.nonfamous.tang.service.upload.pojo;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.nonfamous.commom.util.DateUtils;
import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.UUIDGenerator;
import com.nonfamous.commom.util.service.POJOServiceBase;
import com.nonfamous.tang.service.upload.UploadFileException;
import com.nonfamous.tang.service.upload.UploadService;

/**
 * @author: fred
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: POJOUploadService.java,v 1.3 2009/04/12 09:10:11 jason Exp $
 */
public class POJOUploadService extends POJOServiceBase implements UploadService {

	private String rootPath = null;

	private long maxSize = 0;

	private static final String FILE_SEPARATOR = "/";

	private static final String OTHER_PATH = FILE_SEPARATOR + "o"
			+ FILE_SEPARATOR;

	private static final String GOODS_PATH = FILE_SEPARATOR + "g"
			+ FILE_SEPARATOR;

	private static final String SHOP_PATH = FILE_SEPARATOR + "s"
			+ FILE_SEPARATOR;

	private static final String URL_PIC_PATH = FILE_SEPARATOR + "u"
	+ FILE_SEPARATOR;
	
	private static final String LARGE_IMAGE_SUFFIX = ".l.jpg";

	private static final String SMALL_IMAGE_SUFFIX = ".s.jpg";

	private static final String NORMAL_IMAGE_SUFFIX = ".n.jpg";

	public void init() {
		logger.info("init POJOUploadService start.................");
		if (StringUtils.isBlank(rootPath)) {
			throw new NullPointerException("rootPath can't be null");
		}
		File f = new File(getGoodsPhisicalPath());
		if (!f.exists()) {
			f.mkdirs();
		}
		f = new File(getShopPhysicalPath());
		if (!f.exists()) {
			f.mkdirs();
		}
		f = new File(getOtherPhysicalPath());
		if (!f.exists()) {
			f.mkdirs();
		}
		logger.info("init POJOUploadService end...................");
	}

	public String uploadFile(MultipartFile multipartFile)
			throws UploadFileException {
		return saveFile(multipartFile, getOtherPhysicalPath(), OTHER_PATH);
	}

	public void imageCompress(String filePath, int width, int height)
			throws UploadFileException {
		if (!ImageProcessor.compressImage(rootPath + filePath,
				NORMAL_IMAGE_SUFFIX, width, height)) {
			throw new UploadFileException("ѹ���ļ�ʧ��");
		}
	}

	public String uploadShopImage(MultipartFile multipartFile)
			throws UploadFileException {
		return saveFile(multipartFile, getShopPhysicalPath(), SHOP_PATH);
	}

	public String uploadShopImageWithCompress(MultipartFile multipartFile)
			throws UploadFileException {
		String path = saveFile(multipartFile, getShopPhysicalPath(), SHOP_PATH);
		if (!ImageProcessor.compressImage(rootPath + path,
				LARGE_IMAGE_SUFFIX, 219, 221)) {
			throw new UploadFileException("ѹ���ļ�ʧ��");
		}
		return path;
	}

	public String uploadGoodsImage(MultipartFile multipartFile)
			throws UploadFileException {
		return saveFile(multipartFile, getGoodsPhisicalPath(), GOODS_PATH);
	}

	public String uploadGoodsImageWithCompress(MultipartFile multipartFile)
			throws UploadFileException {
		String path = saveFile(multipartFile, getGoodsPhisicalPath(),
				GOODS_PATH);
		if (!ImageProcessor.compressImage(rootPath + path,
				LARGE_IMAGE_SUFFIX, 210, 188)) {
			throw new UploadFileException("ѹ���ļ�ʧ��");
		}
		if (!ImageProcessor.compressImage(rootPath + path,
				SMALL_IMAGE_SUFFIX, 80, 80)) {
			throw new UploadFileException("ѹ���ļ�ʧ��");
		}
		return path;
	}

	public String uploadURLImageWithCompress(MultipartFile multipartFile)
	throws UploadFileException {
	
	String path = saveFile(multipartFile, getURLPicPhisicalPath(), URL_PIC_PATH);
	if (!ImageProcessor.compressImage(rootPath + path, LARGE_IMAGE_SUFFIX,
			210, 188)) {
		throw new UploadFileException("ѹ���ļ�ʧ��");
	}
	if (!ImageProcessor.compressImage(rootPath + path, SMALL_IMAGE_SUFFIX,
			80, 80)) {
		throw new UploadFileException("ѹ���ļ�ʧ��");
	}
	return path;
}
	
	public void deleteImageFile(String filePath) {
		File f = new File(rootPath + filePath);
		if (f.exists()) {
			f.delete();
		}
		f = new File(rootPath + filePath + NORMAL_IMAGE_SUFFIX);
		if (f.exists()) {
			f.delete();
		}
		f = new File(rootPath + filePath + LARGE_IMAGE_SUFFIX);
		if (f.exists()) {
			f.delete();
		}
		f = new File(rootPath + filePath + SMALL_IMAGE_SUFFIX);
		if (f.exists()) {
			f.delete();
		}

	}

	/**
	 * �����ϴ��ļ����ļ����·��
	 * 
	 * @param multipartFile
	 * @return
	 */
	private StringBuffer genratorUploadFileName(MultipartFile multipartFile) {
		StringBuffer uploadFileName = new StringBuffer(DateUtils
				.getYearMonWithoutDot(new Date(), 0));
		uploadFileName.append(FILE_SEPARATOR);
		uploadFileName.append(UUIDGenerator.generate());
		uploadFileName
				.append(getFileSuffix(multipartFile.getOriginalFilename()));
		return uploadFileName;
	}

	/**
	 * ����ϴ��ļ��Ĵ�С���Ƿ�Ϊ�գ����Խ�һ�������������ļ��ϴ��ĺ�׺�ж�
	 * 
	 * @param multipartFile
	 * @throws UploadFileException
	 */
	private void checkUploadFile(MultipartFile multipartFile)
			throws UploadFileException {
		if (multipartFile == null || multipartFile.getSize() == 0) {
			throw new UploadFileException("�ϴ��ļ�����Ϊ��");
		}
		if (multipartFile.getSize() > maxSize) {
			throw new UploadFileException("�ϴ��ļ����ܳ��� " + maxSize / 1024 + " k");
		}
		//�õ�upload�ļ���
		String fileName=multipartFile.getOriginalFilename();
		//ȡ�ú�׺��
		String ext = getFileSuffix(fileName);
		//����׺��ȫͳһ��Сд�������ִ�Сд	    
		ext = ext.toLowerCase(); 
		if(!ext.equals(".jpg")&&!ext.equals(".jpeg")&&!ext.equals(".gif"))
		{
			throw new UploadFileException(" ��ʹ��GIF��JPG��ʽ��ͼƬ!");
		}
	}

	/**
	 * ����ָ��·�������ļ�
	 * 
	 * @param multipartFile
	 * @param physiclePath
	 * @return
	 * @throws UploadFileException
	 */
	private String saveFile(MultipartFile multipartFile, String physiclePath,
			String pathPrefix) throws UploadFileException {
		checkUploadFile(multipartFile);
		StringBuffer uploadFileName = genratorUploadFileName(multipartFile);
		try {
			File f = new File(physiclePath + uploadFileName.toString());
			if (!f.exists()) {
				f.mkdirs();
			}
			multipartFile.transferTo(f);
		} catch (IllegalStateException e) {
			logger.error("�����ļ�ʧ��", e);
			throw new UploadFileException("�����ļ�ʧ��");
		} catch (IOException e) {
			logger.error("�����ļ�ʧ��", e);
			throw new UploadFileException("�����ļ�ʧ��");
		}

		return uploadFileName.insert(0, pathPrefix).toString();
	}
	
	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}

	public void setRootPath(String rootPath) {
		if (StringUtils.right(rootPath, 1).equals(FILE_SEPARATOR)) {
			this.rootPath = StringUtils.substring(rootPath, 0, rootPath
					.length() - 1);
		} else {
			this.rootPath = rootPath;
		}
	}

	private String getGoodsPhisicalPath() {
		return new StringBuffer(rootPath).append(GOODS_PATH).toString();
	}

	private String getURLPicPhisicalPath() {
		return new StringBuffer(rootPath).append(URL_PIC_PATH).toString();
	}
	
	private String getOtherPhysicalPath() {
		return new StringBuffer(rootPath).append(OTHER_PATH).toString();
	}

	private String getShopPhysicalPath() {
		return new StringBuffer(rootPath).append(SHOP_PATH).toString();
	}

	private String getFileSuffix(String fileName) {
		return StringUtils.substring(fileName, StringUtils.lastIndexOf(
				fileName, "."));
	}
}

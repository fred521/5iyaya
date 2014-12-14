package com.nonfamous.commom.util.image.pojo;

import java.io.File;
import java.io.IOException;

import magick.CompressionType;
import magick.DrawInfo;
import magick.GravityType;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import magick.PixelPacket;
import magick.PreviewType;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.image.ImageService;

public class JMagickImageService implements ImageService {
	protected static final Log logger = LogFactory
			.getLog(JMagickImageService.class);

	static {
		System.getProperties().setProperty("jmagick.systemclassloader", "no");
	}

	private ImageInfo imageInfo;

	public JMagickImageService() throws MagickException {
		super();
		imageInfo = new ImageInfo();
	}

	public void compress(File orignalFile, File targetFile, int width,
			int height) throws IOException {
		if (orignalFile == null) {
			throw new NullPointerException("orignal file can't be null");
		}
		if (!orignalFile.exists()) {
			throw new IOException("orignal file is not exists");
		}
		if (targetFile == null) {
			throw new NullPointerException("target file can't be null");
		}
		byte[] blob = getBytes(orignalFile);
		compress(blob, targetFile, width, height);
	}

	private byte[] getBytes(File orignalFile) throws IOException {
		return FileUtils.readFileToByteArray(orignalFile);
	}

	public void compress(byte[] orignalImage, File targetFile, int width,
			int height) throws IOException {
		if (orignalImage == null) {
			throw new NullPointerException("orignal file can't be null");
		}
		if (targetFile == null) {
			throw new NullPointerException("target file can't be null");
		}
		try {
			MagickImage image = new MagickImage(imageInfo, orignalImage);
			MagickImage scaled = image.scaleImage(width, height);// 小图片文件的大小.
			byte[] targetBlob = scaled.imageToBlob(imageInfo);
			saveImageToFile(targetBlob, targetFile);
		} catch (MagickException e) {
			if (logger.isErrorEnabled()) {
				logger.error("error then compress image", e);
			}
			throw new IOException(e.getLocalizedMessage());
		}
	}
	
	public void saveImageToFile(byte[] orignalImage, File targetFile)
			throws IOException {
		if (orignalImage == null) {
			throw new NullPointerException("orignal file can't be null");
		}
		if (targetFile == null) {
			throw new NullPointerException("target file can't be null");
		}
		FileUtils.writeByteArrayToFile(targetFile, orignalImage);
	}

	public void addWatermark2Image(File originalImageFile, String text, File targetFile)
			throws IOException {
		if (originalImageFile == null) {
			throw new NullPointerException("orignal file can't be null");
		}
		if (targetFile == null) {
			throw new NullPointerException("target file can't be null");
		}
		
		try{
			byte[] originalImage = getBytes(originalImageFile);
			MagickImage image = new MagickImage(imageInfo, originalImage);
			
			String orgImg = originalImageFile.getAbsolutePath();
			if (orgImg.toUpperCase().endsWith("JPG")
					|| orgImg.toUpperCase().endsWith("JPEG")) {
				imageInfo.setCompression(CompressionType.JPEGCompression); // 压缩类别为JPEG格式
				imageInfo.setPreviewType(PreviewType.JPEGPreview); // 预览格式为JPEG格式
				imageInfo.setQuality(95);
			}
			
			DrawInfo drawInfo = new DrawInfo(imageInfo);
			drawInfo.setFill(PixelPacket.queryColorDatabase("pink"));
//			drawInfo.setUnderColor(PixelPacket.queryColorDatabase("grey"));
			drawInfo.setOpacity(80);
			drawInfo.setPointsize(20);
//			drawInfo.setFont(fontPath); //if need encoding
			drawInfo.setTextAntialias(true);
			
			// Step 3: Writing The Text
			drawInfo.setText(text);
			drawInfo.setGravity(GravityType.SouthEastGravity);
			image.annotateImage(drawInfo);

			byte[] targetImage = image.imageToBlob(imageInfo);
			saveImageToFile(targetImage, targetFile);
		}
		catch(MagickException e){
			logger.error("error when invoke method[addWatermark2Image]", e);
			throw new IOException(e.getLocalizedMessage());
		}
	}

	private String fontPath;

	public String getFontPath() {
		if(SystemUtils.IS_OS_WINDOWS){
			this.fontPath = "C:\\WINDOWS\\Fonts\\simsun.ttc";
		}
		else if(SystemUtils.IS_OS_UNIX || SystemUtils.IS_OS_LINUX){
			this.fontPath = "";
		}
		return StringUtils.isBlank(fontPath) ? "" : fontPath;
	}

	public void setFontPath(String fontPath) {
		this.fontPath = fontPath;
	}
	
}

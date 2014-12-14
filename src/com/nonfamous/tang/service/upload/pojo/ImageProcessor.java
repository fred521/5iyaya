package com.nonfamous.tang.service.upload.pojo;

import java.awt.Rectangle;

import magick.CompositeOperator;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import magick.PixelPacket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author: fred
 * 
 *          <pre>
 *          图片文件压缩类，采用第三方的压缩工具jmagic。使用这个工具再windows下需要按照一个
 *          第三方工具，然后将jmagic这个dll放到system32下面即可；如果是再linux下面则需要
 *          将jmagic.so放置到当前用户的环境变量下面
 * </pre>
 * 
 * @version $Id: ImageProcessor.java,v 1.2 2008/11/29 02:53:47 fred Exp $
 */
public class ImageProcessor {
	protected static final Log log = LogFactory.getLog(ImageProcessor.class);

	private static final int IMAGE_SUM_SCALE_LIMIT = 160;

	static {
		System.getProperties().setProperty("jmagick.systemclassloader", "no");
	}

	/**
	 * 按照固定的大小进行压缩
	 * 
	 * @param orgImg
	 *            原是图片路径
	 * @param compressFileSuffix
	 *            压缩图片后缀，以dot开始
	 * @param width
	 *            宽度
	 * @return
	 */
	public static boolean compressImage(String orgImg,
			String compressFileSuffix, int width, int height) {
		boolean result = false;
		try {

			MagickImage image = new MagickImage(new ImageInfo(orgImg));

			// 目标图片尺寸，包括边界
			int targetWidth = width > 0 ? width : IMAGE_SUM_SCALE_LIMIT;
			int targetHeight = height > 0 ? height : IMAGE_SUM_SCALE_LIMIT;

			// 原始尺寸
			int orgWidth = image.getDimension().width;
			int orgHeight = image.getDimension().height;

			// 初始化等比例缩放后尺寸
			int newWidth = targetWidth;
			int newHeight = targetHeight;

			// 调整缩放后的尺寸
			if (orgWidth * targetHeight > orgHeight * targetWidth) {
				// 缩放后的宽度等于目标宽度，高度小于目标高度
				newHeight = (orgHeight * newWidth) / orgWidth;
				// 调整差距高度为偶数像素
				newHeight = targetHeight - (targetHeight - newHeight) / 2 * 2;
			} else {
				// 缩放后的高度等于目标高度，宽度小于目标宽度
				newWidth = (orgWidth * newHeight) / orgHeight;
				// 调整差距宽度为偶数像素
				newWidth = targetWidth - (targetWidth - newWidth) / 2 * 2;
			}

			// 根据调整后的尺寸参数进行图片压缩，保证等比例压缩，小图片可能会压大哦
			MagickImage small = image.scaleImage(newWidth, newHeight);

			// 调整边界，将图片调整到目标尺寸
			int borderX = 0;
			int borderY = 0;

			if (newWidth < targetWidth) {
				borderX = (targetWidth - newWidth) / 2;
			} else if (newHeight < targetHeight) {
				borderY = (targetHeight - newHeight) / 2;
			}

			Rectangle rect = new Rectangle();
			rect.setSize(borderX, borderY);
			// 设置填充边界颜色
			small.setBorderColor(new PixelPacket(-255, -255, -255, 0));
			small = small.borderImage(rect);

			small.setFileName(orgImg + compressFileSuffix);
			small.writeImage(new ImageInfo());
			small.destroyImages();

			result = true;
		} catch (Exception e1) {
			String errorMessage = " [genearateSumImg] unsupported JPEG variation format "
					+ " Big Picture=" + orgImg;
			log.error(errorMessage, e1);
		}
		return result;
	}

	/**
	 * 按照宽度进行压缩
	 * 
	 * @param orgImg
	 *            原是图片路径
	 * @param compressFileSuffix
	 *            压缩图片后缀，已dot开始
	 * @param width
	 *            宽度
	 * @return
	 */
	public static boolean compressImage(String orgImg,
			String compressFileSuffix, int width) {
		boolean result = false;

		try {
			System.getProperties().setProperty("jmagick.systemclassloader",
					"no");
			MagickImage image = new MagickImage(new ImageInfo(orgImg));

			// 原始尺寸
			int beforeScaleX = image.getDimension().width;
			int beforeScaleY = image.getDimension().height;

			double pro = (double) beforeScaleX / width;
			int scaley = (int) ((double) beforeScaleY / pro);

			// 直接按照输入的参数进行图片压缩，小图片可能会压大哦
			MagickImage small = image.scaleImage(width, scaley);
			small.setFileName(orgImg + compressFileSuffix);
			small.writeImage(new ImageInfo());
			small.destroyImages();

			result = true;
		} catch (Exception e1) {
			String errorMessage = " [genearateSumImg] unsupported JPEG variation format "
					+ " Big Picture=" + orgImg;
			log.error(errorMessage, e1);
		}
		return result;
	}

	public static boolean addWaterMark(String logoImg, String orgImg,
			String destImg) {
		boolean result = false;
		MagickImage image = null;
		MagickImage mask = null;
		MagickImage dest = null;
		try {
			ImageInfo info = new ImageInfo();
			image = new MagickImage(new ImageInfo(orgImg + "[0]"));
			mask = new MagickImage(new ImageInfo(logoImg));
			image.setFileName(destImg);
			image.writeImage(info);
			dest = new MagickImage(new ImageInfo(destImg));
			dest.compositeImage(CompositeOperator.AtopCompositeOp, mask,10, 10);
			dest.setFileName(destImg);
			dest.writeImage(info);
			result = true;
		} catch (MagickException e) {

		} finally {
			if (image != null) {
				image.destroyImages();
			}
			if (mask != null) {
				mask.destroyImages();
			}
			if (dest != null) {
				dest.destroyImages();
			}
		}

		return result;
	}

}
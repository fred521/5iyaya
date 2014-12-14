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
 *          ͼƬ�ļ�ѹ���࣬���õ�������ѹ������jmagic��ʹ�����������windows����Ҫ����һ��
 *          ���������ߣ�Ȼ��jmagic���dll�ŵ�system32���漴�ɣ��������linux��������Ҫ
 *          ��jmagic.so���õ���ǰ�û��Ļ�����������
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
	 * ���չ̶��Ĵ�С����ѹ��
	 * 
	 * @param orgImg
	 *            ԭ��ͼƬ·��
	 * @param compressFileSuffix
	 *            ѹ��ͼƬ��׺����dot��ʼ
	 * @param width
	 *            ���
	 * @return
	 */
	public static boolean compressImage(String orgImg,
			String compressFileSuffix, int width, int height) {
		boolean result = false;
		try {

			MagickImage image = new MagickImage(new ImageInfo(orgImg));

			// Ŀ��ͼƬ�ߴ磬�����߽�
			int targetWidth = width > 0 ? width : IMAGE_SUM_SCALE_LIMIT;
			int targetHeight = height > 0 ? height : IMAGE_SUM_SCALE_LIMIT;

			// ԭʼ�ߴ�
			int orgWidth = image.getDimension().width;
			int orgHeight = image.getDimension().height;

			// ��ʼ���ȱ������ź�ߴ�
			int newWidth = targetWidth;
			int newHeight = targetHeight;

			// �������ź�ĳߴ�
			if (orgWidth * targetHeight > orgHeight * targetWidth) {
				// ���ź�Ŀ�ȵ���Ŀ���ȣ��߶�С��Ŀ��߶�
				newHeight = (orgHeight * newWidth) / orgWidth;
				// �������߶�Ϊż������
				newHeight = targetHeight - (targetHeight - newHeight) / 2 * 2;
			} else {
				// ���ź�ĸ߶ȵ���Ŀ��߶ȣ����С��Ŀ����
				newWidth = (orgWidth * newHeight) / orgHeight;
				// ���������Ϊż������
				newWidth = targetWidth - (targetWidth - newWidth) / 2 * 2;
			}

			// ���ݵ�����ĳߴ��������ͼƬѹ������֤�ȱ���ѹ����СͼƬ���ܻ�ѹ��Ŷ
			MagickImage small = image.scaleImage(newWidth, newHeight);

			// �����߽磬��ͼƬ������Ŀ��ߴ�
			int borderX = 0;
			int borderY = 0;

			if (newWidth < targetWidth) {
				borderX = (targetWidth - newWidth) / 2;
			} else if (newHeight < targetHeight) {
				borderY = (targetHeight - newHeight) / 2;
			}

			Rectangle rect = new Rectangle();
			rect.setSize(borderX, borderY);
			// �������߽���ɫ
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
	 * ���տ�Ƚ���ѹ��
	 * 
	 * @param orgImg
	 *            ԭ��ͼƬ·��
	 * @param compressFileSuffix
	 *            ѹ��ͼƬ��׺����dot��ʼ
	 * @param width
	 *            ���
	 * @return
	 */
	public static boolean compressImage(String orgImg,
			String compressFileSuffix, int width) {
		boolean result = false;

		try {
			System.getProperties().setProperty("jmagick.systemclassloader",
					"no");
			MagickImage image = new MagickImage(new ImageInfo(orgImg));

			// ԭʼ�ߴ�
			int beforeScaleX = image.getDimension().width;
			int beforeScaleY = image.getDimension().height;

			double pro = (double) beforeScaleX / width;
			int scaley = (int) ((double) beforeScaleY / pro);

			// ֱ�Ӱ�������Ĳ�������ͼƬѹ����СͼƬ���ܻ�ѹ��Ŷ
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
package com.nonfamous.commom.util.image;

import java.io.File;
import java.io.IOException;

public interface ImageService {

	/**
	 * 保存图片到磁盘
	 * 
	 * @param orignalImage
	 *            不能为null
	 * @param targetFile
	 *            目的图片，不能为null，可以不存在，如果已存在，则覆盖
	 * @throws IOException
	 */
	public void saveImageToFile(byte[] orignalImage, File targetFile)
			throws IOException;

	/**
	 * 压缩图片
	 * 
	 * @param orignalFile
	 *            源图片，不能为null，而且必须存在
	 * @param targetFile
	 *            目的图片，不能为null，可以不存在，如果已存在，则覆盖
	 * @param width
	 *            新文件宽
	 * @param height
	 *            新文件高
	 * @throws IOException
	 */
	public void compress(File orignalImageFile, File targetFile, int width,
			int height) throws IOException;

	/**
	 * 压缩图片
	 * 
	 * @param orignalImage
	 *            源图片，不能为null
	 * @param targetFile
	 *            目的图片，不能为null，可以不存在，如果存在，则覆盖
	 * @param width
	 *            新文件宽
	 * @param height
	 *            新文件高
	 * @throws IOException
	 */
	public void compress(byte[] orignalImage, File targetFile, int width,
			int height) throws IOException;

	/**
	 * 给图片加水印
	 * 
	 * @param originalImageFile
	 *            源图片，不能为null
	 * @param targetFile
	 *            目的图片，不能为null，可以不存在，如果存在，则覆盖
	 * @throws IOException
	 */
	public void addWatermark2Image(File originalImageFile, String text, File targetFile)
			throws IOException;

}

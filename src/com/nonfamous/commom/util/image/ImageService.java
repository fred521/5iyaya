package com.nonfamous.commom.util.image;

import java.io.File;
import java.io.IOException;

public interface ImageService {

	/**
	 * ����ͼƬ������
	 * 
	 * @param orignalImage
	 *            ����Ϊnull
	 * @param targetFile
	 *            Ŀ��ͼƬ������Ϊnull�����Բ����ڣ�����Ѵ��ڣ��򸲸�
	 * @throws IOException
	 */
	public void saveImageToFile(byte[] orignalImage, File targetFile)
			throws IOException;

	/**
	 * ѹ��ͼƬ
	 * 
	 * @param orignalFile
	 *            ԴͼƬ������Ϊnull�����ұ������
	 * @param targetFile
	 *            Ŀ��ͼƬ������Ϊnull�����Բ����ڣ�����Ѵ��ڣ��򸲸�
	 * @param width
	 *            ���ļ���
	 * @param height
	 *            ���ļ���
	 * @throws IOException
	 */
	public void compress(File orignalImageFile, File targetFile, int width,
			int height) throws IOException;

	/**
	 * ѹ��ͼƬ
	 * 
	 * @param orignalImage
	 *            ԴͼƬ������Ϊnull
	 * @param targetFile
	 *            Ŀ��ͼƬ������Ϊnull�����Բ����ڣ�������ڣ��򸲸�
	 * @param width
	 *            ���ļ���
	 * @param height
	 *            ���ļ���
	 * @throws IOException
	 */
	public void compress(byte[] orignalImage, File targetFile, int width,
			int height) throws IOException;

	/**
	 * ��ͼƬ��ˮӡ
	 * 
	 * @param originalImageFile
	 *            ԴͼƬ������Ϊnull
	 * @param targetFile
	 *            Ŀ��ͼƬ������Ϊnull�����Բ����ڣ�������ڣ��򸲸�
	 * @throws IOException
	 */
	public void addWatermark2Image(File originalImageFile, String text, File targetFile)
			throws IOException;

}

package com.nonfamous.commom.util.web.checkcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class ImageBuilder {
	private static Random random = new Random();

	public static final void buildImage(OutputStream outputStream, int width,
			int height, String string) throws IOException {
		// ���ڴ��д���ͼ��
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		// ��ȡͼ��������
		Graphics g = image.getGraphics();

		// �趨����ɫ
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, width, height);

		// �������12�������ߣ�ʹͼ���е���֤�벻�ױ���������̽�⵽
		g.setColor(Color.BLACK);
		for (int i = 0; i < 12; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(width / 4);
			int yl = random.nextInt(height / 4);
			g.drawLine(x, y, x + xl, y + yl);
		}

		// �趨����
		g.setFont(new Font("Times   New   Roman", Font.PLAIN, 18));

		// ����֤����ʾ��ͼ����
		int jumpHight = -2;
		for (int i = 0; i < string.length(); i++) {
			String ch = string.substring(i, i + 1);

			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			// ���ú�����������ɫ��ͬ����������Ϊ����̫�ӽ�������ֻ��ֱ������
			if (jumpHight == -2) {
				jumpHight = 2;
			} else {
				jumpHight = -2;
			}
			g.drawString(ch, 13 * i + 6, 16 + jumpHight);
		}

		// ͼ����Ч
		g.dispose();

		// ���ͼ��ҳ��
		ImageIO.write(image, "JPEG", outputStream);
	}
}

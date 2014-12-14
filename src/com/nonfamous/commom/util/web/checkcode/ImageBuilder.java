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
		// 在内存中创建图象
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics g = image.getGraphics();

		// 设定背景色
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, width, height);

		// 随机产生12条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(Color.BLACK);
		for (int i = 0; i < 12; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(width / 4);
			int yl = random.nextInt(height / 4);
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 设定字体
		g.setFont(new Font("Times   New   Roman", Font.PLAIN, 18));

		// 将认证码显示到图象中
		int jumpHight = -2;
		for (int i = 0; i < string.length(); i++) {
			String ch = string.substring(i, i + 1);

			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			if (jumpHight == -2) {
				jumpHight = 2;
			} else {
				jumpHight = -2;
			}
			g.drawString(ch, 13 * i + 6, 16 + jumpHight);
		}

		// 图象生效
		g.dispose();

		// 输出图象到页面
		ImageIO.write(image, "JPEG", outputStream);
	}
}

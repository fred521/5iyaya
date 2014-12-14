package com.nonfamous.commom.util.web.checkcode;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.WebContentGenerator;

import com.nonfamous.commom.util.web.cookyjar.Cookyjar;

public class CheckCodeImageProducer extends WebContentGenerator implements
		Controller {

	// 生成随机类
	private static Random random = new Random();

	private static int width = 60, height = 20;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("image/jpeg");

		String randomString = String.valueOf(random.nextInt(9000) + 1000);// 产生0
		Cookyjar cookyjar = (Cookyjar) request
				.getAttribute(Cookyjar.CookyjarInRequest);
		cookyjar.set("checkCode", randomString);
		this.preventCaching(response);
		// 0－9999之间的随机数字
		ImageBuilder.buildImage(response.getOutputStream(), width, height,
				randomString);

		return null;
	}
}

package com.nonfamous.tang.web.control;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.nonfamous.tang.dao.query.QueryBase;

/**
 * @author fred
 * @version $Id: PageSlider.java,v 1.2 2009/09/12 05:12:58 fred Exp $
 *          分页的control,使方法 1：request.setAttribute("query",$query子类)
 *          2:request.setAttribute("total_page",$总页数)
 *          request.setAttribute("current_page",$当前页数) 两个变量都应该是int类型
 * 
 * 如果要指定vm，请request.setAttribute("slider_vm_path",vm路径)
 * 如果要指定浮动窗大小,请request.setAttribute("slider_width",int)，否则使用缺省值
 */
public class PageSlider implements Controller {

	public static final Log logger = LogFactory.getLog(PageSlider.class);

	/**
	 * 浮动窗大小，应该是奇数为好
	 */
	private int sliderWidth = 5;

	private String sliderPath = "control/pageSlider";

	public String getSliderPath() {
		return sliderPath;
	}

	public void setSliderPath(String silderPath) {
		this.sliderPath = silderPath;
	}

	public int getSliderWidth() {
		return sliderWidth;
	}

	public void setSliderWidth(int sliderWidth) {
		this.sliderWidth = sliderWidth;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int totalPage = 0;
		int currentPage = 0;
		QueryBase query = (QueryBase) request.getAttribute("query");
		if (query == null) {
			totalPage = (Integer) request.getAttribute("total_page");
			currentPage = (Integer) request.getAttribute("current_page");
		}
		else{
			totalPage = query.getTotalPage();
			currentPage = query.getCurrentPage();
		}
		if(currentPage == 0){
			throw new IllegalStateException("current page will be 0?");
		}
		Object temp = null;
		int width = sliderWidth;
		if ((temp = request.getAttribute("slider_width")) != null) {
			try {
				width = Integer.parseInt(temp.toString());
			} catch (NumberFormatException e) {
				logger.error("error then parse slider_width", e);
			}
		}
		List<Integer> grids = getGrids(totalPage, currentPage, width);
		String currentSlider = this.sliderPath;
		if ((temp = request.getAttribute("slider_vm_path")) != null) {
			currentSlider = temp.toString();
		}
		ModelAndView mv = new ModelAndView(currentSlider, "grids", grids);
		mv.addObject("sliderTotal", totalPage);
		mv.addObject("sliderCurrent", currentPage);
		mv.addObject("sliderNext", currentPage + 1);
		return mv;
	}

	protected static List<Integer> getGrids(int totalPage, int currentPage,
			int width) {
		int widthHalf = width / 2;
		List<Integer> grids = new ArrayList<Integer>();
		for (int i = 1; i <= min(width, currentPage); i++) {
			grids.add(i);
		}
		if (currentPage - grids.size() > widthHalf) {
			grids.add(0);
		}
		for (int i = max(currentPage - widthHalf, grids.size() + 1); i <= min(
				currentPage + widthHalf, totalPage); i++) {
			grids.add(i);
		}
		if (totalPage - grids.get(grids.size() - 1) > width) {
			grids.add(0);
		}
		for (int i = max(totalPage - width + 1, grids.get(grids.size() - 1) + 1); i <= totalPage; i++) {
			grids.add(i);
		}
		return grids;
	}

}

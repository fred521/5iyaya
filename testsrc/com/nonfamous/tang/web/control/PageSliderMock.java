package com.nonfamous.tang.web.control;

import java.util.List;

public class PageSliderMock extends PageSlider {

	public static List<Integer> getGrids(int totalPage, int currentPage,
			int width) {
		return PageSlider.getGrids(totalPage, currentPage, width);
	}
}

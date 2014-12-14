package test.com.nonfamous.tang.web.control;

import java.util.List;

import com.nonfamous.tang.web.control.PageSliderMock;

import junit.framework.TestCase;

public class PageSliderTest extends TestCase{

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testNormal(){
		List<Integer> list = PageSliderMock.getGrids(100, 50, 3);
		System.out.println("100, 50, 3:"+list);
		list = PageSliderMock.getGrids(100, 50, 5);
		System.out.println("100, 50, 5:"+list);
		list = PageSliderMock.getGrids(100, 1, 3);
		System.out.println("100, 1, 3:"+list);
		list = PageSliderMock.getGrids(100, 1, 5);
		System.out.println("100, 1, 5:"+list);
		list = PageSliderMock.getGrids(100, 99, 3);
		System.out.println("100, 99, 3:"+list);
		list = PageSliderMock.getGrids(100, 99, 5);
		System.out.println("100, 99, 5:"+list);
	}
	
	
	public void testExtreme(){
		List<Integer> list = PageSliderMock.getGrids(1, 1, 3);
		System.out.println("1, 1, 3:"+list);
		list = PageSliderMock.getGrids(100, 50, 5);
		System.out.println("100, 50, 5:"+list);
		list = PageSliderMock.getGrids(100, 99, 21);
		System.out.println("100, 99, 21:"+list);
		list = PageSliderMock.getGrids(100, 1, 100);
		System.out.println("100, 1, 100:"+list);
		list = PageSliderMock.getGrids(20, 1, 101);
		System.out.println("20, 1, 101:"+list);
		list = PageSliderMock.getGrids(10, 3,5);
		System.out.println("10, 3,5:"+list);
		list = PageSliderMock.getGrids(10, 4,5);
		System.out.println("10, 4,5:"+list);
		list = PageSliderMock.getGrids(10, 6,5);
		System.out.println("10, 6,5:"+list);
	}

}

package test.com.nonfamous.commom.util;

import com.nonfamous.commom.util.PriceUtils;

import junit.framework.TestCase;

public class PriceUtilsTest extends TestCase {
	public void testFenToYuan() {
		assertEquals("1.00", PriceUtils.fenToYuanString(100));
		assertEquals("0.01", PriceUtils.fenToYuanString(1));
		assertEquals("6.21", PriceUtils.fenToYuanString(621));
		assertEquals("1.30", PriceUtils.fenToYuanString(130));
		assertEquals("76.45", PriceUtils.fenToYuanString(7645));
	}
	
	public void testYuanToFen(){
		assertEquals(100,PriceUtils.yuanToFen(1.00));
		assertEquals("621",PriceUtils.yuanToFenString(6.21));
		assertEquals(442432,PriceUtils.yuanToFen(4424.32));
		assertEquals(43200,PriceUtils.yuanToFen(432));
	}
}

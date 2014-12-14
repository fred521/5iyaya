package test.com.nonfamous.tang.service.upload;

import com.nonfamous.tang.service.upload.pojo.ImageProcessor;

import junit.framework.TestCase;

public class ImageProcessorTest extends TestCase {

	public void testCompressImage() {

		System.out.println( System.getProperty("user.dir") );
		ImageProcessor.compressImage("./testsrc/compress_test.jpg", ".sum.jpg", 0, 0);
		//ImageProcessor.compressImage("./testsrc/compress_test_vert.jpg", ".sum.jpg", 200, 180);

	}
	public void testAddWaterMark()
	{
		String logoImg="d:\\watermark\\index_logo.png";
		String orgImg="d:\\watermark\\test.jpg";
		String destImg="d:\\watermark\\final.jpg";
		System.out.println( System.getProperty("user.dir") );
		ImageProcessor.addWaterMark(logoImg, orgImg, destImg);
		ImageProcessor.compressImage(destImg, ".sum.jpg", 0, 0);
	}
}

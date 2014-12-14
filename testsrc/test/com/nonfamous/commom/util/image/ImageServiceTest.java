package test.com.nonfamous.commom.util.image;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import junit.framework.TestCase;

import com.nonfamous.commom.util.image.ImageService;
import com.nonfamous.commom.util.image.pojo.JMagickImageService;

public class ImageServiceTest extends TestCase {
	private ImageService imageDealing;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		imageDealing = new JMagickImageService();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		imageDealing = null;
	}

	public void testCompress() throws IOException, URISyntaxException {
		URI uri = this.getClass().getClassLoader().getResource(
				"compress_test.jpg").toURI();
		File orignal = new File(uri);
		File target = new File("after_compress.jpg");
		imageDealing.compress(orignal, target, 256, 170);
		assertTrue(target.exists());
		target.delete();
	}

	public void testeAddWatermark2Image() throws Exception {
		URI uri = this.getClass().getClassLoader().getResource("logo.jpg")
				.toURI();
		File orignal = new File(uri);
		File target = new File("watermarked.jpg");
		imageDealing.addWatermark2Image(orignal, "www.5iyya.com", target);
		assertTrue(target.exists());
	}
}

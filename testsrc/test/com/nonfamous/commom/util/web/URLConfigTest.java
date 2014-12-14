package test.com.nonfamous.commom.util.web;

import com.nonfamous.commom.util.web.URLConfig;

import junit.framework.TestCase;

public class URLConfigTest extends TestCase {

	private URLConfig config;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testHttp() {
		config = new URLConfig();
		config.setProtocol("http");
		config.setHost("www.nonof.com");
		config.setPort(8080);
		String url = config.getURL();
		assertEquals("http://www.nonof.com:8080", url);
		config = new URLConfig();
		config.setProtocol("http");
		config.setHost("www.nonof.com");
		config.setPort(8050);
		config.setPath("/admin");
		url = config.getURL();
		assertEquals("http://www.nonof.com:8050", url);
		config = new URLConfig();
		config.setProtocol("http");
		config.setHost("www.nonof.com");
		config.setPort(80);
		config.setPath("user");
		url = config.getURL();
		assertEquals("http://www.nonof.com", url);
		config = new URLConfig();
		config.setProtocol("http");
		config.setHost("www.nonof.com");
		config.setPort(80);
		config.setPath("/");
		url = config.getURL();
		assertEquals("http://www.nonof.com", url);
	}

}
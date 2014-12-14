package test.com.nonfamous.tang.dao;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author eyeieye
 * 
 */
public class DAOTestBase extends TestCase {
	protected BeanFactory daoBeanFactory = null;/*
												 * new
												 * ClassPathXmlApplicationContext(
												 * new String[] {
												 * "data-source_4_unit_test.xml","dao_beans_4_unit_test.xml"
												 * });
												 */

	private static String[] configFile = new String[] {
			"WEB-INF/conf/spring/dao-beans.xml",
			"WEB-INF/conf/spring/data-source.xml",
			};

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		String cp = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "WebRoot";
		File f = new File(cp);
		URL u = f.toURL();
		ClassLoader cl = new URLClassLoader(new URL[] { u }, this.getClass()
				.getClassLoader());
		Thread.currentThread().setContextClassLoader(cl);
		daoBeanFactory = new ClassPathXmlApplicationContext(configFile);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	// public void testBeanFactory() {
	// assertNotNull(daoBeanFactory.getBean("dataSource"));
	// }
}

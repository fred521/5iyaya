package test.com.nonfamous.commom.util.pool;

import java.util.concurrent.Executor;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author fish
 * @version $Id: ThreadPoolExecutorFactoryTest.java,v 1.1 2008/07/11 00:47:11 fred Exp $
 */
public class ThreadPoolExecutorFactoryTest extends TestCase {

	private BeanFactory beanFactory = new ClassPathXmlApplicationContext(
			"test/com/nonfamous/commom/util/pool/thread_pool_test.xml");

	private Executor executor = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		executor = (Executor) beanFactory.getBean("executor");
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetBean() {
		assertNotNull(executor);
	}

}

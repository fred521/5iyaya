package test.com.nonfamous.commom.cache;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nonfamous.commom.cache.CompactCache;
import com.nonfamous.commom.util.UUIDGenerator;

/**
 * @author eyeieye
 * @version $Id: CompactCacheTest.java,v 1.1 2008/07/11 00:47:16 fred Exp $
 */
public class CompactCacheTest extends TestCase {

	private BeanFactory beanFactory = new ClassPathXmlApplicationContext(
			"test/com/nonfamous/commom/cache/compact_cache_test.xml");

	private CompactCache compactCache;

	@SuppressWarnings("unchecked")
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		compactCache = (CompactCache) beanFactory.getBean("testCache");
	}

	@Override
	protected void tearDown() throws Exception {
		compactCache = null;
		super.tearDown();
	}

	public void testPutGet() {
		String key = "fdasdsafds";
		Integer value = 321321;
		this.compactCache.put(key, value);
		for (int i = 0; i < 100; i++) {
			String uuid = UUIDGenerator.generate();
			this.compactCache.put(uuid, 43143);
		}
		Integer get = (Integer) this.compactCache.get(key);
		assertNotNull(get);
		assertEquals(value, get);
		get = (Integer) this.compactCache.get(key);
		assertNotNull(get);
		assertEquals(value, get);
	}

	public void testClean() {
		String key = "rqr43qfsaf";
		Integer value = 327321;
		this.compactCache.put(key, value);
		for (int i = 0; i < 100; i++) {
			String uuid = UUIDGenerator.generate();
			this.compactCache.put(uuid, 43143);
		}
		this.compactCache.clean();
		Integer get = (Integer) this.compactCache.get(key);
		assertNull(get);
	}

}

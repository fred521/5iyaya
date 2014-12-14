package test.com.nonfamous.commom.cache.count;

import java.util.Map;
import java.util.concurrent.Executor;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nonfamous.commom.cache.count.impl.AbstractHitsCountService;

public class HitsCountTest extends TestCase {
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

	public static void assertIntEquals(int a, int b) {
		assertEquals(a, b);
	}

	public void testCountAdd() {
		final String id1 = "12345";
		final String id2 = "434322";
		AbstractHitsCountService<String> countHitsTask = new AbstractHitsCountService<String>() {

			@Override
			public void writerHits(Map<String, Integer> id2HitsMap) {
				assertIntEquals(1, id2HitsMap.get(id1));
				assertIntEquals(12, id2HitsMap.get(id2));
			}
		};
		countHitsTask.setExecutor(executor);
		countHitsTask.addHitOnce(id1);
		countHitsTask.addHits(id2, 12);
		countHitsTask.flush();
	}

	public void testAdd2Max() throws InterruptedException {
		final String id1 = "12345";
		final String id2 = "434322";
		AbstractHitsCountService<String> countHitsTask = new AbstractHitsCountService<String>() {

			@Override
			public void writerHits(Map<String, Integer> id2HitsMap) {
				assertIntEquals(23, id2HitsMap.get(id1));
				assertIntEquals(12, id2HitsMap.get(id2));
//				throw new RuntimeException("hiehei");
			}
		};
		countHitsTask.setExecutor(executor);
		countHitsTask.setHitsCountMax(50);
		countHitsTask.addHits(id1, 23);
		countHitsTask.addHits(id2, 12);
		try {
			countHitsTask.addHits("dafdsafd", 49);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

}

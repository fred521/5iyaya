package test.com.nonfamous.commom.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.nonfamous.commom.util.UUIDGenerator;

import junit.framework.TestCase;

public class UUIDGeneratorTest extends TestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCreate() {
		String s1 = UUIDGenerator.generate();
		assertNotNull(s1);
	}

	public void testInThreads() throws InterruptedException {
		final Set<String> ids = Collections.synchronizedSet(new HashSet<String>());
		long begin = System.currentTimeMillis();
		for(int i=0;i<30;i++){
			Thread t = new Thread(){
				@Override
				public void run() {
					for(int j=0;j<1500;j++){
						String s = UUIDGenerator.generate();
						if(!ids.add(s)){
							assertTrue(false);
						}
					}
					
				}
				
			};
			t.start();
		}
		Thread.sleep(500);
		long end = System.currentTimeMillis();
		System.out.println("在"+(end - begin)+"毫秒内，产生了"+ids.size()+"个id，没有重复");
		
	}
}

package test.com.nonfamous.tang.service.search;

import java.util.ArrayList;
import java.util.List;

import com.nonfamous.tang.domain.SearchKeyWord;
import com.nonfamous.tang.service.search.KeyWordService;

import test.com.nonfamous.tang.service.ServiceTestBase;

/**
 * @author fish
 * 
 */
public class KeyWordServiceTest extends ServiceTestBase {
	private KeyWordService keyWordSevice;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		keyWordSevice = (KeyWordService) this.serviceBeanFactory
				.getBean("keyWordService");
	}

	@Override
	protected void tearDown() throws Exception {
		keyWordSevice = null;
		super.tearDown();
	}

	public void testKeyWordHit() throws InterruptedException {
		keyWordSevice.keyWordHit("�����ս", SearchKeyWord.KeyTypeGoods);
		keyWordSevice.keyWordHit("�����ս", SearchKeyWord.KeyTypeGoods);
		keyWordSevice.keyWordHit("�����ս", SearchKeyWord.KeyTypeGoods);
		Thread.sleep(500);
	}
	
	public void testKeyWordsHit() throws InterruptedException{
		List<String> words = new ArrayList<String>();
		words.add("�����ս");
		words.add("�۹�����ս");
		words.add("��ʿ����");
		keyWordSevice.keyWordHit(words, SearchKeyWord.KeyTypeNews);
		Thread.sleep(500);
	}
}

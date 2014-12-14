package test.com.nonfamous.tang.dao.home;

import java.util.Collection;
import java.util.Random;

import test.com.nonfamous.tang.dao.DAOTestBase;

import com.nonfamous.tang.dao.home.SearchKeyWordDAO;
import com.nonfamous.tang.domain.SearchKeyWord;

public class SearchKeyWordDAOTest extends DAOTestBase{
	private SearchKeyWordDAO keyWordDAO;
	Random r = new Random();
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		keyWordDAO = (SearchKeyWordDAO) this.daoBeanFactory
				.getBean("searchKeyWordDAO");
	}

	@Override
	protected void tearDown() throws Exception {
		keyWordDAO = null;
		super.tearDown();
	}
	public void testAddKeyWord(){
		SearchKeyWord keyWord=new SearchKeyWord();
		String name="victor";
		keyWord.setKeyName(name);
		keyWord.setKeyType("3");
		keyWord.setSearchCount(Math.abs(r.nextLong()));
		if(keyWordDAO.getKeyWordByWord(name)!=null)
//			keyWordDAO.deleteSearchKeyWord(name);
		
		keyWordDAO.addSearchKeyWord(keyWord);
		
		keyWord=keyWordDAO.getKeyWordByWord(keyWord.getKeyName());
		assertNotNull(keyWord.getKeyId());
		assertEquals(name, keyWord.getKeyName());
	}
	public void testupdateKeyWord(){
		SearchKeyWord keyWord=new SearchKeyWord();
		keyWord.setKeyName("µçÄÔ");
		keyWord.setKeyType("1");
		keyWord.setSearchCount(Math.abs(r.nextLong()));
		keyWord.setKeyId(Math.abs(r.nextLong()));
		long count=keyWord.getSearchCount();
		if(keyWordDAO.getKeyWordByWord("µçÄÔ")!=null)
//			keyWordDAO.deleteSearchKeyWord("µçÄÔ");
		keyWordDAO.addSearchKeyWord(keyWord);
		keyWord.setSearchCount(keyWord.getSearchCount()+1);
//		keyWordDAO.updateSearchKeyWord(keyWord);
		keyWord=keyWordDAO.getKeyWordByWord(keyWord.getKeyName());
		assertEquals(count+1, keyWord.getSearchCount().longValue());
	}
	public void testGetHotKeyWord(){
		Collection list=keyWordDAO.getHotKeyWord(10,"1");
		assertNotNull(list);
		assertTrue(!list.isEmpty());
	}
}

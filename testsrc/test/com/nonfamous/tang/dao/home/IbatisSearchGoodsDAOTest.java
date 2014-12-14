package test.com.nonfamous.tang.dao.home;

import test.com.nonfamous.tang.dao.DAOTestBase;

import com.nonfamous.tang.dao.home.SearchGoodsDAO;
import com.nonfamous.tang.dao.query.SearchGoodsQuery;
/**
 * @author  victor
 * */
public class IbatisSearchGoodsDAOTest extends DAOTestBase {
	private SearchGoodsDAO searchGoodsDAO;
	protected void setUp() throws Exception {
		super.setUp();
		searchGoodsDAO = (SearchGoodsDAO) this.daoBeanFactory
				.getBean("searchGoodsDAO");
	}

	@Override
	protected void tearDown() throws Exception {
		searchGoodsDAO = null;
		super.tearDown();
	}
	public void testGetShopListByIds(){
		searchGoodsDAO.setIndexFilePath("C:/index/good");
		SearchGoodsQuery query=new SearchGoodsQuery();
		searchGoodsDAO.findAllGoods(query);
		System.out.println(query.getSearchGoodsCatList());
	}
}

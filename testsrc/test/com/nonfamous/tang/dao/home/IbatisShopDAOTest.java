package test.com.nonfamous.tang.dao.home;

import test.com.nonfamous.tang.dao.DAOTestBase;

import com.nonfamous.tang.dao.home.ShopDAO;
/**
 * @author  victor
 * */
public class IbatisShopDAOTest extends DAOTestBase {
	private ShopDAO shopDAO;
	protected void setUp() throws Exception {
		super.setUp();
		shopDAO = (ShopDAO) this.daoBeanFactory
				.getBean("shopDAO");
	}

	@Override
	protected void tearDown() throws Exception {
		shopDAO = null;
		super.tearDown();
	}
	public void testFindAllGoods(){
		String[] ids = new String[2];
		ids[0] = "1111";
		ids[1] = "2222";
		System.out.println(shopDAO.getShopListByIds(ids));
	}
}

package test.com.nonfamous.tang.dao.home;

import java.util.Collection;

import com.nonfamous.tang.dao.home.MarketTypeDAO;

import test.com.nonfamous.tang.dao.DAOTestBase;

public class MarketTypeDaoTest extends DAOTestBase {
	private MarketTypeDAO marketTypeDAO;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		marketTypeDAO = (MarketTypeDAO) this.daoBeanFactory.getBean("marketTypeDAO");
	}

	@Override
	protected void tearDown() throws Exception {
		marketTypeDAO = null;
		super.tearDown();
	}

	public void testGetAllMarketTypeList() {
		Collection collection = marketTypeDAO.getAllMarketType();
		assertNotNull(collection);
	}

	public void testGetNewsTypeById() {

		assertNotNull(marketTypeDAO.getMarketTypeById("10"));
	}
	
//	public void testGetAllMarketShopList() {
//		List result = new ArrayList();
//		Map showMarket = new HashMap();
//		List market = (List)marketTypeDAO.getAllMarketType();
//		for(int i=0;i<market.size();i++){
//			MarketType m = (MarketType)market.get(i);
//			List shop = (List)marketTypeDAO.getShopByMarketId(m.getMarketType());
//			showMarket.put("marketName", m.getMarketName());
//			showMarket.put("shops", shop);
//			result.add(showMarket);
//		}
//		int s = result.size();
//		assertNotNull(showMarket);
//	}
}

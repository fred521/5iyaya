/**
 * 
 */
package test.com.nonfamous.tang.service.home;

import com.nonfamous.tang.dao.home.ShopDAO;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.domain.result.ShopResult;
import com.nonfamous.tang.service.home.ShopService;

import test.com.nonfamous.tang.service.ServiceTestBase;

/**
 * @author swordfish
 *
 */
public class ShopServieTest extends ServiceTestBase{
	
	private ShopService shopService;

	@SuppressWarnings("unused")
	private ShopDAO shopDAO;


	@Override
	protected void setUp() throws Exception {
		super.setUp();
		shopService = (ShopService) this.serviceBeanFactory
				.getBean("shopService");
		shopDAO = (ShopDAO) this.serviceBeanFactory.getBean("shopDAO");
	}

	public void testAddShop() {
		Shop shop = new Shop();
//		for(int s=1;s<10;s++){
//		for(int i=10;i<90;i=i+10){
		shop.setShopName("新开的店铺");
		shop.setMemberId("f89e47866096449ea5b63939ac0f606c");
//		shop.setMemberId(UUIDGenerator.generate());
		shop.setLoginId("Swordfish");
		shop.setShopOwner("Swordfish");
		shop.setCommodity("卫星电视222");
		shop.setBelongMarketId("10");
		shop.setAddress("杭州市");
		ShopResult result = shopService.insertShop(shop,true);
		assertNotNull(result);
//		}
//		}
	}
}

package test.com.nonfamous.tang.service.home;

import com.nonfamous.tang.dao.home.GoodsDAO;
import com.nonfamous.tang.dao.query.GoodsQuery;
import com.nonfamous.tang.service.home.GoodsService;

import test.com.nonfamous.tang.service.ServiceTestBase;

public class GoodsServiceTest extends ServiceTestBase{

	@SuppressWarnings("unused")
	private GoodsService goodsService;

	private GoodsDAO goodsDAO;
	
	protected void setUp() throws Exception {
		super.setUp();
		goodsService = (GoodsService) this.serviceBeanFactory
				.getBean("adminGoodsService");
		goodsDAO = (GoodsDAO) this.serviceBeanFactory.getBean("goodsDAO");
	}
	
//	public void testGetSearchList(){
//		GoodsQuery query = new GoodsQuery();
//		query.setGoodsStatus("N");
//		query.setGoodsName("product");
//		query.setNick("nick");
//		query.setMemberId("f89e47866096449ea5b63939ac0f606c");
//		query = adminGoodsService.getGoodsList(query);
//		assertNotNull(query);
//		System.out.println(query.getGoods().size());
//		
//	}
//	public void testGetGoodsDetailById(){
//		String id = "4028928b1339ff3c011339ff41e90005";
//		GoodsBaseInfo b = goodsDAO.getGoodsDetailById(id);
//		assertNotNull(b);
//	}
	
//	public void testListAll(){
//		GoodsQuery query = new GoodsQuery();
//		query.setGoodsStatus(GoodsBaseInfo.DELETE_STATUS);
//		query = goodsDAO.getAllGoodsList(query);
//		assertNotNull(query);
//		assertEquals(query.getGoods().size(), 20);
//		
//	}
	
	public void testGetSearchList(){
		GoodsQuery query = new GoodsQuery();
		query.setShopOwner("haha");
		query = goodsDAO.getSearchList(query);
		assertEquals(query.getGoods().size(), 0);
	}
}

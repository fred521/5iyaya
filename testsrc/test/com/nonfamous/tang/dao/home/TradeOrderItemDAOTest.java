package test.com.nonfamous.tang.dao.home;

import java.util.List;
import java.util.Random;

import com.nonfamous.tang.dao.home.TradeOrderDAO;
import com.nonfamous.tang.domain.trade.TradeOrderItem;

import test.com.nonfamous.tang.dao.DAOTestBase;

public class TradeOrderItemDAOTest extends DAOTestBase {
	private TradeOrderDAO tradeOrderDAO;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.tradeOrderDAO = (TradeOrderDAO) this.daoBeanFactory
				.getBean("tradeOrderDAO");
	}

	@Override
	protected void tearDown() throws Exception {
		this.tradeOrderDAO = null;
		super.tearDown();
	}

	public void testInsertItem() {
		TradeOrderItem item = new TradeOrderItem();
		item.setBatchPrice(new Long(Math.abs(new Random().nextInt())));
		item.setCreator("402881e4138c35d001138c35d07d0000");
		item.setGoodsId(Math.abs(new Random().nextInt())+"");
		item.setGoodsName("假商品,不存在"+new Random().nextInt());
		item.setOrderNo("2007091510000000");
		item.setShopId(Math.abs(new Random().nextInt())+"");
		item.setModifier("402881e4138c35d001138c35d07d0000");
		assertTrue(this.tradeOrderDAO.insertOrderItem(item));
	}
	
	public void testFindItems(){
		List<TradeOrderItem> items = this.tradeOrderDAO.findOrderItems("2007091510000000");
		assertNotNull(items);
		assertFalse(items.isEmpty());
	}
	
	public void testUpdateItems(){
		List<TradeOrderItem> items = this.tradeOrderDAO.findOrderItems("2007091510000000");
		for(TradeOrderItem item : items){
			item.setBatchPrice(12345L);
			item.setTotalFree(987654321L);
			item.setTotalNum(1119L);
		}
		this.tradeOrderDAO.updateOrderItems(items);
	}
}

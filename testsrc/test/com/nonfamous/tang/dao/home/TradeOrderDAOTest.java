package test.com.nonfamous.tang.dao.home;

import com.nonfamous.tang.dao.home.TradeOrderDAO;
import com.nonfamous.tang.dao.query.OrderQuery;
import com.nonfamous.tang.domain.trade.TradeOrder;

import test.com.nonfamous.tang.dao.DAOTestBase;

public class TradeOrderDAOTest extends DAOTestBase {
	private TradeOrderDAO orderDAO;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.orderDAO = (TradeOrderDAO) this.daoBeanFactory
				.getBean("tradeOrderDAO");
	}

	@Override
	protected void tearDown() throws Exception {
		this.orderDAO = null;
		super.tearDown();
	}

	public void testInsertOrder() {
		TradeOrder order = new TradeOrder();
		order.setBuyerId("4028928c150859d801150859d8aa0000");// test user
																// shenyu
		order.setBuyerLoginId("shenyu");
		order.setSellerId("402881e4138c35d001138c35d07d0000");// test user
																// daodao
		order.setSellerLoginId("DAODAO");
		order.setCreator("unit test");
		order.setMemo("µ•‘™≤‚ ‘,ÕÊÕÊµƒ");
		order.setOrderType(TradeOrder.TypeTrade);
		order.setShopId("297e13ef13b040c70113b04f3bc60001");
		order.setShopName("daodao ≤‚ ‘”√µÍ∆Ã");
		order.setStatus(TradeOrder.StatusInit);
		order.setModifier("4028928c150859d801150859d8aa0000");
		assertTrue(this.orderDAO.insertOrder(order));
	}

	public void testFindOrders() {
		OrderQuery query = new OrderQuery();
		query.setMemberId("4028928c150859d801150859d8aa0000");
		query.setSearchStatus(OrderQuery.SearchStatusAll);
		query = this.orderDAO.findOrders(query);
		assertNotNull(query);
		assertFalse(query.getOrders().isEmpty());
		
		query = new OrderQuery();
		query.setMemberId("4028928c150859d801150859d8aa0000");
		query.setSearchStatus(OrderQuery.SearchStatusBothConfirm);
		query = this.orderDAO.findOrders(query);
		assertNotNull(query);
		assertFalse(query.getOrders().isEmpty());
		
		query = new OrderQuery();
		query.setMemberId("4028928c150859d801150859d8aa0000");
		query.setSearchStatus(OrderQuery.SearchStatusCloed);
		query = this.orderDAO.findOrders(query);
		assertNotNull(query);
		assertFalse(query.getOrders().isEmpty());
		
		query = new OrderQuery();
		query.setMemberId("4028928c150859d801150859d8aa0000");
		query.setSearchStatus(OrderQuery.SearchStatusWaitHimConfirm);
		query = this.orderDAO.findOrders(query);
		assertNotNull(query);
		assertFalse(query.getOrders().isEmpty());
		
		query = new OrderQuery();
		query.setMemberId("4028928c150859d801150859d8aa0000");
		query.setSearchStatus(OrderQuery.SearchStatusWaitMeConfirm);
		query = this.orderDAO.findOrders(query);
		assertNotNull(query);
		assertFalse(query.getOrders().isEmpty());
	}
	
	public void testGetOrder(){
		String id = "2007091510000002";
		TradeOrder order = this.orderDAO.getTradeOrder(id);
		assertNotNull(order);
	}
	
	public void testUpdateStatus(){
		String id = "2007091510000002";
		TradeOrder order = this.orderDAO.getTradeOrder(id);
		order.setStatus(TradeOrder.StatusOrderClose);
		order.setModifier("unit test");
		assertTrue(this.orderDAO.updateOrderStatus(order));
		
		order.setStatus(TradeOrder.StatusOrderClose);
		order.setModifier("unit test");
		assertTrue(this.orderDAO.updateOrderStatus(order));
		
		order.setStatus(TradeOrder.StatusOrderSuccess);
		order.setModifier("unit test");
		assertTrue(this.orderDAO.updateOrderStatus(order));
		
		order.setStatus(TradeOrder.StatusWaitBuyerConfirm);
		order.setModifier("unit test");
		assertTrue(this.orderDAO.updateOrderStatus(order));
		
		order.setStatus(TradeOrder.StatusWaitPay);
		order.setModifier("unit test");
		assertTrue(this.orderDAO.updateOrderStatus(order));
		
		order.setStatus(TradeOrder.StatusWaitSellerConfirm);
		order.setModifier("unit test");
		assertTrue(this.orderDAO.updateOrderStatus(order));
		
		order.setStatus(TradeOrder.StatusInit);
		order.setModifier("unit test");
		assertTrue(this.orderDAO.updateOrderStatus(order));
	}
}

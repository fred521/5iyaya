package test.com.nonfamous.tang.service.home;

import com.nonfamous.tang.dao.query.TradePayQuery;
import com.nonfamous.tang.domain.result.PayResult;
import com.nonfamous.tang.domain.trade.TradeOrder;
import com.nonfamous.tang.service.home.TradePayService;

import test.com.nonfamous.tang.service.ServiceTestBase;

public class TradePayServiceTest extends ServiceTestBase {

	private TradePayService tradePayService;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tradePayService = (TradePayService) this.serviceBeanFactory
				.getBean("tradePayService");
	}

	public void testOrderPay() {
		PayResult result = tradePayService.orderPay("2007091510000020",
				"4028928c150859d801150859d8aa0001");
		assertFalse(result.isSuccess());
		assertEquals(result.getErrorCode(), PayResult.ERROR_INVALID_PARAMETER);
		result = tradePayService.orderPay("2007091510000020",
				"4028928c150859d801150859d8aa0000");
		result = tradePayService.orderPay("2007091510000005",
				"4028928c150859d801150859d8aa0000");
		assertFalse(result.isSuccess());
		assertEquals(result.getErrorCode(), PayResult.ERROR_ORDER_STATUS);
	}

	public void testQuickPay() {
		TradeOrder tradeOrder = new TradeOrder();
		tradeOrder.setBuyerId("297e13ef138261780113826178a70000");
		tradeOrder.setBuyerLoginId("testseller1");
		tradeOrder.setMemo("unit test quick pay");
		tradeOrder.setOrderDate("20070917");
		tradeOrder.setPayFee(new Long(300));
		tradeOrder.setShopId("297e13ef13b040c70113b04f3bc60001");
		tradeOrder.setShopName("daodao ≤‚ ‘”√µÍ∆Ã");
		PayResult result = tradePayService.quickPay(tradeOrder, "unit test");
		assertTrue(result.isSuccess());
	}

	public void testQueryPayList() {
		TradePayQuery query = new TradePayQuery();
		query.setBuyId("297e13ef138261780113826178a70000");
		query.setSellerId("402881e4138c35d001138c35d07d0000");
		query.setStartDate("2007-09-17");
		query.setEndDate("2007-09-18");
		query = tradePayService.queryTradePayList(query);
		assertNotNull(query.getTradePayList());
		assertEquals(query.getTotalItem().intValue(), 3);
	}

	public void testChangePayStatus() {
		PayResult result = tradePayService.changePayStatus(new Long(61), "S",
				"unit tester", "INNER", "0");
		assertTrue(result.isSuccess());
	}

	public void testChangeTransPay() {
		PayResult result = tradePayService.changeTransStatus(new Long(41), "S",
				"unit tester");
		assertTrue(result.isSuccess());
	}

}

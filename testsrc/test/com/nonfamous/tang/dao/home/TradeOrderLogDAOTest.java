package test.com.nonfamous.tang.dao.home;

import java.util.List;

import com.nonfamous.tang.dao.home.TradeOrderDAO;
import com.nonfamous.tang.domain.trade.TradeOrder;
import com.nonfamous.tang.domain.trade.TradeOrderLog;

import test.com.nonfamous.tang.dao.DAOTestBase;

public class TradeOrderLogDAOTest extends DAOTestBase {
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

	public void testInsertLog() {
		TradeOrderLog log = new TradeOrderLog();
		log.setCreator("unit test");
		log.setMemberType(TradeOrderLog.TypeBuyer);
		log.setMemo("µ•‘™≤‚ ‘“≤" + System.currentTimeMillis());
		log.setOrderNo("2007091510000002");
		log.setPayFee(232123L);
		log.setStatus(TradeOrder.StatusWaitBuyerConfirm);
		this.tradeOrderDAO.insertOrderLog(log);
	}
	
	public void testFindLogs(){
		List<TradeOrderLog> logs = this.tradeOrderDAO.findOrderLogByOrderNo("2007091510000002");
		assertNotNull(logs);
		assertFalse(logs.isEmpty());
	}

}

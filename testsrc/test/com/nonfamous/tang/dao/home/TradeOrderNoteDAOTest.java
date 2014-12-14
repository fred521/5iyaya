package test.com.nonfamous.tang.dao.home;

import java.util.List;

import test.com.nonfamous.tang.dao.DAOTestBase;

import com.nonfamous.tang.dao.home.TradeOrderDAO;
import com.nonfamous.tang.domain.trade.TradeOrderNote;

public class TradeOrderNoteDAOTest extends DAOTestBase {
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

	public void testInsertNote() {
		TradeOrderNote note = new TradeOrderNote();
		note.setOrderNo("2007091510000000");// 测试第一条交易记录
		note.setCreator("402881e4138c35d001138c35d07d0000");
		note.setMemberType(TradeOrderNote.TypeBuyer);
		note.setMemo("我要买" + System.currentTimeMillis() + " 我就是要买呀");
		assertTrue(this.tradeOrderDAO.insertNote(note));

		note = new TradeOrderNote();
		note.setOrderNo("2007091510000000");// 测试第一条交易记录
		note.setCreator("4028928c150859d801150859d8aa0000");
		note.setMemberType(TradeOrderNote.TypeSeller);
		note.setMemo("我要卖" + System.currentTimeMillis() + " 我就是要卖呀");
		assertTrue(this.tradeOrderDAO.insertNote(note));
	}

	public void testFindNotes() {
		String orderId = "2007091510000000";
		List<TradeOrderNote> list = this.tradeOrderDAO
				.findNotesByOrderId(orderId);
		assertNotNull(list);
		assertFalse(list.isEmpty());
	}
}

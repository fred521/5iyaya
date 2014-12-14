package test.com.nonfamous.tang.dao.home;

import test.com.nonfamous.tang.dao.DAOTestBase;

import com.nonfamous.tang.dao.home.QuartzLogDAO;
import com.nonfamous.tang.domain.QuartzLog;
/**
 * @author:victor
 * */
public class QuartzLogDAOTest extends DAOTestBase {
	private QuartzLogDAO quartzLogDAO;
	protected void setUp() throws Exception {
		super.setUp();
		quartzLogDAO = (QuartzLogDAO) this.daoBeanFactory
				.getBean("quartzLogDAO");
	}

	@Override
	protected void tearDown() throws Exception {
		quartzLogDAO = null;
		super.tearDown();
	}
	public void testGetQuartzLogByType(){
		QuartzLog log=quartzLogDAO.getQuartzLogByType("goods");
		assertNotNull(log);
		log=quartzLogDAO.getQuartzLogByType("shop");
		assertNotNull(log);
		log=quartzLogDAO.getQuartzLogByType("member");
		assertNotNull(log);
		log=quartzLogDAO.getQuartzLogByType("news");
		assertNotNull(log);
	}
	public void testUpdateQuartzLogByType(){
		QuartzLog log=quartzLogDAO.getQuartzLogByType("goods");
		log.setQuartzMemo(log.getQuartzMemo()+"test");
		quartzLogDAO.updateQuartzLog(log);
		log=quartzLogDAO.getQuartzLogByType("goods");
		log.setQuartzMemo(log.getQuartzMemo().substring(0, log.getQuartzMemo().length()-4));
		quartzLogDAO.updateQuartzLog(log);
	}
}

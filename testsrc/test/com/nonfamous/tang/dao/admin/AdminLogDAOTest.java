package test.com.nonfamous.tang.dao.admin;

import org.apache.commons.lang.RandomStringUtils;

import test.com.nonfamous.tang.dao.DAOTestBase;

import com.nonfamous.tang.dao.admin.AdminLogDAO;
import com.nonfamous.tang.domain.AdminLog;

public class AdminLogDAOTest extends DAOTestBase {
	private AdminLogDAO adminLogDAO ;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		adminLogDAO = (AdminLogDAO) this.daoBeanFactory.getBean("adminLogDAO");
	}

	@Override
	protected void tearDown() throws Exception {
		adminLogDAO = null;
		super.tearDown();
	}
	
	public void testCreateLog(){
		AdminLog log = new AdminLog();
		log.setAdminUserId(123L);
		log.setActionIp("127.0.0.1");
		log.setAction("unit test");
		log.setParameter(RandomStringUtils.randomAscii(645));
		Long id = this.adminLogDAO.createAdminLog(log);
		assertNotNull(id);
		
		log = new AdminLog();
		log.setAdminUserId(123L);
		log.setActionIp("127.0.0.1");
		log.setAction("unit test");
		log.setParameter(RandomStringUtils.randomAscii(5432));
		id = this.adminLogDAO.createAdminLog(log);
		assertNotNull(id);
	}
}

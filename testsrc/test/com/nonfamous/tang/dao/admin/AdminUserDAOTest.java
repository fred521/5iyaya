package test.com.nonfamous.tang.dao.admin;

import java.util.List;
import java.util.Random;

import test.com.nonfamous.tang.dao.DAOTestBase;

import com.nonfamous.tang.dao.admin.AdminUserDAO;
import com.nonfamous.tang.domain.AdminUser;

public class AdminUserDAOTest extends DAOTestBase {

	private AdminUserDAO adminUserDAO;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		adminUserDAO = (AdminUserDAO) this.daoBeanFactory
				.getBean("adminUserDAO");
	}

	@Override
	protected void tearDown() throws Exception {
		adminUserDAO = null;
		super.tearDown();
	}

	public void testGetAdminUserByName() {
		AdminUser u = adminUserDAO.getUserByName("system");
		assertNotNull(u);
	}

	public void testCreateAdminUser() {
		Random r = new Random();
		AdminUser u = new AdminUser();
		u.setCreator("unit test");
		String userName = "au" + r.nextInt(9999999);
		u.setLoginName(userName);
		String psw = "fobidden" + r.nextInt(99);
		u.setUnencryptPassword(psw);
		String phone = "1388888" + r.nextInt(99);
		u.setPhone(phone);

		adminUserDAO.createAdminUser(u);
		u = adminUserDAO.getUserByName(userName);

		assertNotNull(u.getUserId());
		assertEquals("unit test", u.getCreator());
		assertEquals(userName, u.getLoginName());
		u.setUnencryptPassword(psw);
		assertTrue(u.isPasswordCorrect());
		assertEquals(phone, u.getPhone());
		assertNotNull(u.getGmtCreate());
		assertNotNull(u.getGmtModify());
	}

	public void testUpdatePassword() {
		Random r = new Random();
		AdminUser u = adminUserDAO.getUserByName("system");
		u.setNewPassword("system");
		u.setModifier("unit test" + r.nextInt(99));
		assertTrue(adminUserDAO.updateAdminUserPassword(u));
		u = adminUserDAO.getUserByName("system");
		u.setUnencryptPassword("system");
		assertTrue(u.isPasswordCorrect());
	}

	public void testUpdateStatus() {
		AdminUser u = adminUserDAO.getUserByName("system");
		u.setUserStatus(AdminUser.StatusNormal);
		u.setModifier("unit test testUpdateStatus");
		adminUserDAO.setAdminUserStatus(u);
		u = adminUserDAO.getUserByName("system");
		assertEquals(u.getUserStatus(), AdminUser.StatusNormal);
		assertEquals("unit test testUpdateStatus", u.getModifier());
	}

	public void testGetAll() {
		List<AdminUser> list = this.adminUserDAO.findAll();
		assertNotNull(list);
		assertTrue(list.size() > 1);
	}
}

package test.com.nonfamous.tang.service.home;

import com.nonfamous.commom.util.MD5Encrypt;
import com.nonfamous.tang.dao.home.MemberDAO;
import com.nonfamous.tang.dao.home.MobileValidateDAO;
import com.nonfamous.tang.dao.query.MemberQuery;
import com.nonfamous.tang.domain.Buyer;
import com.nonfamous.tang.domain.Member;
import com.nonfamous.tang.domain.MobileValidate;
import com.nonfamous.tang.domain.Seller;
import com.nonfamous.tang.domain.result.ActiveResult;
import com.nonfamous.tang.domain.result.LoginResult;
import com.nonfamous.tang.domain.result.NewMemberResult;
import com.nonfamous.tang.domain.result.UpdateMemberInfoResult;
import com.nonfamous.tang.service.home.MemberService;

import test.com.nonfamous.tang.service.ServiceTestBase;

/**
 * <p>
 * 会员服务测试类
 * </p>
 * 
 * @author:daodao
 * @version $Id: MemberServiceTest.java,v 1.1 2008/07/11 00:47:03 fred Exp $
 */
public class MemberServiceTest extends ServiceTestBase {
	private MemberService memberService;

	private MemberDAO memberDAO;

	private MobileValidateDAO mobileValidateDAO;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		memberService = (MemberService) this.serviceBeanFactory
				.getBean("memberService");
		memberDAO = (MemberDAO) this.serviceBeanFactory.getBean("memberDAO");
		mobileValidateDAO = (MobileValidateDAO) this.serviceBeanFactory
				.getBean("mobileValidateDAO");
	}

	public void testAddSeller() {
		Seller seller = new Seller();
		seller.setAddress("杭州文三路");
		seller.setAreaCode("330103");
		seller.setEmail("sunzy@hundsun.com");
		seller.setLoginId("testSeller");
		seller.setMD5LoginPassword("111111");
		seller.setMobile("13588010001");
		seller.setName("daodao测试");
		seller.setNick("daodao");
		seller.setPhone("0571-26888888");
		seller.setPostCode("310000");
		seller.setRegisterIp("127.0.0.1");
		seller.setSex("F");
		NewMemberResult result = memberService.addSeller(seller, "daodaotest");
		assertTrue(result.isSuccess());
		assertNotNull(result.getMemberId());
		String memberId = result.getMemberId();
		result = memberService.addSeller(seller, "daodaotest");
		assertFalse(result.isSuccess());
		assertEquals(result.getErrorCode(), NewMemberResult.ERROR_MEMBER_EXIST);
		seller.setLoginId("test1");
		assertFalse(result.isSuccess());
		result = memberService.addSeller(seller, "daodaotest");
		assertEquals(result.getErrorCode(), NewMemberResult.ERROR_MOBILE_EXIST);
		// 清理数据，不然一堆垃圾数据了
		memberDAO.deleteMember(memberId);
	}

	public void testAddBuyer() {
		Buyer buyer = new Buyer();
		buyer.setAddress("杭州体育场路");
		buyer.setAreaCode("330102");
		buyer.setEmail("daodao16@163.com");
		buyer.setLoginId("testBuyer1");
		buyer.setMD5LoginPassword("111111");
		buyer.setMobile("13588010003");
		buyer.setName("daodao测试买家");
		buyer.setNick("daodao");
		buyer.setPhone("0571-85090000");
		buyer.setPostCode("310004");
		buyer.setRegisterIp("127.0.0.1");
		buyer.setSex("F");
		NewMemberResult result = memberService.addBuyer(buyer);
		assertTrue(result.isSuccess());
		assertNotNull(result.getMemberId());
		String memberId = result.getMemberId();
		// 清理垃圾数据
		memberDAO.deleteMember(memberId);
		mobileValidateDAO.delete(memberId);
	}

	public void testLogin() {
		LoginResult result = memberService.login("testSeller1", "111111",
				"192.168.0.1");
		assertTrue(result.isSuccess());
		Member member = result.getMember();
		assertNotNull(member);
		assertEquals(member.getAddress(), "杭州文三路");
		assertEquals(member.getEmail(), "sunzy@hundsun.com");

		result = memberService.login("testSeller", "111111", "192.168.0.1");
		assertFalse(result.isSuccess());
		assertEquals(result.getErrorCode(), LoginResult.ERROR_NO_MEMBER);

		result = memberService.login("testBuyer1", "1111112", "192.168.0.1");
		assertFalse(result.isSuccess());
		assertEquals(result.getErrorCode(), LoginResult.ERROR_PASS_ERROR);

		result = memberService.login("testBuyer1", "111111", "192.168.0.1");
		assertFalse(result.isSuccess());
		assertEquals(result.getErrorCode(), LoginResult.ERROR_MEMBER_STATUS);
	}

	public void testGetMemberById() {
		Member member = memberService
				.getMemberById("29788689fd38435383777d767b42d5f9");
		assertNotNull(member);
		assertEquals(member.getAddress(), "杭州体育场路");
		assertEquals(member.getEmail(), "daodao16@163.com");
		assertEquals(member.getName(), "daodao测试买家");
		assertEquals(member.getStatus(), Member.STATUS_DISABLE);
		assertEquals(member.getMemberType(), Member.FLAG_BUYER);
	}

	public void testUpdateUserInfo() {
		Seller seller = new Seller();
		seller.setAddress("杭州文三路");
		seller.setAreaCode("330103");
		seller.setEmail("sunzy@hundsun.com");
		seller.setLoginId("testSellerUpdate");
		seller.setMD5LoginPassword("111111");
		seller.setMobile("13988010001");
		seller.setName("daodao测试");
		seller.setNick("daodao");
		seller.setPhone("0571-26888888");
		seller.setPostCode("310000");
		seller.setRegisterIp("127.0.0.1");
		seller.setSex("F");
		NewMemberResult result = memberService.addSeller(seller,
				"daodaotestUpdate");
		assertTrue(result.isSuccess());
		seller.setAddress("杭州文三路Update");
		seller.setAreaCode("330108");
		seller.setEmail("sunzy@handsome.com.cn");
		seller.setMD5LoginPassword("111112");
		seller.setMobile("13988010002");
		seller.setName("daodao测试Update");
		seller.setNick("daodaoUpdate");
		seller.setPhone("0571-26888889");
		seller.setPostCode("310001");
		seller.setSex("M");
		UpdateMemberInfoResult updateResult = memberService.updateMemberInfo(
				seller, "testUpdate");
		assertTrue(updateResult.isSuccess());
		Member member = memberService.getMemberById(result.getMemberId());
		assertNotNull(member);
		assertEquals(member.getAddress(), "杭州文三路Update");
		assertEquals(member.getAreaCode(), "330108");
		assertEquals(member.getEmail(), "sunzy@handsome.com.cn");
		assertEquals(member.getMobile(), "13988010002");
		assertEquals(member.getName(), "daodao测试Update");
		assertEquals(member.getNick(), "daodaoUpdate");
		assertEquals(member.getPhone(), "0571-26888889");
		assertEquals(member.getPostCode(), "310001");
		assertEquals(member.getSex(), "M");
		// 清理垃圾数据
		memberDAO.deleteMember(result.getMemberId());
	}

	public void testChangePassword() {
		Seller seller = new Seller();
		seller.setAddress("杭州文三路");
		seller.setAreaCode("330103");
		seller.setEmail("sunzy@hundsun.com");
		seller.setLoginId("testSellerChangePwd");
		seller.setMD5LoginPassword("111111");
		seller.setMobile("13988010001");
		seller.setName("daodao测试");
		seller.setNick("daodao");
		seller.setPhone("0571-26888888");
		seller.setPostCode("310000");
		seller.setRegisterIp("127.0.0.1");
		seller.setSex("F");
		NewMemberResult result = memberService.addSeller(seller,
				"daodaotestChangePwd");
		UpdateMemberInfoResult resultUpdate = memberService.changePassowrd(
				result.getMemberId(), "111111", "111112");
		assertTrue(resultUpdate.isSuccess());
		Member member = memberService.getMemberById(result.getMemberId());
		assertEquals(member.getLoginPassword(), MD5Encrypt.encode("111112"));

		resultUpdate = memberService.changePassowrd("123456", "111111",
				"111112");
		assertFalse(resultUpdate.isSuccess());
		assertEquals(resultUpdate.getErrorCode(),
				UpdateMemberInfoResult.ERROR_NO_MEMBER);

		memberDAO.updateStatus(result.getMemberId(), Member.STATUS_DELETED,
				"testChangePwd");
		resultUpdate = memberService.changePassowrd(result.getMemberId(),
				"111111", "111112");
		assertFalse(resultUpdate.isSuccess());
		assertEquals(resultUpdate.getErrorCode(),
				UpdateMemberInfoResult.ERROR_UPDATE_NOT_ALLOWED);

		memberDAO.updateStatus(result.getMemberId(), Member.STATUS_NORMAL,
				"testChangePwd");
		resultUpdate = memberService.changePassowrd(result.getMemberId(),
				"111111", "111112");
		assertFalse(resultUpdate.isSuccess());
		assertEquals(resultUpdate.getErrorCode(),
				UpdateMemberInfoResult.ERROR_OLD_PASSWORD);
		// 清理垃圾数据
		memberDAO.deleteMember(result.getMemberId());
	}

	public void testActiveMember() {
		Buyer buyer = new Buyer();
		buyer.setAddress("杭州文三路测试");
		buyer.setAreaCode("330102");
		buyer.setEmail("sunzy@handsome.com.cn");
		buyer.setLoginId("testBuyerActive");
		buyer.setMD5LoginPassword("111111");
		buyer.setMobile("13588010043");
		buyer.setName("daodao测试激活");
		buyer.setNick("daodao");
		buyer.setPhone("0571-85090012");
		buyer.setPostCode("310004");
		buyer.setRegisterIp("127.0.0.1");
		buyer.setSex("F");
		NewMemberResult result = memberService.addBuyer(buyer);
		assertTrue(result.isSuccess());
		ActiveResult activeResult = memberService.activeMember("123456",
				"111111");
		assertFalse(activeResult.isSuccess());
		assertEquals(activeResult.getErrorCode(), ActiveResult.ERROR_NO_MEMBER);
		activeResult = memberService.activeMember(
				"29788689fd38435383777d767b42d5f9", "111111");
		assertFalse(activeResult.isSuccess());
		assertEquals(activeResult.getErrorCode(),
				ActiveResult.ERROR_UPDATE_NOT_ALLOWED);
		activeResult = memberService
				.activeMember(result.getMemberId(), "12345");
		assertFalse(activeResult.isSuccess());
		assertEquals(activeResult.getErrorCode(),
				ActiveResult.ERROR_INVALID_CHECK_CODE);
		MobileValidate validate = mobileValidateDAO.getValidateInfo(result
				.getMemberId());
		assertNotNull(validate);
		String activeCode = validate.getValidateCode();
		activeResult = memberService.activeMember(result.getMemberId(),
				activeCode);
		assertTrue(activeResult.isSuccess());
		activeResult = memberService.activeMember(result.getMemberId(),
				activeCode);
		assertFalse(activeResult.isSuccess());
		assertEquals(activeResult.getErrorCode(), ActiveResult.ERROR_HAS_ACTIVE);
		memberDAO.deleteMember(result.getMemberId());
	}

	public void testUpdateMemberStatus() {
		Seller seller = new Seller();
		seller.setAddress("杭州文三路测试修改");
		seller.setAreaCode("330103");
		seller.setEmail("sunzy@handsome.com.cn");
		seller.setLoginId("testSellerStatus");
		seller.setMD5LoginPassword("111111");
		seller.setMobile("13588010011");
		seller.setName("daodao测试Status");
		seller.setNick("daodao");
		seller.setPhone("0571-26888888");
		seller.setPostCode("310000");
		seller.setRegisterIp("127.0.0.1");
		seller.setSex("F");
		NewMemberResult resultNew = memberService.addSeller(seller,
				"daodaotest");
		assertTrue(resultNew.isSuccess());
		UpdateMemberInfoResult result = memberService.updateMemberStatus(
				resultNew.memberId, Member.STATUS_DELETED, "daodaoDeleter");
		assertFalse(result.isSuccess());
		assertEquals(result.getErrorCode(),
				UpdateMemberInfoResult.ERROR_UPDATE_NOT_ALLOWED);
		result = memberService.updateMemberStatus(resultNew.memberId,
				Member.STATUS_DISABLE, "daodaoDeleter");
		assertTrue(result.isSuccess());
		memberDAO.deleteMember(resultNew.getMemberId());
	}

	public void testQuery() {
		MemberQuery query = new MemberQuery();
		query.setLoginId("test123");
		query.setNick("真实姓名");
		query.setMobile("133");
		query.setMemberType("S");
		memberService.queryMember(query);
	}

	public void testQuickQuery() {
		MemberQuery query = new MemberQuery();
		query.setLoginId("test123");
		query.setRealName("真实姓名");
		query.setNick("真实姓名");
		memberService.quickQueryShop(query);
		query.setLoginId(null);
		query.setShopName("Michael的小店");
		memberService.quickQueryShop(query);
	}

	public void testQueryByIds() {
		String[] memberIds = new String[2];
		memberIds[0] = "402881e41325997f011325997f670000";
		memberIds[1] = "402881e4134ee6ed01134ee6ed410000";
		memberService.queryMemberMapByIds(memberIds);
	}
}

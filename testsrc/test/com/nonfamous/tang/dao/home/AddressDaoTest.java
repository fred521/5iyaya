package test.com.nonfamous.tang.dao.home;

import java.util.List;

import test.com.nonfamous.tang.dao.DAOTestBase;

import com.nonfamous.commom.util.UUIDGenerator;
import com.nonfamous.tang.dao.home.AddressDAO;
import com.nonfamous.tang.domain.Address;

public class AddressDaoTest extends DAOTestBase{
	private AddressDAO addressDao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		addressDao = (AddressDAO) this.daoBeanFactory
				.getBean("addressDAO");
	}

	@Override
	protected void tearDown() throws Exception {
		addressDao = null;
		super.tearDown();
	}
	
	public void testSimpleCURD(){
		Address address = newAddress();
		
		addressDao.insertAddress(address);
		
		Address result = addressDao.getAddressById(address.getAddressId());
		assertEquals(address, result);
		
		address.setConsignee("liujun");
		address.setAreaCode("330508");
		address.setPhone("0571-88888888");
		address.setMobile("15858155485");
		address.setPostCode("310005");
		address.setStatus("T");
		address.setStreetAddress("XiHuanLu");
		addressDao.updateAddress(address);
		
		result = addressDao.getAddressById(address.getAddressId());
		assertEquals(address.getConsignee(), result.getConsignee());
		assertEquals(address.getAreaCode(), result.getAreaCode());
		assertEquals(address.getPhone(), result.getPhone());
		assertEquals(address.getMobile(), result.getMobile());
		assertEquals(address.getPostCode(), result.getPostCode());
		assertEquals(address.getStatus(), result.getStatus());
		assertEquals(address.getStreetAddress(), result.getStreetAddress());
		
		addressDao.deleteAddress(address.getAddressId());
		result = addressDao.getAddressById(address.getAddressId());
		assertNull(result);
		
	}

	public void testGetAddressListByMemberId(){
		Address addr1 = newAddress();
		Address addr2 = newAddress();
		Address addr3 = newAddress();
		
		addressDao.insertAddress(addr1);
		addressDao.insertAddress(addr2);
		addressDao.insertAddress(addr3);
		
		Address result1 = addressDao.getAddressById(addr1.getAddressId());
		assertEquals(addr1, result1);
		
		Address result2 = addressDao.getAddressById(addr2.getAddressId());
		assertEquals(addr2, result2);
		
		Address result3 = addressDao.getAddressById(addr3.getAddressId());
		assertEquals(addr3, result3);
		
		List<Address> addressList = addressDao.getAddressListByMemberId(addr1.getMemberId());
		assertTrue(addressList.size() == 3);
		
		addressDao.deleteAddress(addr1.getAddressId());
		addressDao.deleteAddress(addr2.getAddressId());
		addressDao.deleteAddress(addr3.getAddressId());
	}

	public void testDeleteAddressesByMemberId(){
		Address addr1 = newAddress();
		Address addr2 = newAddress();
		Address addr3 = newAddress();
		
		addressDao.insertAddress(addr1);
		addressDao.insertAddress(addr2);
		addressDao.insertAddress(addr3);
		
		Address result1 = addressDao.getAddressById(addr1.getAddressId());
		assertEquals(addr1, result1);
		
		Address result2 = addressDao.getAddressById(addr2.getAddressId());
		assertEquals(addr2, result2);
		
		Address result3 = addressDao.getAddressById(addr3.getAddressId());
		assertEquals(addr3, result3);
		
		List<Address> addressList = addressDao.getAddressListByMemberId(addr1.getMemberId());
		assertTrue(addressList.size() == 3);
		
		addressDao.deleteAddressesByMemberId(addr1.getMemberId());
	}
	
	private Address newAddress() {
		Address address = new Address();
		address.setAddressId(UUIDGenerator.generate());
		address.setConsignee("刘军");
		address.setMemberId("testmemberid64");
		address.setAreaCode("330108");
		address.setPhone("0571-88889999");
		address.setMobile("13858554615");
		address.setPostCode("310005");
		address.setStatus("N");
		address.setStreetAddress("西环路");
		address.setModifier("ff8080811d807aa9011d807aa9f20000");
		return address;
	}

}

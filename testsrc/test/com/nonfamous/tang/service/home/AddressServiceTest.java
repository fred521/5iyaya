package test.com.nonfamous.tang.service.home;

import java.util.List;

import com.nonfamous.commom.util.UUIDGenerator;
import com.nonfamous.tang.domain.Address;
import com.nonfamous.tang.service.home.AddressService;

import test.com.nonfamous.tang.service.ServiceTestBase;

public class AddressServiceTest extends ServiceTestBase{
	
	private AddressService addressService;

	public void setUp() throws Exception{
		super.setUp();
		addressService = (AddressService)serviceBeanFactory.getBean("addressService");
	}
	
	public void tearDown() throws Exception{
		super.tearDown();
		addressService = null;
	}
	
	public void testSimpleCURD(){
		Address address = newAddress();
		
		addressService.addAddress(address);
		
		Address result = addressService.getAddressById(address.getAddressId());
		assertEquals(address, result);
		
		address.setConsignee("liujun");
		address.setAreaCode("330508");
		address.setPhone("0571-88888888");
		address.setMobile("15858155485");
		address.setPostCode("310005");
		address.setStatus("T");
		address.setStreetAddress("XiHuanLu");
		addressService.updateAddress(address);
		
		result = addressService.getAddressById(address.getAddressId());
		assertEquals(address.getConsignee(), result.getConsignee());
		assertEquals(address.getAreaCode(), result.getAreaCode());
		assertEquals(address.getPhone(), result.getPhone());
		assertEquals(address.getMobile(), result.getMobile());
		assertEquals(address.getPostCode(), result.getPostCode());
		assertEquals(address.getStatus(), result.getStatus());
		assertEquals(address.getStreetAddress(), result.getStreetAddress());
		
		addressService.deleteAddress(address.getAddressId());
		result = addressService.getAddressById(address.getAddressId());
		assertNull(result);
	}
	
	public void testGetAddressListByMemberId(){
		Address addr1 = newAddress();
		Address addr2 = newAddress();
		Address addr3 = newAddress();
		
		addressService.addAddress(addr1);
		addressService.addAddress(addr2);
		addressService.addAddress(addr3);
		
		Address result1 = addressService.getAddressById(addr1.getAddressId());
		assertEquals(addr1, result1);
		
		Address result2 = addressService.getAddressById(addr2.getAddressId());
		assertEquals(addr2, result2);
		
		Address result3 = addressService.getAddressById(addr3.getAddressId());
		assertEquals(addr3, result3);
		
		List<Address> addressList = addressService.getAddressListByMemberId(addr1.getMemberId());
		assertTrue(addressList.size() == 3);
		
		addressService.deleteAddress(addr1.getAddressId());
		addressService.deleteAddress(addr2.getAddressId());
		addressService.deleteAddress(addr3.getAddressId());
	}

	public void testDeleteAddressesByMemberId(){
		Address addr1 = newAddress();
		Address addr2 = newAddress();
		Address addr3 = newAddress();
		
		addressService.addAddress(addr1);
		addressService.addAddress(addr2);
		addressService.addAddress(addr3);
		
		Address result1 = addressService.getAddressById(addr1.getAddressId());
		assertEquals(addr1, result1);
		
		Address result2 = addressService.getAddressById(addr2.getAddressId());
		assertEquals(addr2, result2);
		
		Address result3 = addressService.getAddressById(addr3.getAddressId());
		assertEquals(addr3, result3);
		
		List<Address> addressList = addressService.getAddressListByMemberId(addr1.getMemberId());
		assertTrue(addressList.size() == 3);
		
		addressService.deleteAddressesByMemberId(addr1.getMemberId());
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

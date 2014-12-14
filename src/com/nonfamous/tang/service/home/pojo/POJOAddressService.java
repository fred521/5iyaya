package com.nonfamous.tang.service.home.pojo;

import java.util.List;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.UUIDGenerator;
import com.nonfamous.commom.util.service.POJOServiceBase;
import com.nonfamous.tang.dao.home.AddressDAO;
import com.nonfamous.tang.domain.Address;
import com.nonfamous.tang.service.home.AddressService;

public class POJOAddressService extends POJOServiceBase implements AddressService {

	private AddressDAO addressDao;
	
	public String addAddress(Address address) {
		if(StringUtils.isBlank(address.getAddressId()))
			address.setAddressId(UUIDGenerator.generate());
		if(StringUtils.isBlank(address.getModifier()))
			address.setModifier(address.getMemberId());
		addressDao.insertAddress(address);
		return address.getAddressId();
	}

	public int deleteAddress(String addressId) {
		return addressDao.deleteAddress(addressId);
	}

	public Address getAddressById(String addressId) {
		return addressDao.getAddressById(addressId);
	}

	public List<Address> getAddressListByMemberId(String memberId) {
		return addressDao.getAddressListByMemberId(memberId);
	}

	public int getAddressCountByMemberId(String memberId) {
		return addressDao.getAddressCountByMemberId(memberId);
	}

	public int deleteAddressesByMemberId(String memberId) {
		return addressDao.deleteAddressesByMemberId(memberId);
	}

	public void updateAddress(Address address) {
		addressDao.updateAddress(address);
	}

	public AddressDAO getAddressDao() {
		return addressDao;
	}

	public void setAddressDao(AddressDAO addressDao) {
		this.addressDao = addressDao;
	}
}

package com.nonfamous.tang.dao.home.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nonfamous.commom.util.ValidateUtils;
import com.nonfamous.tang.dao.home.AddressDAO;
import com.nonfamous.tang.domain.Address;

public class IbatisAddressDAO  extends SqlMapClientDaoSupport implements AddressDAO{

	public static final String ADDRESS_SPACE    = "ADDRESS_SPACE.";

	public int deleteAddress(String addressId) {
		ValidateUtils.notBlank(addressId, "addressId");
		return getSqlMapClientTemplate().delete(ADDRESS_SPACE + "delete_address", addressId);
	}

	public Address getAddressById(String addressId) {
		ValidateUtils.notNull(addressId, "addressId");
		return (Address)getSqlMapClientTemplate().queryForObject(ADDRESS_SPACE + "get_address_by_id", addressId);
	}

	public void insertAddress(Address address) {
		ValidateUtils.notNull(address, "address");
		this.getSqlMapClientTemplate().insert(ADDRESS_SPACE + "insert_address", address);
	}

	public void updateAddress(Address address) {
		ValidateUtils.notNull(address, "address");
		this.getSqlMapClientTemplate().update(ADDRESS_SPACE + "update_address", address);
	}

	@SuppressWarnings("unchecked")
	public List<Address> getAddressListByMemberId(String memberId) {
		ValidateUtils.notBlank(memberId, "memberId");
		return this.getSqlMapClientTemplate().queryForList(ADDRESS_SPACE + "get_address_by_member_id", memberId);
	}

	public int getAddressCountByMemberId(String memberId){
		ValidateUtils.notBlank(memberId, "memberId");
		Integer count = (Integer)this.getSqlMapClientTemplate().queryForObject(ADDRESS_SPACE + "get_address_count_by_member_id", memberId); 
		return count.intValue();
	}
	
	public int deleteAddressesByMemberId(String memberId) {
		ValidateUtils.notBlank(memberId, "memberId");
		return getSqlMapClientTemplate().delete(ADDRESS_SPACE + "delete_addresses_by_member_id", memberId);
	}
}

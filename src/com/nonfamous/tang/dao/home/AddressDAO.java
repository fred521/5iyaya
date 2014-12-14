package com.nonfamous.tang.dao.home;

import java.util.List;

import com.nonfamous.tang.domain.Address;

/**
 * 
 * @author liujun
 * 
 * <pre>
 * 地址管理dao
 * </pre>
 */
public interface AddressDAO {
	
	/**
	 * 增加Address
	 * @param address
	 * @return
	 */
	public void insertAddress(Address address);
	
	/**
	 * 更新Address
	 * @param address
	 * @return
	 */
	public void updateAddress(Address address);
	
	/**
	 * 通过ID获取Address
	 * @param addressId
	 * @return
	 */
	public Address getAddressById(String addressId);
	
	/**
	 * 通过会员ID获取Address列表
	 * @param memberId
	 * @return
	 */
	public List<Address> getAddressListByMemberId(String memberId);
	
	/**
	 * 通过会员ID获取Address总数
	 * @param memberId
	 * @return
	 */
	public int getAddressCountByMemberId(String memberId);
	
	/**
	 * 通过ID删除Address
	 * @param addressId
	 * @return
	 */
	public int deleteAddress(String addressId);
	
	/**
	 * 通过会员ID删除所有地址
	 * @param memberId
	 * @return
	 */
	public int deleteAddressesByMemberId(String memberId);
	
}

package com.nonfamous.tang.service.home;

import java.util.List;

import com.nonfamous.tang.domain.Address;

/**
 * 买家地址管理
 * 
 * @author liujun
 *
 */
public interface AddressService {
	/**
	 * 增加地址
	 * @param address
	 */
	public String addAddress(Address address);
	
	/**
	 * 修改地址
	 * @param address
	 */
	public void updateAddress(Address address);
	
	/**
	 * 删除地址
	 * @param addressId
	 * @return
	 */
	public int deleteAddress(String addressId);
	
	/**
	 * 通过地址ID获取地址
	 * @param addressId
	 * @return
	 */
	public Address getAddressById(String addressId);
	
	/**
	 * 通过会员ID获取地址列表
	 * @param memberId
	 * @return
	 */
	public List<Address> getAddressListByMemberId(String memberId);

	/**
	 * 通过会员ID获取地址总数
	 * @param member
	 * @return
	 */
	public int getAddressCountByMemberId(String member);
	
	/**
	 * 通过会员ID删除地址
	 * @param memberId
	 * @return
	 */
	public int deleteAddressesByMemberId(String memberId);
	
}

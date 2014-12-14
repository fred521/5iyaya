package com.nonfamous.tang.dao.home;

import java.util.List;

import com.nonfamous.tang.domain.Address;

/**
 * 
 * @author liujun
 * 
 * <pre>
 * ��ַ����dao
 * </pre>
 */
public interface AddressDAO {
	
	/**
	 * ����Address
	 * @param address
	 * @return
	 */
	public void insertAddress(Address address);
	
	/**
	 * ����Address
	 * @param address
	 * @return
	 */
	public void updateAddress(Address address);
	
	/**
	 * ͨ��ID��ȡAddress
	 * @param addressId
	 * @return
	 */
	public Address getAddressById(String addressId);
	
	/**
	 * ͨ����ԱID��ȡAddress�б�
	 * @param memberId
	 * @return
	 */
	public List<Address> getAddressListByMemberId(String memberId);
	
	/**
	 * ͨ����ԱID��ȡAddress����
	 * @param memberId
	 * @return
	 */
	public int getAddressCountByMemberId(String memberId);
	
	/**
	 * ͨ��IDɾ��Address
	 * @param addressId
	 * @return
	 */
	public int deleteAddress(String addressId);
	
	/**
	 * ͨ����ԱIDɾ�����е�ַ
	 * @param memberId
	 * @return
	 */
	public int deleteAddressesByMemberId(String memberId);
	
}

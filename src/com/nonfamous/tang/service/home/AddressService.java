package com.nonfamous.tang.service.home;

import java.util.List;

import com.nonfamous.tang.domain.Address;

/**
 * ��ҵ�ַ����
 * 
 * @author liujun
 *
 */
public interface AddressService {
	/**
	 * ���ӵ�ַ
	 * @param address
	 */
	public String addAddress(Address address);
	
	/**
	 * �޸ĵ�ַ
	 * @param address
	 */
	public void updateAddress(Address address);
	
	/**
	 * ɾ����ַ
	 * @param addressId
	 * @return
	 */
	public int deleteAddress(String addressId);
	
	/**
	 * ͨ����ַID��ȡ��ַ
	 * @param addressId
	 * @return
	 */
	public Address getAddressById(String addressId);
	
	/**
	 * ͨ����ԱID��ȡ��ַ�б�
	 * @param memberId
	 * @return
	 */
	public List<Address> getAddressListByMemberId(String memberId);

	/**
	 * ͨ����ԱID��ȡ��ַ����
	 * @param member
	 * @return
	 */
	public int getAddressCountByMemberId(String member);
	
	/**
	 * ͨ����ԱIDɾ����ַ
	 * @param memberId
	 * @return
	 */
	public int deleteAddressesByMemberId(String memberId);
	
}

package com.nonfamous.commom.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import com.nonfamous.tang.exception.ServiceException;

/**
 * ��ַͨ����
 * <p>
 * ���ฺ��������ļ���ȡ��ַ��Ϣ�������ַ�����ṹ�������ṩ�ӿڹ��������ȡ��ַ��Ϣ�͵�ַת�����������и����ӵ�����ɿ��ǽ�������ʽ�ĳ����ݿ�������ʽ���Ƕ���ӿڲ���
 * 
 * �����ļ���ʽ�� �������,����,�ϼ��������,�ʱ�,�Ƿ����� ��5���ֶΣ��ֶμ��Զ��Ÿ���
 * </p>
 * 
 * @author daodao
 * @version $Id: AddressHelper.java,v 1.2 2009/05/04 05:17:35 jason Exp $
 */
public class AddressHelper {

	/** ��ͨ��־ */
	public final Log logger = LogFactory.getLog(this.getClass());

	/** ��ַ����ԭʼ��Ϣ�ļ� */
	private Resource addressFile;

	/** ʡ�б� */
	private List<AddressInfo> provList;

	/** ���ʱ��γɵ�ַ�����MAP */
	private Map<String, AddressInfo> postMap;

	/** ���������γɵ�ַ�����MAP */
	private Map<String, AddressInfo> areaCodeMap;

	/**
	 * ��ʼ����ַ��Ϣ
	 */
	public void init() throws IOException {

		if (addressFile == null) {
			throw new ServiceException("δ�������������ļ�");
		}

		postMap = new HashMap<String, AddressInfo>();
		areaCodeMap = new HashMap<String, AddressInfo>();
		provList = new ArrayList<AddressInfo>();
		logger.info("��ʼ��ʼ����ַ����ӳ���");

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				addressFile.getInputStream(), "GBK"));
		// ��ɴ��ļ������ж����ݵĹ���
		while (true) {
			String line = reader.readLine();

			if (line == null) {
				break;
			}

			if (StringUtils.isBlank(line)) {
				continue;
			}

			String[] parts = line.split(",");
			if (parts.length != 5) {
				logger.warn("��Ч�ĵ�ַ����������: " + line);
				continue;
			}

			// �����������Ĳ�����ĵ�ַ������

			String[] address_line = new String[5];
			for (int i = 0; i < parts.length; i++) {
				parts[i] = StringUtils.trimToNull(parts[i]);
				address_line[i] = parts[i];

			}
			// ���ݶ���������Ϣ���ɵ�ַ����
			AddressInfo addressInfo = new AddressInfo(address_line[0],
					address_line[1], address_line[2], address_line[3]);

			addressInfo.setDisabled((address_line[4].equals("Y")) ? false
					: true);
			// ���ݵ�����Ѹö������areaCodeMap��
			if (!StringUtils.isBlank(addressInfo.getAreaCode())) {
				areaCodeMap.put(addressInfo.getAreaCode(), addressInfo);
			}
			// ������������Ѹö������postMap��
			if (!StringUtils.isBlank(addressInfo.getPostcode())) {
				postMap.put(addressInfo.getPostcode(), addressInfo);
			}
			// �жϵ�ǰ������û�и��룬�������������ڸ���������б���
			String parentCode = addressInfo.getParentAreaCode();
			if (!StringUtils.isBlank(parentCode)) {
				AddressInfo parentAddress = (AddressInfo) areaCodeMap
						.get(parentCode);
				if (parentAddress == null) {
					logger.warn("���ϼ����룬���ϼ�������Ϣ�޷��ҵ�" + line);
					continue;
				}
				parentAddress.addChild(addressInfo);

			} else {
				provList.add(addressInfo);
			}
		}

		// ��¼��ʼ���ɹ���־��Ϣ
		logger.info("init addressHelper success");

	}

	/**
	 * ���ݵ��������õ�ַ��Ϣ
	 * 
	 * @param areaCode
	 * @return
	 */
	public AddressInfo findAddressByAreaCode(String areaCode) {
		if (StringUtils.isBlank(areaCode) || this.areaCodeMap == null) {
			return null;
		}
		AddressInfo info = (AddressInfo) areaCodeMap.get(areaCode);

		return info;

	}

	/**
	 * ����ʡ���С�������Ϣ��ȡ������ַ��Ϣ
	 * 
	 * @param prov
	 *            ������ʽ�����硰�㽭��
	 * @param city
	 *            ������ʽ�����硰���ݡ�
	 * @param area
	 *            ������ʽ�����硰�ϳ�����
	 * @return
	 */
	public AddressInfo getAreaCodeByProvCity(String prov, String city,
			String area) {
		if (StringUtils.isBlank(prov) || StringUtils.isBlank(city)
				|| this.provList == null) {
			return null;
		}

		for (int i = 0; i < this.provList.size(); i++) {
			AddressInfo info = (AddressInfo) provList.get(i);
			// �ҵ�ƥ���ʡ
			if (info.getName() != null && info.getName().equals(prov)) {
				List cities = info.getChildList();
				if (cities != null && cities.size() > 0) {
					for (int j = 0; j < cities.size(); j++) {
						info = (AddressInfo) cities.get(j);
						// �ҵ�ƥ�������Ϣ
						if (info.getName() != null
								&& info.getName().equals(city)) {
							// �������Ϊ�գ�����Ѱ��ƥ�������Ϣ
							if (StringUtils.isBlank(area)) {
								return info;
							}
							List areas = info.getChildList();
							if (areas != null && areas.size() > 0) {
								for (int k = 0; k < areas.size(); k++) {
									info = (AddressInfo) areas.get(k);
									if (info.getName() != null
											&& info.getName().equals(area)) {
										return info;
									}
								}
							}
						}
					}
				}
			}
		}
		// ʲô��û�ҵ����Ǿͷ��ؿ���
		return null;

	}

	/**
	 * ����ʡ��ȡ���б�
	 * 
	 * @param prov
	 *            ������ʽ�����硰�㽭��
	 * @return
	 */
	public List getCitiesByProv(String prov) {
		if (StringUtils.isBlank(prov) || this.provList == null) {
			return null;
		}

		for (int i = 0; i < this.provList.size(); i++) {
			AddressInfo provAddress = (AddressInfo) provList.get(i);
			// �ҵ�ƥ���ʡ
			if (provAddress.getName() != null
					&& provAddress.getName().equals(prov)) {
				return provAddress.getChildList();
			}
		}
		// ʲô��û�ҵ����Ǿͷ��ؿ���
		return null;

	}

	/**
	 * ����ʡ������Ϣ��ȡ���б�
	 * 
	 * @param prov
	 *            ������ʽ�����硰�㽭��
	 * @param city
	 *            ������ʽ�����硰���ݡ�
	 * @return
	 */
	public List getAreasByProvAndCity(String prov, String city) {
		if (StringUtils.isEmpty(prov) || StringUtils.isEmpty(city)
				|| this.provList == null) {
			return null;
		}

		for (int i = 0; i < this.provList.size(); i++) {
			AddressInfo provAddress = (AddressInfo) this.provList.get(i);
			// �ҵ�ƥ���ʡ
			if (provAddress.getName() != null
					&& provAddress.getName().equals(prov)) {
				List citis = provAddress.getChildList();
				if (citis != null && citis.size() > 0) {
					for (int j = 0; j < citis.size(); j++) {
						AddressInfo cityAddress = (AddressInfo) provAddress
								.getChildList().get(j);
						// �ҵ�ƥ�����
						if (cityAddress.getName() != null
								&& cityAddress.getName().equals(city)) {
							return cityAddress.getChildList();
						}
					}
				}
			}
		}

		// ʲô��û�ҵ����Ǿͷ��ؿ���
		return null;
	}

	/**
	 * �����ʱ��ȡ��ַ��Ϣ
	 * 
	 * @param postCode
	 * @return
	 */
	public AddressInfo findAddressByPostCode(String postCode) {
		if (logger.isDebugEnabled()) {
			logger.debug("������������" + postCode + "�����ƥ����Ϣ");
		}

		if (StringUtils.isBlank(postCode) || !StringUtils.isNumeric(postCode)
				|| postCode.length() != 6) {
			return null;
		}

		char[] chars = postCode.toCharArray();
		/*
		 * ��ǰ����λ������FOR
		 */
		for (int i = 5; i >= 1; i--) {
			if (chars[i] == '0' && i > 1) {
				continue;
			}

			AddressInfo info = (AddressInfo) postMap.get(new String(chars));
			if (info != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("�ڵ�" + (6 - i) + "���ҵ���������" + postCode
							+ "�����ƥ����Ϣ: " + info);
				}

				return info;
			}

			chars[i] = '0';
		}

		if (logger.isDebugEnabled()) {
			logger.debug("δ�ҵ���������" + postCode + "�����ƥ����Ϣ");
		}

		return null;
	}

	public static String convertAddressFormatBackToNormal(String addressOrderFormat){
		
		StringBuilder address = new StringBuilder();
		String[] addressInfo = addressOrderFormat.split("\\|");
		for(String addr : addressInfo){
			address.append(addr + " ");
		}
		return address.toString();
	}
	
	/**
	 * ������������е�ʡ�б�
	 * 
	 * @return
	 */
	public List<AddressInfo> getProvList() {
		return this.provList;
	}

	public void setAddressFile(Resource addressFile) {
		this.addressFile = addressFile;
	}
}

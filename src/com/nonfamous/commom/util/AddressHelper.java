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
 * 地址通用类
 * <p>
 * 该类负责从配置文件读取地址信息，构造地址的树结构，并且提供接口供其他类获取地址信息和地址转化。如果今后有更复杂的需求可考虑将配置形式改成数据库配置形式但是对外接口不变
 * 
 * 配置文件格式： 区域编码,名称,上级区域编码,邮编,是否在用 共5个字段，字段间以逗号隔开
 * </p>
 * 
 * @author daodao
 * @version $Id: AddressHelper.java,v 1.2 2009/05/04 05:17:35 jason Exp $
 */
public class AddressHelper {

	/** 普通日志 */
	public final Log logger = LogFactory.getLog(this.getClass());

	/** 地址编码原始信息文件 */
	private Resource addressFile;

	/** 省列表 */
	private List<AddressInfo> provList;

	/** 按邮编形成地址对象的MAP */
	private Map<String, AddressInfo> postMap;

	/** 按地区码形成地址对象的MAP */
	private Map<String, AddressInfo> areaCodeMap;

	/**
	 * 初始化地址信息
	 */
	public void init() throws IOException {

		if (addressFile == null) {
			throw new ServiceException("未配置邮政编码文件");
		}

		postMap = new HashMap<String, AddressInfo>();
		areaCodeMap = new HashMap<String, AddressInfo>();
		provList = new ArrayList<AddressInfo>();
		logger.info("开始初始化地址编码映射表。");

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				addressFile.getInputStream(), "GBK"));
		// 完成从文件中逐行读数据的功能
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
				logger.warn("无效的地址编码配置行: " + line);
				continue;
			}

			// 将解析出来的不规则的地址编码规格化

			String[] address_line = new String[5];
			for (int i = 0; i < parts.length; i++) {
				parts[i] = StringUtils.trimToNull(parts[i]);
				address_line[i] = parts[i];

			}
			// 根据读出来的信息生成地址对象
			AddressInfo addressInfo = new AddressInfo(address_line[0],
					address_line[1], address_line[2], address_line[3]);

			addressInfo.setDisabled((address_line[4].equals("Y")) ? false
					: true);
			// 根据地区码把该对象放在areaCodeMap中
			if (!StringUtils.isBlank(addressInfo.getAreaCode())) {
				areaCodeMap.put(addressInfo.getAreaCode(), addressInfo);
			}
			// 根据邮政编码把该对象放在postMap中
			if (!StringUtils.isBlank(addressInfo.getPostcode())) {
				postMap.put(addressInfo.getPostcode(), addressInfo);
			}
			// 判断当前对象有没有父码，如果有则把他放在父对象的子列表中
			String parentCode = addressInfo.getParentAreaCode();
			if (!StringUtils.isBlank(parentCode)) {
				AddressInfo parentAddress = (AddressInfo) areaCodeMap
						.get(parentCode);
				if (parentAddress == null) {
					logger.warn("有上级编码，但上级区域信息无法找到" + line);
					continue;
				}
				parentAddress.addChild(addressInfo);

			} else {
				provList.add(addressInfo);
			}
		}

		// 记录初始化成功日志信息
		logger.info("init addressHelper success");

	}

	/**
	 * 根据地区编码获得地址信息
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
	 * 根据省、市、区的信息获取地区地址信息
	 * 
	 * @param prov
	 *            中文形式，例如“浙江”
	 * @param city
	 *            中文形式，例如“杭州”
	 * @param area
	 *            中文形式，例如“上城区”
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
			// 找到匹配的省
			if (info.getName() != null && info.getName().equals(prov)) {
				List cities = info.getChildList();
				if (cities != null && cities.size() > 0) {
					for (int j = 0; j < cities.size(); j++) {
						info = (AddressInfo) cities.get(j);
						// 找到匹配的市信息
						if (info.getName() != null
								&& info.getName().equals(city)) {
							// 如果区不为空，继续寻找匹配的区信息
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
		// 什么都没找到，那就返回空了
		return null;

	}

	/**
	 * 根据省获取市列表
	 * 
	 * @param prov
	 *            中文形式，例如“浙江”
	 * @return
	 */
	public List getCitiesByProv(String prov) {
		if (StringUtils.isBlank(prov) || this.provList == null) {
			return null;
		}

		for (int i = 0; i < this.provList.size(); i++) {
			AddressInfo provAddress = (AddressInfo) provList.get(i);
			// 找到匹配的省
			if (provAddress.getName() != null
					&& provAddress.getName().equals(prov)) {
				return provAddress.getChildList();
			}
		}
		// 什么都没找到，那就返回空了
		return null;

	}

	/**
	 * 根据省、市信息获取区列表
	 * 
	 * @param prov
	 *            中文形式，例如“浙江”
	 * @param city
	 *            中文形式，例如“杭州”
	 * @return
	 */
	public List getAreasByProvAndCity(String prov, String city) {
		if (StringUtils.isEmpty(prov) || StringUtils.isEmpty(city)
				|| this.provList == null) {
			return null;
		}

		for (int i = 0; i < this.provList.size(); i++) {
			AddressInfo provAddress = (AddressInfo) this.provList.get(i);
			// 找到匹配的省
			if (provAddress.getName() != null
					&& provAddress.getName().equals(prov)) {
				List citis = provAddress.getChildList();
				if (citis != null && citis.size() > 0) {
					for (int j = 0; j < citis.size(); j++) {
						AddressInfo cityAddress = (AddressInfo) provAddress
								.getChildList().get(j);
						// 找到匹配的市
						if (cityAddress.getName() != null
								&& cityAddress.getName().equals(city)) {
							return cityAddress.getChildList();
						}
					}
				}
			}
		}

		// 什么都没找到，那就返回空了
		return null;
	}

	/**
	 * 根据邮编获取地址信息
	 * 
	 * @param postCode
	 * @return
	 */
	public AddressInfo findAddressByPostCode(String postCode) {
		if (logger.isDebugEnabled()) {
			logger.debug("查找邮政编码" + postCode + "的最佳匹配信息");
		}

		if (StringUtils.isBlank(postCode) || !StringUtils.isNumeric(postCode)
				|| postCode.length() != 6) {
			return null;
		}

		char[] chars = postCode.toCharArray();
		/*
		 * 最前面两位不参与FOR
		 */
		for (int i = 5; i >= 1; i--) {
			if (chars[i] == '0' && i > 1) {
				continue;
			}

			AddressInfo info = (AddressInfo) postMap.get(new String(chars));
			if (info != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("在第" + (6 - i) + "轮找到邮政编码" + postCode
							+ "的最佳匹配信息: " + info);
				}

				return info;
			}

			chars[i] = '0';
		}

		if (logger.isDebugEnabled()) {
			logger.debug("未找到邮政编码" + postCode + "的最佳匹配信息");
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
	 * 获得配置中所有的省列表
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

package com.nonfamous.tang.web.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nonfamous.commom.form.DefaultFormFactory;
import com.nonfamous.commom.form.Form;
import com.nonfamous.commom.util.AddressHelper;
import com.nonfamous.commom.util.AddressInfo;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.commom.util.web.cookyjar.Cookyjar;
import com.nonfamous.tang.domain.Address;
import com.nonfamous.tang.service.home.AddressService;
import com.nonfamous.tang.web.common.Constants;

public class AddressController extends MultiActionController {
	
	private AddressService addressService;

	private DefaultFormFactory formFactory;

	private AddressHelper addressHelper;
	
	/**
	 * ��ҵ�ַ������ҳ
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception{
		RequestValueParse rvp = new RequestValueParse(request);
		ModelAndView mv = new ModelAndView("/home/address/index");
		Cookyjar cookyjar = rvp.getCookyjar();
		String memberId = cookyjar.get(Constants.MemberId_Cookie);
		prepareAddresses(mv, memberId);
		return mv;
	}

	private void prepareAddresses(ModelAndView mv, String memberId) {
		List<Address> addressList = addressService.getAddressListByMemberId(memberId);
		for(Address address : addressList){
			AddressInfo addressInfo1 = addressHelper.findAddressByAreaCode(address.getAreaCode());
			AddressInfo addressInfo2 = addressHelper.findAddressByAreaCode(addressInfo1.getParentAreaCode());
			AddressInfo addressInfo3 = addressHelper.findAddressByAreaCode(addressInfo2.getParentAreaCode());
			address.setAreaCodeStr(addressInfo3.getName() + " " + addressInfo2.getName() + " " + addressInfo1.getName());
		}
		mv.addObject("addressList", addressList);
	}
	
	/**
	 * ������ҵ�ַ
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception{
		RequestValueParse rvp = new RequestValueParse(request);
		ModelAndView mv = new ModelAndView("/home/address/index");
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		if(addressService.getAddressCountByMemberId(memberId) >= 5){
			prepareAddresses(mv, memberId);
			mv.addObject("action", "add");
			mv.addObject("errorMessage", "���ֻ������5����Ч��ַ!");
			return mv;
		}
		Form form = formFactory.getForm("address", request);
		if(!form.isValide()){
			prepareAddresses(mv, memberId);
			mv.addObject("action", "add");
			mv.addObject("form", form);
			return mv;
		}
		Address address = new Address();
		bind(request, address);
		address.setMemberId(memberId);
		address.setStatus(Address.TEMP_STATUS);
		address.setAreaCode(rvp.getParameter("areaCode").getString());
		addressService.addAddress(address);
		
		prepareAddresses(mv, memberId);
		mv.addObject("succMessage", "���ӵ�ַ�ɹ�.");
		return mv;
	}
	
	/**
	 * ������ҵ�ַ
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws Exception{
		RequestValueParse rvp = new RequestValueParse(request);
		ModelAndView mv = new ModelAndView("/home/address/index");
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		Form form = formFactory.getForm("address", request);
		if(!form.isValide()){
			prepareAddresses(mv, memberId);
			mv.addObject("action", "update");
			mv.addObject("form", form);
			return mv;
		}
		Address address = new Address();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(address);
		binder.bind(request);
		address.setMemberId(memberId);
		address.setStatus(Address.TEMP_STATUS);
		address.setAreaCode(rvp.getParameter("areaCode").getString());
		addressService.updateAddress(address);
		
		prepareAddresses(mv, memberId);
		mv.addObject("succMessage", "���µ�ַ�ɹ�.");
		return mv;
	}
	
	/**
	 * ɾ����ҵ�ַ
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		RequestValueParse rvp = new RequestValueParse(request);
		ModelAndView mv = new ModelAndView("/home/address/index");
		String memberId = rvp.getCookyjar().get(Constants.MemberId_Cookie);
		String addressId = rvp.getParameter("addressId").getString();
		int res = addressService.deleteAddress(addressId);
		if(res <= 0){
			prepareAddresses(mv, memberId);
			mv.addObject("action", "delete");
			mv.addObject("errorMessage", "ɾ����ַʧ�ܣ�������!");
		}
		prepareAddresses(mv, memberId);
		return mv;
	}
	
	public AddressService getAddressService() {
		return addressService;
	}

	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}

	public void setFormFactory(DefaultFormFactory formFactory) {
		this.formFactory = formFactory;
	}

	public void setAddressHelper(AddressHelper addressHelper) {
		this.addressHelper = addressHelper;
	}
	
	
}

package com.nonfamous.tang.web.validator;

import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestUtils;

import com.nonfamous.commom.form.FieldConfig;
import com.nonfamous.commom.form.Form;
import com.nonfamous.commom.form.FormFactory;
import com.nonfamous.tang.domain.GoodsBaseInfo;

public class AddGoodsValidator {

	private FormFactory factory;
	private HttpServletRequest request;
	private Form form;
	
	public void setFactory(FormFactory factory) {
		this.factory = factory;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public Form getForm() {
		return form;
	}
	public void setForm(Form form) {
		this.form = form;
	}
	/**
	 * Validate method.
	 * 
	 * @return
	 */
	public boolean validate(){
		form = factory.getForm("addGoods", request);
		String goodsType = ServletRequestUtils.getStringParameter(
				request, "goodsType", GoodsBaseInfo.NORMAL_TYPE);
		if(GoodsBaseInfo.NORMAL_TYPE.equals(goodsType)){
			form.getField("groupPrice").setFieldConfigs(new ArrayList<FieldConfig>());
			form.getField("groupDefaultNum").setFieldConfigs(new ArrayList<FieldConfig>());
		}
		else if(GoodsBaseInfo.TEAM_TYPE.equals(goodsType)){
			form.getField("batchPrice").setFieldConfigs(new ArrayList<FieldConfig>());
			form.getField("batchNum").setFieldConfigs(new ArrayList<FieldConfig>());
		}
		else if(GoodsBaseInfo.MIXED_TYPE.equals(goodsType)){
			//ignore since there is already configured validation.
		}
		return form.isValide();
	}
}

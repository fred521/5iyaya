package com.nonfamous.commom.util.web;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.web.servlet.view.velocity.VelocityConfig;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

/**
 * @author eyeieye
 * 
 */
public class NofmsVelocityConfigurer extends VelocityConfigurer/*VelocityEngineFactory*/ implements
		VelocityConfig, InitializingBean, ResourceLoaderAware {

	private String macroFile;
	
	protected void postProcessVelocityEngine(VelocityEngine velocityEngine) {
		super.postProcessVelocityEngine(velocityEngine);
		if (macroFile != null) {
			velocityEngine.addProperty(VelocityEngine.VM_LIBRARY,
					macroFile);
			if (logger.isInfoEnabled()) {
				logger.info("Load nonfamousMacro from:" + macroFile);
			}
		}
	}

	public String getMacroFile() {
		return macroFile;
	}

	public void setMacroFile(String macroFile) {
		this.macroFile = macroFile;
	}

}
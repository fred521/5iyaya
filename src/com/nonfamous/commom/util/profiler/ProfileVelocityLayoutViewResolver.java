package com.nonfamous.commom.util.profiler;

import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;

/**
 * @author eyeieye
 * 
 */
public class ProfileVelocityLayoutViewResolver extends
		VelocityLayoutViewResolver {

	@Override
	protected Class requiredViewClass() {
		return ProfileVelocityLayoutView.class;
	}

}

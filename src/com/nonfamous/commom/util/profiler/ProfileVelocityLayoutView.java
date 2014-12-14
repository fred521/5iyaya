package com.nonfamous.commom.util.profiler;

import org.apache.velocity.Template;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;

/**
 * ��veloctiry��templateִ��ʱ����и���
 * 
 * @author fish
 * 
 */
public class ProfileVelocityLayoutView extends VelocityLayoutView {

	@Override
	protected Template getTemplate(String name) throws Exception {
		Template template = super.getTemplate(name);
		return new TemplateWithProfile(template);
	}

}

package com.nonfamous.commom.util.profiler;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * 对velocity的template进行增强，可记录渲染的执行时间
 * 
 * @author fish
 * 
 */
public class TemplateWithProfile extends Template {
	private Template target = null;

	public TemplateWithProfile(Template t) {
		target = t;
	}

	@Override
	public void merge(Context context, Writer writer)
			throws ResourceNotFoundException, ParseErrorException,
			MethodInvocationException, IOException {
		if (!TimeProfiler.isProfileEnable()) {
			target.merge(context, writer);
		} else {
			mergeWithProfile(context, writer);
		}

	}

	private void mergeWithProfile(Context context, Writer writer)
			throws ResourceNotFoundException, ParseErrorException,
			MethodInvocationException, IOException {
		TimeProfiler.beginTask("render:" + target.getName());
		try {
			target.merge(context, writer);
		} finally {
			TimeProfiler.endTask();
		}
	}
}

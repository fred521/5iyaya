package com.nonfamous.commom.util.profiler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * ���ܼ�⣬����ָ��ֵ������������¼�� �����TimeProfiler ��log
 * level��Ϊinfo���������Ҫ��Ϣ�������Ϊdebug���������ϸ��Ϣ
 * 
 * @author fish
 * 
 */

public class TimeProfiler {

	private static final Log logger = LogFactory.getLog(TimeProfiler.class);

	private static final int Default_Threshold = 100;// default value

	private String id;

	private int threshold;

	private long begin = 0;

	private long end = 0;

	private List<ProfilerTask> tasks = new ArrayList<ProfilerTask>();

	private ProfilerTask currentTask;

	private static final ThreadLocal<TimeProfiler> local = new ThreadLocal<TimeProfiler>();

	/**
	 * 
	 * @param id
	 * @param threshold
	 *            �������ֵ�����н�����¼
	 */
	private TimeProfiler(String id, int threshold) {
		this.id = id;
		this.threshold = threshold;
	}

	/**
	 * ��ǰ�����Ƿ�����profile
	 * 
	 * @return
	 */
	public static boolean isProfileEnable() {
		return logger.isInfoEnabled() || logger.isDebugEnabled();
	}

	/**
	 * ��ʼһ��profiler����
	 * 
	 * @param name
	 *            ��������
	 * @param threshold
	 *            ��������ֵ�Ľ�����¼
	 * @return
	 */
	public static TimeProfiler start(String name, int threshold) {
		if (local.get() != null) {
			throw new IllegalStateException(
					"Can't start TimeProfiler: it's already running");
		}
		TimeProfiler profiler = new TimeProfiler(name, threshold);
		local.set(profiler);
		profiler.begin = System.currentTimeMillis();
		return profiler;
	}

	/**
	 * ʹ��ȱʡֵ��ʼһ��profiler����
	 * 
	 * @param name
	 * @return
	 */
	public static TimeProfiler start(String name) {
		return start(name, Default_Threshold);
	}

	/**
	 * ����������
	 * 
	 */
	public void release() {
		release(null);
	}

	/**
	 * ���������񣬲����ڴ�ȷ�����������
	 * 
	 * @param name
	 */
	public void release(String name) {
		local.remove();
		this.end = System.currentTimeMillis();
		logProfiler(name);
	}

	private void logProfiler(String name) {
		if (getTotalTimeMillis() < this.threshold) {
			return;
		}
		if (logger.isDebugEnabled()) {
			logger.debug(name == null ? prettyPrint() : prettyPrint(name));
		} else {
			if (logger.isInfoEnabled()) {
				logger.info(name == null ? shortSummary() : shortSummary(name));
			}

		}
	}

	private long getTotalTimeMillis() {
		return end - begin;
	}

	private String shortSummary(String name) {
		StringBuffer sb = new StringBuffer();
		sb.append("TimeProfiler output:").append("\r\n");
		sb.append("Entire running time(millis)=").append(getTotalTimeMillis());
		sb.append(" in ").append(name);
		return sb.toString();
	}

	private String shortSummary() {
		return this.shortSummary(this.id);
	}

	private String prettyPrint(String name) {
		StringBuffer sb = new StringBuffer(shortSummary(name));
		for (ProfilerTask task : this.tasks) {
			sb.append("\r\n").append(task.prettyPrint());
		}
		return sb.toString();
	}

	private String prettyPrint() {
		StringBuffer sb = new StringBuffer(shortSummary());
		for (ProfilerTask task : this.tasks) {
			sb.append("\r\n").append(task.prettyPrint());
		}
		return sb.toString();
	}

	/**
	 * ��ʼһ�����������û������profile����˷���������profile����
	 * 
	 * @param string
	 *            ����������
	 */
	public static void beginTask(String string) {
		TimeProfiler profiler = local.get();
		boolean iFireIt = false;
		if (profiler == null) {
			profiler = start(string);
			iFireIt = true;
		}
		ProfilerTask task = profiler.new ProfilerTask(string);
		task.fire = iFireIt;
		profiler.addTask(task);
		task.start();
	}

	private void addTask(ProfilerTask task) {
		if (this.currentTask == null) {// û�а�����һ�������������У�Ҳ����һ����������
			this.tasks.add(task);
		} else {
			this.currentTask.addChildTask(task);
		}
		this.currentTask = task;
	}

	/**
	 * ������ǰ������
	 * 
	 */
	public static void endTask() {
		TimeProfiler profiler = local.get();
		if (profiler == null) {
			throw new IllegalStateException(
					"Can't end ProfilerTask: TimeProfiler is not running");
		}
		if (profiler.currentTask == null) {
			throw new IllegalStateException(
					"Can't end ProfilerTask: currentTask is null");
		}
		ProfilerTask current = profiler.currentTask;
		current.end();
		boolean iFireIt = current.fire;
		profiler.currentTask = profiler.currentTask.getParentTask();
		if (iFireIt) {
			profiler.release();
		}
	}

	private class ProfilerTask {

		private ProfilerTask parent;

		private List<ProfilerTask> children;

		private String name;

		private long start;

		private long end;

		private int deep = 1;

		private boolean fire = false;;// �Ƿ�����task������time Profiler

		public ProfilerTask(String n) {
			this.name = n;
		}

		public StringBuffer prettyPrint() {
			StringBuffer sb = new StringBuffer();
			sb.append("Task:").append(this.name);
			sb.append(" running time(millis)=").append(getTotalTimeMillis());
			double rate = (double) this.getTotalTimeMillis()
					/ TimeProfiler.this.getTotalTimeMillis();
			rate = rate * 100;
			sb.append(" ").append((int) rate).append('%');
			if (this.children != null) {
				for (ProfilerTask child : this.children) {
					sb.append("\r\n");
					for (int i = 0; i < this.deep; i++) {
						sb.append("--");
					}
					sb.append(child.prettyPrint());
				}
			}
			return sb;
		}

		public void start() {
			start = System.currentTimeMillis();
		}

		public void addChildTask(ProfilerTask task) {
			if (children == null) {
				children = new ArrayList<ProfilerTask>();
			}
			children.add(task);
			task.parent = this;
			task.deep = this.deep + 1;
		}

		public void end() {
			end = System.currentTimeMillis();
		}

		public ProfilerTask getParentTask() {
			return parent;
		}

		public String getName() {
			return this.name;
		}

		public long getTotalTimeMillis() {
			return end - start;
		}

	}
}

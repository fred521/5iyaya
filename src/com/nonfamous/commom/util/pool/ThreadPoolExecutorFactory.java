package com.nonfamous.commom.util.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 将新的java concurrent 包中的ThreadPool变成Spring中配置可用的bean
 * 
 * @author fred
 * @version $Id: ThreadPoolExecutorFactory.java,v 1.1 2007/06/16 02:23:50 fish
 *          Exp $
 */
public class ThreadPoolExecutorFactory implements FactoryBean, InitializingBean {
	private static final Log logger = LogFactory
			.getLog(ThreadPoolExecutorFactory.class);

	private int corePoolSize = 10;

	private int maximumPoolSize = 20;

	private int keepAliveTime = 50;

	private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

	private BlockingQueue<Runnable> workQueue = null;

	private ThreadFactory threadFactory = null;

	private RejectedExecutionHandler handler = null;

	/**
	 * workQueue 缺省为SynchronousQueue threadFactory, handler 可以为null
	 */
	public void afterPropertiesSet() throws Exception {
		if (this.workQueue == null) {
			this.workQueue = new SynchronousQueue<Runnable>();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("afterPropertiesSet:"
					+ ToStringBuilder.reflectionToString(this));
		}
	}

	public Object getObject() throws Exception {
		ThreadPoolExecutor executor = null;
		if (threadFactory == null && handler == null) {
			executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
					keepAliveTime, timeUnit, workQueue);
		} else {
			if (threadFactory == null && handler != null) {
				executor = new ThreadPoolExecutor(corePoolSize,
						maximumPoolSize, keepAliveTime, timeUnit, workQueue,
						handler);
			} else {
				if (threadFactory != null && handler == null) {
					executor = new ThreadPoolExecutor(corePoolSize,
							maximumPoolSize, keepAliveTime, timeUnit,
							workQueue, threadFactory);
				} else {
					executor = new ThreadPoolExecutor(corePoolSize,
							maximumPoolSize, keepAliveTime, timeUnit,
							workQueue, threadFactory, handler);
				}
			}
		}
		return executor;
	}

	public Class getObjectType() {
		return Executor.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public RejectedExecutionHandler getHandler() {
		return handler;
	}

	public void setHandler(RejectedExecutionHandler handler) {
		this.handler = handler;
	}

	public int getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(int keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	public ThreadFactory getThreadFactory() {
		return threadFactory;
	}

	public void setThreadFactory(ThreadFactory threadFactory) {
		this.threadFactory = threadFactory;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public BlockingQueue<Runnable> getWorkQueue() {
		return workQueue;
	}

	public void setWorkQueue(BlockingQueue<Runnable> workQueue) {
		this.workQueue = workQueue;
	}

}

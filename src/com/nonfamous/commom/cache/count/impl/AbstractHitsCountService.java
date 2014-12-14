package com.nonfamous.commom.cache.count.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nonfamous.commom.cache.count.HitsCountService;

/**
 * 实现中并不需要考虑太多的同步问题，访问量允许有一定量的丢失
 * 
 * @author fish
 * @version $Id: AbstractHitsCountService.java,v 1.1 2007/06/20 11:50:11 fish
 *          Exp $
 */
public abstract class AbstractHitsCountService<T> implements
		HitsCountService<T> {

	private final Log logger = LogFactory.getLog(this.getClass());

	private int hitsCountMax = 100;// 缺省条件

	private Map<T, Integer> id2HitsMap = getNewMap();

	private int totalHitsCount;

	private Executor executor;

	private Map<T, Integer> getNewMap() {
		return new HashMap<T, Integer>();
	}

	public void addHitOnce(T id) {
		if (id == null) {
			throw new NullPointerException("id can't be null.");
		}
		addHits(id, 1);
	}

	public void addHits(T id, int count) {
		if (id == null) {
			throw new NullPointerException("id can't be null.");
		}
		if (count <= 0) {
			throw new IllegalArgumentException("count must greate than zero");
		}
		Integer i = id2HitsMap.get(id);
		if (i == null) {
			i = 0;
		}
		i += count;
		id2HitsMap.put(id, i);
		totalHitsCount += count;
		checkCount();
	}

	
	public void flush() {
		this.flush(true);
	}

	/**
	 * 是否同步调用
	 * 
	 * @param isSynchronized
	 *            true代表同步调用
	 */
	private void flush(boolean isSynchronized) {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("now flush hitsCount.id2HitsMap.size="
					+ id2HitsMap.size() + " and isSynchronized:"
					+ isSynchronized);
		}
		final Map<T, Integer> currnet = this.id2HitsMap;
		this.id2HitsMap = getNewMap();
		this.totalHitsCount = 0;
		if (isSynchronized) {
			writerHits(currnet);
		} else {
			executor.execute(new Runnable() {
				public void run() {
					writerHits(currnet);
				}
			});
		}
	}

	public abstract void writerHits(Map<T, Integer> id2HitsMap);

	protected void checkCount() {
		if (totalHitsCount < hitsCountMax) {
			return;
		}
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("totalHitsCount[" + totalHitsCount
					+ "] reach the hitsCountMax[" + hitsCountMax
					+ "],so writerHits");
		}
		flush(false);
	}

	public int getHitsCountMax() {
		return hitsCountMax;
	}

	public void setHitsCountMax(int max) {
		this.hitsCountMax = max;
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("setHitsCountMax:" + max);
		}

	}

	public Executor getExecutor() {
		return executor;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}
}

package com.nonfamous.tang.dao.query;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.nonfamous.commom.util.StringUtils;

/**
 * 分页算法封装。 分页须设置: TotalItem（总条数）,缺省为0, 应该在dao中被设置 PageSize（每页条数），应在web层被设置
 * QueryBase 缺省为20，子类可以通过覆盖 getDefaultPageSize() 修改 CurrentPage（当前页）,缺省为1，首页，
 * 应在web层被设置 分页后，可以得到：TotalPage（总页数） FristItem(当前页开始记录位置，从1开始记数)
 * PageLastItem(当前页最后记录位置) 页面上，每页显示条数名字应为： lines ，当前页名字应为： page
 * 
 * @author fish
 * @version $Id: QueryBase.java,v 1.1 2008/07/11 00:46:56 fred Exp $
 */
public class QueryBase implements Serializable {

	private static final long serialVersionUID = 7603300820027561064L;

	private static final Integer defaultPageSize = new Integer(20);

	private static final Integer defaultFriatPage = new Integer(1);

	private static final Integer defaultTotleItem = new Integer(0);

	private Integer totalItem;

	private Integer pageSize;

	private Integer currentPage;

	// for paging
	private int startRow;

	private int endRow;
	
	
	/**
	 * 取得数据库分页起始位置
	 */
	public final int getDbStartNo() {
		int cPage = this.getCurrentPage().intValue();

		if (cPage == 1) {
			return 0; // 第一页开始当然是第 1 条记录
		}

		cPage--;
		int pgSize = this.getPageSize().intValue();

		return pgSize * cPage;
	}

	/**
	 * 取得数据库分页截止位置
	 */
	public final int getDbEndNo() {

		// int cPage = this.getCurrentPage().intValue();

		int pgSize = this.getPageSize().intValue();

		return pgSize;
	}

	/**
	 * @return Returns the defaultPageSize.
	 */
	protected final Integer getDefaultPageSize() {
		return defaultPageSize;
	}

	public boolean isFirstPage() {
		return this.getCurrentPage().intValue() == 1;
	}

	public int getPreviousPage() {
		int back = this.getCurrentPage().intValue() - 1;

		if (back <= 0) {
			back = 1;
		}

		return back;
	}

	public boolean isLastPage() {
		return this.getTotalPage() == this.getCurrentPage().intValue();
	}

	public int getNextPage() {
		int back = this.getCurrentPage().intValue() + 1;

		if (back > this.getTotalPage()) {
			back = this.getTotalPage();
		}

		return back;
	}

	/**
	 * @return Returns the currentPage.
	 */
	public Integer getCurrentPage() {
		if (currentPage == null) {
			return defaultFriatPage;
		}

		return currentPage;
	}

	/**
	 * @param currentPage
	 *            The currentPage to set.
	 */
	public void setCurrentPage(Integer cPage) {
		if ((cPage == null) || (cPage.intValue() <= 0)) {
			this.currentPage = defaultFriatPage;
		} else {
			this.currentPage = cPage;
		}
	}

	public void setCurrentPageString(String s) {
		if (StringUtils.isBlank(s)) {
			return;
		}
		try {
			setCurrentPage(Integer.parseInt(s));
		} catch (NumberFormatException ignore) {
			this.setCurrentPage(defaultFriatPage);
		}
	}

	/**
	 * @return Returns the pageSize.
	 */
	public Integer getPageSize() {
		if (pageSize == null) {
			return getDefaultPageSize();
		}

		return pageSize;
	}

	public boolean hasSetPageSize() {
		return pageSize != null;
	}

	/**
	 * @param pageSize
	 *            The pageSize to set.
	 */
	public void setPageSize(Integer pSize) {
		if (pSize == null) {
			throw new IllegalArgumentException("PageSize can't be null.");
		}

		if (pSize.intValue() <= 0) {
			throw new IllegalArgumentException("PageSize must great than zero.");
		}

		this.pageSize = pSize;
	}

	public void setPageSizeString(String pageSizeString) {
		if (StringUtils.isBlank(pageSizeString)) {
			return;
		}

		try {
			Integer integer = new Integer(pageSizeString);
			this.setPageSize(integer);
		} catch (NumberFormatException ignore) {
		}
	}

	/**
	 * @return Returns the totalItem.
	 */
	public Integer getTotalItem() {
		if (totalItem == null) {
			// throw new IllegalStateException("Please set the TotalItem
			// frist.");
			return defaultTotleItem;
		}

		return totalItem;
	}

	/**
	 * @param totalItem
	 *            The totalItem to set.
	 */
	public void setTotalItem(Integer tItem) {
		if (tItem == null) {
			// throw new IllegalArgumentException("TotalItem can't be null.");
			tItem = new Integer(0);
		}
		this.totalItem = tItem;
		int current = this.getCurrentPage().intValue();
		int lastPage = this.getTotalPage();

		if (current > lastPage) {
			this.setCurrentPage(new Integer(lastPage));
		}
	}

	public int getTotalPage() {
		int pgSize = this.getPageSize().intValue();
		int total = this.getTotalItem().intValue();
		int result = total / pgSize;

		if ((total % pgSize) != 0) {
			result++;
		}

		return result;
	}

	public int getPageFristItem() {
		int cPage = this.getCurrentPage().intValue();

		if (cPage == 1) {
			return 1; // 第一页开始当然是第 1 条记录
		}

		cPage--;

		int pgSize = this.getPageSize().intValue();

		return (pgSize * cPage) + 1;
	}

	public int getPageLastItem() {
		int cPage = this.getCurrentPage().intValue();
		int pgSize = this.getPageSize().intValue();
		int assumeLast = pgSize * cPage;
		int totalItem = getTotalItem().intValue();

		if (assumeLast > totalItem) {
			return totalItem;
		} else {
			return assumeLast;
		}
	}

	/**
	 * @return Returns the endRow.
	 */
	public int getEndRow() {
		return endRow;
	}

	/**
	 * @param endRow
	 *            The endRow to set.
	 */
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	/**
	 * @return Returns the startRow.
	 */
	public int getStartRow() {
		return startRow;
	}

	/**
	 * @param startRow
	 *            The startRow to set.
	 */
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	protected String getSQLBlurValue(String value) {
		if (value == null) {
			return null;
		}

		return value + '%';
	}

	/*
	 * 查询页面时间处理 <p> comment </p> @author shengyong @version:$Id:
	 * QueryBase.java,v 1.1 2005/03/14 22:20:05 qianxiao Exp $
	 */
	protected String formatDate(String datestring) {
		if ((datestring == null) || datestring.equals("")) {
			return null;
		} else {
			return (datestring + " 00:00:00");
		}
	}

	/**
	 * 时间查询时,结束时间的 23:59:59
	 */
	protected String addDateEndPostfix(String datestring) {
		if ((datestring == null) || datestring.equals("")) {
			return null;
		}

		return datestring + " 23:59:59";
	}

	/**
	 * 时间查询时，开始时间的 00:00:00
	 */
	protected String addDateStartPostfix(String datestring) {
		if ((datestring == null) || datestring.equals("")) {
			return null;
		}

		return datestring + " 00:00:00";
	}

	/**
	 * 获取输入时间的后面一天
	 * 
	 * @param date
	 * 
	 * @return
	 */
	public Date getAddDate(Date date) {
		Calendar cad = Calendar.getInstance();

		cad.setTime(date);
		cad.add(Calendar.DATE, 1);
		return cad.getTime();
	}

	public boolean hasSearchCondition() {
		return true;
	}
}

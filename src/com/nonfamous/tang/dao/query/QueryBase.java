package com.nonfamous.tang.dao.query;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.nonfamous.commom.util.StringUtils;

/**
 * ��ҳ�㷨��װ�� ��ҳ������: TotalItem����������,ȱʡΪ0, Ӧ����dao�б����� PageSize��ÿҳ��������Ӧ��web�㱻����
 * QueryBase ȱʡΪ20���������ͨ������ getDefaultPageSize() �޸� CurrentPage����ǰҳ��,ȱʡΪ1����ҳ��
 * Ӧ��web�㱻���� ��ҳ�󣬿��Եõ���TotalPage����ҳ���� FristItem(��ǰҳ��ʼ��¼λ�ã���1��ʼ����)
 * PageLastItem(��ǰҳ����¼λ��) ҳ���ϣ�ÿҳ��ʾ��������ӦΪ�� lines ����ǰҳ����ӦΪ�� page
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
	 * ȡ�����ݿ��ҳ��ʼλ��
	 */
	public final int getDbStartNo() {
		int cPage = this.getCurrentPage().intValue();

		if (cPage == 1) {
			return 0; // ��һҳ��ʼ��Ȼ�ǵ� 1 ����¼
		}

		cPage--;
		int pgSize = this.getPageSize().intValue();

		return pgSize * cPage;
	}

	/**
	 * ȡ�����ݿ��ҳ��ֹλ��
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
			return 1; // ��һҳ��ʼ��Ȼ�ǵ� 1 ����¼
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
	 * ��ѯҳ��ʱ�䴦�� <p> comment </p> @author shengyong @version:$Id:
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
	 * ʱ���ѯʱ,����ʱ��� 23:59:59
	 */
	protected String addDateEndPostfix(String datestring) {
		if ((datestring == null) || datestring.equals("")) {
			return null;
		}

		return datestring + " 23:59:59";
	}

	/**
	 * ʱ���ѯʱ����ʼʱ��� 00:00:00
	 */
	protected String addDateStartPostfix(String datestring) {
		if ((datestring == null) || datestring.equals("")) {
			return null;
		}

		return datestring + " 00:00:00";
	}

	/**
	 * ��ȡ����ʱ��ĺ���һ��
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

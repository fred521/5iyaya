package com.nonfamous.tang.domain;
/**
 * 
 * @author victor
 * 
 */
public class SearchGoodsCat extends GoodsCat implements Comparable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8017178990217184143L;
	// 字段描述：搜索结果，类目下结果数
	private long num = 0;

	public SearchGoodsCat(GoodsCat cat) {
		this.setLevels(cat.getLevels());
		this.setParentId(cat.getParentId());
		this.setTypeId(cat.getTypeId());
		this.setTypeName(cat.getTypeName());
		this.setParent(cat.getParent());
	}
	

	public void setNum(long num) {
		this.num = num;
	}


	public boolean isEmpty() {
		return num <= 0;
	}

	public long getNum() {
		return num;
	}


	public int compareTo(Object o) {
		SearchGoodsCat cat = (SearchGoodsCat) o;
		if (this.num < cat.num)
			return 1;
		else if (this.num == cat.num)
			return 0;
		else
			return -1;
	}
}

package com.nonfamous.tang.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.nonfamous.tang.domain.base.DomainBase;

/**
 * @author: fred
 * 
 * <pre>
 * comment
 * </pre>
 * 
 * @version $Id: GoodsCat.java,v 1.2 2008/11/29 02:53:14 fred Exp $
 */
public class GoodsCat extends DomainBase {

	public static final int TypeIdLength = 3;

	private static final long serialVersionUID = 7761325722251359954L;

	public static final char PathSeparator = ',';

	// �ֶ�����:������
	private java.lang.String typeId;

	// �ֶ�����:��������
	private java.lang.String typeName;

	// �ֶ�����:���Ϊһ�����࣬��Ϊ1����������Ϊ2
	private java.lang.Long levels;

	// �ֶ�����:������Ŀ���
	private java.lang.String parentId;

	// ��ʾ˳��
	private Integer showOrder;

	// ����Ŀ
	private GoodsCat parent;

	// ֱ������Ŀ
	private List<GoodsCat> children = new ArrayList<GoodsCat>();

	// �Ƿ񶥼���Ŀ
	public boolean isRoot() {
		return (levels != null && levels.longValue() == 1) ? true : false;
	}

	// �Ƿ�������Ŀ
	public boolean hasChildren() {
		return this.children != null && !this.children.isEmpty();
	}

	// �õ����е�����Ŀ,����������Ŀ,������Ŀ�����е�����Ŀ
	@SuppressWarnings("unchecked")
	public List<GoodsCat> getAllChildren() {
		if (!this.hasChildren()) {
			return Collections.EMPTY_LIST;
		}
		List<GoodsCat> back = new ArrayList<GoodsCat>();
		addChildrenToList(this.getChildren(), back);
		return back;
	}

	private void addChildrenToList(List<GoodsCat> cats, List<GoodsCat> list) {
		if (cats == null || cats.isEmpty()) {
			return;
		}
		list.addAll(cats);
		for (GoodsCat one : cats) {
			if (one.hasChildren()) {
				addChildrenToList(one.getChildren(), list);
			}
		}
	}

	private String path;

	/**
	 * �õ��Ӹ���this��·��,�Էָ����ָ�,
	 * 
	 * @return
	 */
	public String getPath() {
		if (path == null) {
			String tempPath = null;
			GoodsCat par = this.getParent();
			if (par != null) {
				String parenetPath = par.getPath();
				tempPath = parenetPath + PathSeparator + this.getId();
			} else {
				tempPath = this.getId();
			}
			path = tempPath;
		}
		return path;
	}

	public void setTypeId(java.lang.String typeId) {
		this.typeId = typeId;
	}

	public java.lang.String getTypeId() {
		return this.typeId;
	}

	public void setTypeName(java.lang.String typeName) {
		this.typeName = typeName;
	}

	public java.lang.String getTypeName() {
		return this.typeName;
	}

	public void setLevels(java.lang.Long levels) {
		this.levels = levels;
	}

	public java.lang.Long getLevels() {
		return this.levels;
	}

	public void setParentId(java.lang.String parentId) {
		this.parentId = parentId;
	}

	public java.lang.String getParentId() {
		return this.parentId;
	}

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public List<GoodsCat> getChildren() {
		return children;
	}

	public void setChildren(List<GoodsCat> children) {
		this.children = children;
	}

	public GoodsCat getParent() {
		return parent;
	}

	public void setParent(GoodsCat parent) {
		this.parent = parent;
	}

	public void setId(String typeId) {
		this.typeId = typeId;
	}

	public String getId() {
		return this.typeId;
	}

}
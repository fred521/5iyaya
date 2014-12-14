package com.nonfamous.tang.service;

import java.util.List;
import java.util.Map;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.cookyjar.Cookyjar;
import com.nonfamous.tang.web.common.Constants;

public class MemberAuthToken {

	public static final String MemberAuthTokenInRequest = "memberAuthToken";

	private String memeberId;

	private String memberLoginId;

	private long memberPermissions;

	private Map<String, String[]> urlPermissions;

	private Cookyjar cookyjar;

	public MemberAuthToken(Cookyjar jar, Map<String, String[]> urlPermissions) {
		super();
		this.cookyjar = jar;
		this.memeberId = jar.get(Constants.MemberId_Cookie);
		this.memberLoginId = jar.get(Constants.MemberLoinName_Cookie);
		String str = jar.get(Constants.MemberPermissions_Cookie);
		if (StringUtils.isNotBlank(str)) {
			this.memberPermissions = Long.parseLong(str, 16);
		}
		this.urlPermissions = urlPermissions;

	}

	/**
	 * 用户登录后设置cookie中的权限
	 * 
	 * @param jar
	 * @param permissions
	 *            不能为null
	 */
	public static void buildPermissions(Cookyjar jar,
			List<Constants.Permission> permissions) {
		if (jar == null) {
			throw new NullPointerException("Cookyjar can't be null");
		}
		if (permissions == null) {
			throw new NullPointerException("permissions can't be null");
		}
		long permission = 1;// NonePermission 赋予每个用户
		for (Constants.Permission per : permissions) {
			int i = per.ordinal();
			permission = permission | (1 << i);
		}
		buildPermissions(jar, permission);
	}

	/**
	 * 用户登录后设置cookie中的权限
	 * 
	 * @param jar
	 */
	public static void buildPermissions(Cookyjar jar, long memberPermissions) {
		jar.set(Constants.MemberPermissions_Cookie, Long
				.toHexString(memberPermissions));
	}

	/**
	 * 去除一个权限
	 * 
	 * @param cookyjar
	 * @param per
	 */
	public void removePermission(Constants.Permission per) {
		if (cookyjar == null) {
			throw new NullPointerException("Cookyjar can't be null");
		}
		if (per == null) {
			throw new NullPointerException("permission can't be null");
		}
		long i = 1 << per.ordinal();
		i = ~i;
		long current = this.memberPermissions;
		current = current & i;
		buildPermissions(this.cookyjar, current);
	}
	
	public void addPermission(Constants.Permission... pers) {
		if (cookyjar == null) {
			throw new NullPointerException("Cookyjar can't be null");
		}
		if (pers == null) {
			throw new NullPointerException("permission can't be null");
		}
		if (pers.length == 0) {
			return;
		}
		long current = this.memberPermissions;
		for(int j = 0;j<pers.length;j++){
			long i = 1 << pers[j].ordinal();
			current = current | i;
		}
		buildPermissions(this.cookyjar, current);
	}

	/**
	 * 当前用户是否有访问此url的权限
	 * 
	 * @param url
	 * @return 是否能访问
	 */
	public boolean havePermission(String url) {
		if (memeberId == null) {// 未登录
			return false;
		}
		String[] permissionNames = urlPermissions.get(url);
		if (permissionNames == null) {
			return true;// 没有配置,缺省可以访问
		}
		for (String permission : permissionNames) {
			Constants.Permission per = Constants.Permission.valueOf(permission);
			if (havePermission(per)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 当前用户是否能访问此权限值
	 * 
	 * @param permission
	 * @return 是否能访问
	 */
	public boolean havePermission(Constants.Permission permission) {
		int ordinal = permission.ordinal();
		return onPositionTrue(this.memberPermissions, ordinal);
	}

	/**
	 * 当前用户是否能访问此权限值
	 * 
	 * @param permissionName
	 *            权限名称
	 * @return 是否能访问
	 */
	public boolean havePermissionByName(String permissionName) {
		Constants.Permission per = Constants.Permission.valueOf(permissionName);
		return this.havePermission(per);
	}

	private static final boolean onPositionTrue(long i, int positions) {
		int num = 1 << positions;
		return (i & num) > 0;
	}

	public String getMemberLoginId() {
		return memberLoginId;
	}

	public String getMemeberId() {
		return memeberId;
	}

	public long getMemberPermissions() {
		return memberPermissions;
	}

}

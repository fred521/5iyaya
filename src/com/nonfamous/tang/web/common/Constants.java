package com.nonfamous.tang.web.common;

public interface Constants {
	public static final String AdminUserId_Cookie = "adminUserId";

	public static final String MemberId_Cookie = "memberId";

	public static final String MemberLoinName_Cookie = "loginName";

	public static final String MemberPermissions_Cookie = "memberPermissions";

	public static final String MemberSaveLoginInfo_Cookie = "memberSaveLoginInfo";
	
	public static final String CheckCode_Cookie = "checkCode";
	
	public static final String PageTitle_Page = "page_title";
	
	public static final String User_Role="userRole";
	
	public static final String Default_Member_Info = "NULL";
	
	public static final String[] Order_Reminder_List = new String[]{"daizhixia@gmail.com", "chenzi66414@gmail.com"};

	public enum Permission {
		NonePermission, MemberActive, ShopEdit, NewsEdit,TradePay
	};

}

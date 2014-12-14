package com.nonfamous.tang.service.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nonfamous.tang.dao.query.MemberQuery;
import com.nonfamous.tang.domain.Buyer;
import com.nonfamous.tang.domain.Member;
import com.nonfamous.tang.domain.Seller;
import com.nonfamous.tang.domain.UserCenter;
import com.nonfamous.tang.domain.message.Message;
import com.nonfamous.tang.domain.result.ActiveResult;
import com.nonfamous.tang.domain.result.LoginResult;
import com.nonfamous.tang.domain.result.NewMemberResult;
import com.nonfamous.tang.domain.result.UpdateMemberInfoResult;
import com.nonfamous.tang.exception.ServiceException;

/**
 * <p>
 * 会员服务接口类，包含了大部分的会员操作
 * </p>
 * 
 * @author:daodao
 * @version $Id: MemberService.java,v 1.3 2009/08/15 10:05:25 andy Exp $
 */
public interface MemberService {
	/**
	 * 新增一个买家会员
	 * 
	 * @param buyer
	 * @return
	 */
	public NewMemberResult addBuyer(Buyer buyer);

	/**
	 * 新增一个卖家会员
	 * 
	 * @param seller
	 * @param creator
	 * @return
	 */
	public NewMemberResult addSeller(Seller seller, String creator);

	/**
	 * 新增卖家会员并且为其创建店铺（目前好像用不到）
	 * 
	 * @param seller
	 * @param creator
	 * @return
	 */
	public NewMemberResult addSellerAndShop(Seller seller, String creator);

	/**
	 * 新增一个会员，不论买家还是卖家
	 * 
	 * @param member
	 * @param creator
	 * @return
	 */
	public NewMemberResult addMember(Member member, String creator);

	/**
	 * 会员登录
	 * 
	 * @param loginAccount
	 * @param password
	 * @param loginIp
	 * @return
	 */
	public LoginResult login(String loginAccount, String password,
			String loginIp);

	/**
	 * 查询会员列表（包括高级搜索）
	 * 
	 * @param query
	 * @return
	 */
	public List queryMember(MemberQuery query);
	
	/**
	 * 获取以memberId为key的member map
	 * @param memberIds
	 * @return
	 */
	public Map queryMemberMapByIds(String[] memberIds);

	/**
	 * 根据会员id获取会员信息
	 * 
	 * @param memberId
	 * @return
	 */
	public Member getMemberById(String memberId);

	/**
	 * 根据店铺的id获取卖家信息(因为一个卖家只允许有且只有一个店铺，所以可以这样来搞，目前好像也用不到)
	 * 
	 * @param shopId
	 * @return
	 */
	public Seller getSellerByShopId(String shopId);

	/**
	 * 修改会员信息
	 * 
	 * @param member
	 * @return
	 */
	public UpdateMemberInfoResult updateMemberInfo(Member member,
			String modifier);

	/**
	 * 更换登录密码
	 * 
	 * @param memberId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public UpdateMemberInfoResult changePassowrd(String memberId,
			String oldPassword, String newPassword);
	/**
	 * 设定标志位是更换登录密码还是重置密码
	 * @param memberId
	 * @param oldPassword
	 * @param newPassword
	 * @param isNeedCheck
	 * @return
	 */
	public UpdateMemberInfoResult changePassowrd(String memberId,
			String oldPassword, String newPassword,boolean isNeedCheck);
	/**
	 * 更换会员手机（暂不实现，后续可能会放到专门的手机服务类中）
	 * 
	 * @param memberId
	 * @param oldMobile
	 * @param newMobile
	 * @return
	 */
	public UpdateMemberInfoResult changeMobile(String memberId,
			String oldMobile, String newMobile);

	/**
	 * 激活会员
	 * 
	 * @param memberId
	 * @param activeCode
	 * @return
	 */
	public ActiveResult activeMember(String memberId, String activeCode);

	/**
	 * 重发校验码
	 * 
	 * @param memberId
	 * @return
	 */
	public ActiveResult resendActiveCode(String memberId);

	/**
	 * 修改会员状态，由于是后台使用，不需要作太多的限制
	 * 
	 * @param memberId
	 * @param status
	 */
	public UpdateMemberInfoResult updateMemberStatus(String memberId,
			String status, String modifier);
	
	
	/**
	 * 会员信息快速搜索
	 * @param query
	 * @return
	 */
	public List quickQueryShop(MemberQuery query);
	
	public boolean emailIsUsed(String email);

	public Member getMemberByEmail(String email);
	
	/**
	 * 
	 * @param memberId 买家或者商家 ID
	 * @param memberType 买家，商家标志
	 * @param mail 买家邮件地址
	 * @param requestIp 请求重置的IP
	 * @param model 邮件模板数据
	 * @return 具体的消息
	 */
	public Message findPassword(String memberId,String mail,String requestIp,HashMap model)throws ServiceException;
	
	/**
	 * 
	 * @param memberId 加密过的发送到邮箱的memberId
	 * @param memberType 买家，商家类型标志
	 * @param code 加密过后发送到邮箱的code
	 * @param messageList 存储到页面的消息列表
	 * @return 修改密码链接无效则返回null,否则返回具体的UserCenter数据
	 */
	public UserCenter checkMemberCode(String verifyCode, ArrayList<Message> messageList)throws ServiceException;
	
	/**
	 * 
	 * @param loginId 用户登录的ID
	 * @return 
	 */
	public Message findLoginId(String mail,HashMap model)throws ServiceException;
	
	/**
	 * 通过loginId查找用户
	 * @param loginId
	 * @return
	 * @throws ServiceException
	 */
	public Member getMemberByLoginId(String loginId)throws ServiceException;
	
	/**
	 * 生成用户修改密码令牌
	 * @return
	 */
	public String generateToken();
	
	/**
	 * 检查令牌是否有效
	 * @param token
	 * @return
	 */
	public Boolean checkToken(String token);
	
}

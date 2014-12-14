package com.nonfamous.tang.web.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.tang.dao.query.MemberQuery;
import com.nonfamous.tang.domain.Member;
import com.nonfamous.tang.service.home.MemberService;

/**
 * @author:alan
 * 
 * <pre>
 * 店铺选择弹出窗口
 * </pre>
 * 
 * 
 * @version $$Id: SelectShopListAction.java,v 1.1 2008/07/11 00:46:55 fred Exp $$
 */
public class SelectShopListAction implements Controller {
	private MemberService memberService;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MemberQuery query = requestToQuery(request);
		query.setMemberType(Member.FLAG_SELLER);

		// 搜索买家会员信息
		List memberList = memberService.quickQueryShop(query);
		ModelAndView mv = new ModelAndView("home/member/selectShopList");
		mv.addObject("memberList", memberList);
		mv.addObject("search", query);
		return mv;
	}

	/**
	 * 获取query信息
	 * 
	 * @param request
	 * @return
	 */
	private MemberQuery requestToQuery(HttpServletRequest request) {
		MemberQuery query = new MemberQuery();
		RequestValueParse rvp = new RequestValueParse(request);
		// 关键字类型
		String keyType = rvp.getParameter("keyType").getString();
		// 关键字
		String keyWord = rvp.getParameter("keyWord").getString();
		request.setAttribute("keyType", keyType);
		request.setAttribute("keyWord", keyWord);

		if (StringUtils.equals(keyType, "loginId")) {
			query.setLoginId(keyWord);
		} else if (StringUtils.equals(keyType, "shopName")) {
			query.setShopName(keyWord);
		} else if (StringUtils.equals(keyType, "shopOwner")) {
			query.setShopOwner(keyWord);
		} else if (StringUtils.equals(keyType, "nick")) {
			query.setNick(keyWord);
		} else if (StringUtils.equals(keyType, "name")) {
			query.setRealName(keyWord);
		} else if (StringUtils.equals(keyType, "mobile")) {
			query.setMobile(keyWord);
		}

		String currentPage = rvp.getParameter("currentPage").getString();
		if (StringUtils.isNotBlank(currentPage)) {
			query.setCurrentPage(Integer.valueOf(currentPage));

		}
		return query;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

}

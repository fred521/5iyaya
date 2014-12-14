package com.nonfamous.tang.web.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.Controller;

import com.nonfamous.tang.dao.home.HelperTypeDAO;
import com.nonfamous.tang.domain.Helper;
import com.nonfamous.tang.domain.HelperType;
import com.nonfamous.tang.service.home.HelperService;
/**
 * 
 * @author fred
 * 
 */
public class HelperControl extends AbstractController implements Controller {
	private HelperService helperService;
	private HelperTypeDAO helperTypeDAO;
	public void setHelperService(HelperService helperService) {
		this.helperService = helperService;
	}
	public void setHelperTypeDAO(HelperTypeDAO helperTypeDAO) {
		this.helperTypeDAO = helperTypeDAO;
	}
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("helperControl");
		List<HelperType> helperTypeList=helperTypeDAO.getAllHelperType();
		mv.addObject("helperTypeList", helperTypeList);
		
		Map<Integer,List<Helper>> helperMap = new HashMap<Integer,List<Helper>>();
		List helperList = helperService.getAllHelperList();
		if( helperList != null ) {
			for( int i=0 ; i<helperList.size() ; i ++ ) {
				Helper helper = (Helper)helperList.get(i) ;
				if( !helperMap.containsKey( helper.getHelperType() ) ) {
					helperMap.put( helper.getHelperType() , new ArrayList<Helper>() ) ;
				}
				helperMap.get( helper.getHelperType() ).add( helper ) ;
			}
		}
		mv.addObject( "helperMap" , helperMap );
		return mv;
	}

	
}

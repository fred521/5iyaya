package com.nonfamous.tang.web.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;

import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.commom.util.web.cookyjar.Cookyjar;
import com.nonfamous.tang.dao.home.ShopDAO;
import com.nonfamous.tang.domain.RecordInformation;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.service.video.ITokenCacheManager;
import com.nonfamous.tang.service.video.ITokenGenerator;
import com.nonfamous.tang.web.common.Constants;

@SuppressWarnings("serial")
public class VideoAction extends MultiActionController {
	
	private ShopDAO shopDAO;
	private String uploadAddress;
	private String recordAddress;
	private String liveAddress;
	private String chatAddress;
	
	private String recordInformation = "" ;
	final static private Long MAX_UPLOAD_FILE_SIZE = 100L ;
	final public static Long   UPLOAD_LENGTH_1M  = 1048576L       ;	
	final public static Long   DEFAULT_RECORD_LENGTH  = 300L       ; // 300 seconds	
	
	private ITokenGenerator tokenGenerator ;	
	private ITokenCacheManager tokenCacheManager ;	
	
	public String getRecordInformation() {
		return recordInformation;
	}
	
	public String getUploadAddress() {
		return uploadAddress;
	}

	public void setUploadAddress(String uploadAddress) {
		this.uploadAddress = uploadAddress;
	}
	
	public String getChatAddress() {
		return chatAddress;
	}

	public void setChatAddress(String chatAddress) {
		this.chatAddress = chatAddress;
	}
	
	public String getRecordAddress() {
		return recordAddress;
	}

	public void setRecordAddress(String recordAddress) {
		this.recordAddress = recordAddress;
	}

	public String getLiveAddress() {
		return liveAddress;
	}

	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}

	public void setTokenCacheManager(ITokenCacheManager tokenCacheManager) {
		this.tokenCacheManager = tokenCacheManager;
	}

	public void setTokenGenerator(ITokenGenerator tokenGenerator) {
		this.tokenGenerator = tokenGenerator;
	}	
	
	public ModelAndView validateToken( HttpServletRequest request,HttpServletResponse response ){
		ModelAndView mv = new ModelAndView("home/video/ri");
		String token = request.getParameter( "token" ) ;
		if( token != null && tokenCacheManager.isTokenExists(token)){
			RecordInformation ri = (RecordInformation)tokenCacheManager.get(token) ;
			if( !ri.isValidated() ) {
			    ri.setValidated(true);
			    recordInformation = new org.json.JSONObject( ri ).toString( ) ;
				mv.addObject( "recordInformation" , recordInformation ) ;
			}
		}
		return mv ;
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView proxyAuthentication( HttpServletRequest request,HttpServletResponse response ){
		String token = request.getParameter( "token" ) ;
		if( token != null && tokenCacheManager.isTokenExists(token)){
		    RecordInformation ri = (RecordInformation)tokenCacheManager.get(token) ;
		    
		    RecordInformation ari= new RecordInformation();
		    ari.setUserId( ri.getUserId() ) ;
			ari.setLength( UPLOAD_LENGTH_1M * MAX_UPLOAD_FILE_SIZE ) ;
			
			recordInformation = new org.json.JSONObject( ari ).toString( ) ;
		}
		ModelAndView mv = new ModelAndView("home/video/ri");
		mv.addObject( "recordInformation" , recordInformation ) ;
		return mv ;
	}	
	
	@SuppressWarnings("unchecked")
	public ModelAndView uploadVideo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		String memberId = cookyjar.get(Constants.MemberId_Cookie);
		RecordInformation ri = new RecordInformation();
		ri.setUserId( "" + memberId ) ;
		Shop shop=shopDAO.shopSelectByMemberId(memberId);
		String token = tokenGenerator.generateToken() ;
		tokenCacheManager.cache(token , ri ) ;
		ModelAndView mav = new ModelAndView("home/video/uploadVideo");
		mav.addObject( "uploadAddress" , uploadAddress + "?token=" + token ) ;
		mav.addObject( "shopId" ,  shop.getShopId() ) ;
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView recordVideo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		RequestValueParse rvp = new RequestValueParse(request);
		Cookyjar cookyjar = rvp.getCookyjar();
		String memberId = cookyjar.get(Constants.MemberId_Cookie);
		RecordInformation ri = new RecordInformation();
		ri.setUserId( "" + memberId) ;
		ri.setLength( DEFAULT_RECORD_LENGTH ) ;
		Shop shop=shopDAO.shopSelectByMemberId(memberId);
		String token = tokenGenerator.generateToken() ;
		tokenCacheManager.cache(token , ri ) ;
		ModelAndView mav = new ModelAndView("home/video/recordVideo");
		mav.addObject( "recordAddress" , recordAddress + "?token=" + token ) ;
		mav.addObject( "shopId" ,  shop.getShopId() ) ;
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView liveVideo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		RecordInformation ri = new RecordInformation();
		ri.setUserId( "" + new RequestValueParse(request).getCookyjar().get(Constants.MemberId_Cookie) ) ;
		ri.setLength( 0L ) ;
		String token = tokenGenerator.generateToken() ;
		tokenCacheManager.cache(token , ri ) ;
		
		return new ModelAndView(new RedirectView(liveAddress + "?token=" + token, false)) ;
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView viewLive(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav =  new ModelAndView( "home/video/viewLive" ) ;		
		String liveId = null ;
		String memberId = request.getParameter( "memberId" ) ;
		if( memberId != null){
			Shop shop=shopDAO.shopSelectByMemberId(memberId);
			if( shop != null ){
				liveId = shop.getLiveId() ;
				mav.addObject( "liveId" ,  liveId ) ;
				mav.addObject( "shopId" ,  shop.getShopId() ) ;
			}
		}	
		return mav ;
	}

	public ShopDAO getShopDAO() {
		return shopDAO;
	}

	public void setShopDAO(ShopDAO shopDAO) {
		this.shopDAO = shopDAO;
	}
}

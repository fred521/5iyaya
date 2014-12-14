package com.nonfamous.tang.service.video.pojo;

import java.util.Map;

import com.nonfamous.commom.util.service.POJOServiceBase;
import com.nonfamous.tang.dao.home.ShopDAO;
import com.nonfamous.tang.domain.RecordInformation;
import com.nonfamous.tang.domain.Shop;
import com.nonfamous.tang.service.video.ITokenCacheManager;


public class GoodsVideoManager extends POJOServiceBase{
	private ITokenCacheManager tokenCacheManager ;
	
	private ShopDAO shopDAO;
	
	public ShopDAO getShopDAO() {
		return shopDAO;
	}

	public void setShopDAO(ShopDAO shopDAO) {
		this.shopDAO = shopDAO;
	}

	@SuppressWarnings("unchecked")
	public void onVideoConvertFinished( Map goodsVideoInformation  ) {
		Long id              = (Long)goodsVideoInformation.get( "id"    ) ;
		String token         = (String)goodsVideoInformation.get( "token"    ) ;
		Long sourceType      = (Long)goodsVideoInformation.get( "sourceType"    ) ;
		
		RecordInformation ri = (RecordInformation)tokenCacheManager.remove( token ) ;
		try {			
			if( ri == null ) {
				throw new Exception( "can not find original record information with token:" + token ) ;
			}
			if( ri.getUserId() == null ) {
				throw new Exception( "can not find original user id information with token:" + token ) ;					
			}
			Shop shop =shopDAO.shopfullSelectByMemberId( ri.getUserId() );
			if( sourceType != null && sourceType == 2) {
				shop.setLiveId( id.toString() ) ;
			} else {
				shop.setVideoId(id.toString() );
			}
			shopDAO.updateShop(shop);
		} catch( Exception e ) {
			logger.error( "onVideoConvertFinished Internal error: " + e.getMessage()) ;
		} 		
	}
	
	public void setTokenCacheManager(ITokenCacheManager tokenCacheManager) {
		this.tokenCacheManager = tokenCacheManager;
	}
	
}

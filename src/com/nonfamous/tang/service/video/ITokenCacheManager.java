package com.nonfamous.tang.service.video;

public interface ITokenCacheManager {

	public void cache( String token , java.io.Serializable cachedObject ) ;
	
	public boolean isTokenExists( String token ) ;
	
	public java.io.Serializable remove( String token ) ;
	
	public java.io.Serializable get( String token ) ;
	
}

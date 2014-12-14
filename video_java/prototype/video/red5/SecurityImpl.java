package prototype.video.red5;

import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
import org.red5.server.api.Red5;
import org.red5.server.api.service.IServiceCapableConnection;
import org.red5.server.api.so.ISharedObject;
import org.red5.server.api.so.ISharedObjectSecurity;
import org.red5.server.api.stream.IStreamPlaybackSecurity;
import org.red5.server.api.stream.IStreamPublishSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import prototype.video.security.TokenSecurity;
import prototype.adapter.xwork.AuthInterceptor;

public class SecurityImpl extends TokenSecurity implements IStreamPlaybackSecurity,IStreamPublishSecurity,ISharedObjectSecurity {
	
	private Logger logger = LoggerFactory.getLogger( SecurityImpl.class ) ;
	
	private Cache red5Cache = null;
	
	private String tokenValidateAddress;
	private int timeout ;
	
	
	
    public void setCacheManager(CacheManager cacheManager) {
    	red5Cache = cacheManager.getCache("red5Cache");
    }
    
	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getTokenValidateAddress() {
		return tokenValidateAddress;
	}

	public void setTokenValidateAddress(String tokenValidateAddress) {
		this.tokenValidateAddress = tokenValidateAddress;
	}

	public boolean isPlaybackAllowed(IScope scope, String name, int start,
			int length, boolean flushPlaylist) {
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean isPublishAllowed(IScope scope, String name, String mode) {		
		UserRecordInformation uri = new UserRecordInformation();
		
		IConnection conn      = Red5.getConnectionLocal();
		String token          = (String)conn.getAttribute( "token" ) ;
		if( token != null ){	
			if( red5Cache.isKeyInCache( token ) ) {
				uri = ( UserRecordInformation )( red5Cache.get( token ).getValue() ) ;
			}
			try {
				if( uri.getLength() == null ){
					Object obj = validateToken( this.tokenValidateAddress , token , timeout ) ;
					if( obj != null && obj instanceof Map) {
				    	uri.setToken(token) ;
				    	uri.setMode( mode ) ;
						if( ((Map)obj).get("length" ) != null ) {
							uri.setLength( Long.valueOf( ( (Map)obj).get("length" ).toString() ) ) ;
						}
						if( ((Map)obj).get( AuthInterceptor.UserIdKey ) != null ) {
							uri.setUserId( ( (Map)obj).get( AuthInterceptor.UserIdKey ).toString()  ) ;
						}							
						red5Cache.put( new Element( token , uri ) ) ;
					}
				}
			} catch( Exception e ){
				logger.error( "error" , e ) ;
			}
		}
		
		if( !name.equals( uri.getUserId() + "/" + uri.getToken() ) ) {
			uri.setLength( null ) ;
			logger.error( "expected name:" + uri.getUserId() + "/" + uri.getToken() + " can not match with passed in name:" + name );
		}
		
		if( uri.getLength() == null ) {
			uri.setLength( -1L ) ;
		}
		
		if( ( conn instanceof IServiceCapableConnection) ){
			IServiceCapableConnection sc = (IServiceCapableConnection) conn; 
	        sc.invoke("updateTime", new Object[]{ uri.getAccessedTime() , uri.getLength() } );
		}
		
		if( token != null ) {
			conn.getClient().setAttribute( "token", token ) ;
		}
		
		uri.setPaused( false ) ;
		
		return uri.getLength() >= 0 ;
	}	
	public boolean isConnectionAllowed(ISharedObject so) {
		return true;
	}

	public boolean isCreationAllowed(IScope scope, String name,
			boolean persistent) {
		return true;
	}

	public boolean isDeleteAllowed(ISharedObject so, String key) {
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean isSendAllowed(ISharedObject so, String message,
			List arguments) {
		return true;
	}

	public boolean isWriteAllowed(ISharedObject so, String key, Object value) {
		return true ;
	}	
}

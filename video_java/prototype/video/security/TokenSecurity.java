package prototype.video.security;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtree.json.JSONReader;

public class TokenSecurity implements ITokenSecurity{
	private Logger logger = LoggerFactory.getLogger( TokenSecurity.class ) ;
	
	public Object validateToken( String serverUrl, String tok , int _timeout ) throws Exception {
		String tokenValidateUrl = serverUrl  + tok ;
		if( logger.isInfoEnabled() ){
			logger.info( "start to validate token:" + tokenValidateUrl );
		}
		
		HttpClient client = new HttpClient(new SimpleHttpConnectionManager());
		client.getHttpConnectionManager().getParams().setConnectionTimeout( _timeout );
		GetMethod getMethod = new GetMethod( tokenValidateUrl ) ;
		if( client.executeMethod( getMethod ) == HttpStatus.SC_OK ) {
			if( logger.isInfoEnabled()){
				logger.info( "succeed." ) ;
			}
			String res = getMethod.getResponseBodyAsString( ) ;
			if( logger.isInfoEnabled() ){
				logger.info( "response:" + res ) ;
			}
			res = res.trim() ;
			if( !res.equals( "" ) ) {
				return new JSONReader().read( res ) ;
			}
		} 
		if(logger.isInfoEnabled() ){
			logger.info( "fail." ) ;
		}
		return null ;
	}
}

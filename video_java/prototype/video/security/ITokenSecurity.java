package prototype.video.security;

public interface ITokenSecurity {

	public Object validateToken( String serverUrl, String tok , int _timeout ) throws Exception ;
	
}

package prototype.video.red5;

import java.util.Iterator;

import javax.jms.Queue;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
import org.red5.server.api.Red5;
import org.red5.server.api.ScopeUtils;
import org.red5.server.api.scheduling.IScheduledJob;
import org.red5.server.api.scheduling.ISchedulingService;
import org.red5.server.api.so.ISharedObjectSecurity;
import org.red5.server.api.stream.IServerStream;
import org.red5.server.api.stream.IStreamFilenameGenerator;
import org.red5.server.api.stream.IStreamPlaybackSecurity;
import org.red5.server.api.stream.IStreamPublishSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import prototype.video.domain.VideoUploadInformation;

public class Application extends ApplicationAdapter implements IScheduledJob,IStreamFilenameGenerator {
	private final String CONVERTED_FILE_SUFFIX  = ".flv"   ;
	private Logger logger = LoggerFactory.getLogger( Application.class ) ;
	
	private IScope appScope;

	private IServerStream serverStream;

	private IStreamPlaybackSecurity playbackSecurityHandler ;
	private ISharedObjectSecurity sharedObjectSecurityHandler ;
	private IStreamPublishSecurity publishSecurityHandler ;	
	private IScheduledJob securityScheduleJob ;
	private Integer securityScheduleFrequency ;
	private Cache red5Cache = null;
	private Boolean syncSignalObjectOfCache = true ;
	private JmsTemplate template;
	private Queue destination;
	private String destFolder; // which folder will be used to store the recorded video
	
	private boolean securityEnabled = true ;
	
	public void setSecurityEnabled( boolean enabled ){
		securityEnabled = enabled ;
	}
	
	
	public String getDestFolder() {
		return destFolder;
	}

	public void setDestFolder(String destFolder) {
		this.destFolder = destFolder;
	}

	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	public void setDestination(Queue destination) {
		this.destination = destination;
	}
    public void setCacheManager(CacheManager cacheManager) {
    	red5Cache = cacheManager.getCache("red5Cache");
    }
    
	public Integer getSecurityScheduleFrequency() {
		return securityScheduleFrequency;
	}

	public void setSecurityScheduleFrequency(Integer securityScheduleFrequency) {
		this.securityScheduleFrequency = securityScheduleFrequency;
	}

	public IScheduledJob getSecurityScheduleJob() {
		return securityScheduleJob;
	}

	public void setSecurityScheduleJob(IScheduledJob securityScheduleJob) {
		this.securityScheduleJob = securityScheduleJob;
	}

	public IStreamPlaybackSecurity getPlaybackSecurityHandler() {
		return playbackSecurityHandler;
	}

	public void setPlaybackSecurityHandler(
			IStreamPlaybackSecurity playbackSecurityHandler) {
		this.playbackSecurityHandler = playbackSecurityHandler;
	}

	public ISharedObjectSecurity getSharedObjectSecurityHandler() {
		return sharedObjectSecurityHandler;
	}

	public void setSharedObjectSecurityHandler(
			ISharedObjectSecurity sharedObjectSecurityHandler) {
		this.sharedObjectSecurityHandler = sharedObjectSecurityHandler;
	}

	public IStreamPublishSecurity getPublishSecurityHandler() {
		return publishSecurityHandler;
	}

	public void setPublishSecurityHandler(
			IStreamPublishSecurity publishSecurityHandler) {
		this.publishSecurityHandler = publishSecurityHandler;
	}

    private String getStreamDirectory(IScope scope) {
		final StringBuilder result = new StringBuilder();
		final IScope app = ScopeUtils.findApplication(scope);
		while (scope != null && scope != app) {
			result.insert(0, scope.getName() + "/");
			scope = scope.getParent();
		}
		return "streams/" + result.toString();
	}

    public String generateFilename(IScope scope, String name, GenerationType type) {
		return generateFilename(scope, name, null, type);
	}

    public String generateFilename(IScope scope, String name, String extension, GenerationType type) {
    	String result = getStreamDirectory(scope) + name;
		if (extension != null && !extension.equals("")) {
			result += extension;
		}
		return result;
	}

	public boolean resolvesToAbsolutePath() {
		return false;
	}
	
	public boolean appStart(IScope app) {
    	if( securityEnabled ){
			if( this.playbackSecurityHandler != null ){
	    		super.registerStreamPlaybackSecurity( this.playbackSecurityHandler ) ;
	    	}
	    	if( this.publishSecurityHandler != null ) {
	    		super.registerStreamPublishSecurity( this.publishSecurityHandler ) ;
	    	}
	    	
	    	if( this.sharedObjectSecurityHandler != null ) {
	    		super.registerSharedObjectSecurity( this.sharedObjectSecurityHandler ) ;
	    	}
    	}
		appScope = app;
			
		if( securityEnabled ) addScheduledJob( securityScheduleFrequency , this ) ;
		return true;
	}
	
	public boolean appConnect(IConnection conn, Object[] params) {
		if( params != null && params.length > 0 && params[0] != null ){
			String[] sparams = params[0].toString().split( "&" );
			for( int i=0 ; i<sparams.length ; i++ ) {
				String[] ps = sparams[i].split( "=" ) ;
				if( ps.length == 2 ) {
					if( logger.isInfoEnabled() ){
						logger.info( "parameter from client:" + ps[0] + "=" + ps[1] ) ;
					}
					conn.setAttribute( ps[0] , ps[1] ) ; 
				}
			}
		}
		
		// don't do bandwidth check for each connection
		if( params != null && params.length == 1 && params[0].toString().equals("true" ) ) {
		    BandwidthDetection detect = new BandwidthDetection();
		    detect.checkBandwidth(conn);
		}
		return super.appConnect(conn, params);
	}
	
	public void appDisconnect(IConnection conn) {
		if (appScope == conn.getScope() && serverStream != null) {
			serverStream.close();
		}
		super.appDisconnect(conn);
	}

	@SuppressWarnings("unchecked")
	public void execute(ISchedulingService service) throws CloneNotSupportedException {
		Iterator iterator = this.getScope().getClients().iterator() ;
		while( iterator.hasNext() ) {
			IClient client = ( IClient )iterator.next( );
			String token = (String)client.getAttribute( "token" ) ;
			if( token != null ){
				UserRecordInformation uri = null ;
				
				// 1, if there is no cached UserRecordInformation for given token, we should disconnect the client
				if( !red5Cache.isKeyInCache( token ) ) {
					if( logger.isInfoEnabled() ) {
						logger.info( "disconnect client. can not find cached UserRecordInformation with token:" + token + " for client:" + client.getId() ) ;
					}
					client.disconnect() ;
				}
				// 2, retrive the cached UserRecordInformation
				else {
					boolean checkNeeded = false ;
					long lastAccessTime = System.currentTimeMillis() ;					
					
					// because it is possible at the same time client send out pause signal to function onRecordPaused
					synchronized( syncSignalObjectOfCache ) {
						uri = (UserRecordInformation)( red5Cache.get( token ).getValue())  ;
						// 3, update accessedTime and lastAccessTime
						if( uri.getLastAccesseTime() == null ){
							uri.setLastAccesseTime( lastAccessTime ) ;
					    } else {		
					    	// when the user is paused, do not calculate time
							if( !uri.isPaused() ){
								uri.setAccessedTime( uri.getAccessedTime() + (lastAccessTime - uri.getLastAccesseTime())/1000 ) ;
							}
							uri.setLastAccesseTime(lastAccessTime) ;
							checkNeeded = true ;
						}
					}
					
					// 4, decide whether we need disconnect the client because time up, if length equals 0 means time is un-limited
					if( checkNeeded && uri.getLength() != 0 && ( uri.getAccessedTime() > uri.getLength() ) ) {
						if( logger.isInfoEnabled() ) {
							logger.info( "disconnect client. because accessedTime " + uri.getAccessedTime() + " >= length " + uri.getLength( ) + " with token:" + token + " for client:" + client.getId() ) ;
						}
						if( !uri.isNotified() ) notifyRecordFinished( uri ) ;
						red5Cache.remove( token ) ;
						client.disconnect() ;
					}
					
					// 5, if it is live video, we need notify it early
					if( !uri.isNotified( ) && uri.getLength() == 0 ) {
						notifyRecordFinished( uri ) ;
					}
					
				}			
			}
		}
	}
	
	public void onRecordFinished(){
		IConnection conn = Red5.getConnectionLocal();
		String token = (String)conn.getAttribute( "token" ) ;
		if( token != null ) {
			if( logger.isInfoEnabled() ) {
				logger.info( "start to clear out token from cache:" + token ) ;
			}
			synchronized( syncSignalObjectOfCache ) {
				if( red5Cache.isKeyInCache( token ) ) {
					UserRecordInformation uri = (UserRecordInformation)( red5Cache.get( token ).getValue())  ;
					if( !uri.isNotified() ) notifyRecordFinished( uri ) ;
					red5Cache.remove( token );
				}
			}
		}
	}
	
	public void onRecordPaused(){
		IConnection conn = Red5.getConnectionLocal();
		String token = (String)conn.getAttribute( "token" ) ;
		if( token != null ) {
			if( logger.isInfoEnabled() ) {
				logger.info( "(received pause signal)stop calculate timing for client with token:"  + token ) ;
			}
			synchronized( syncSignalObjectOfCache ) {
				if( red5Cache.isKeyInCache( token ) ) {
					UserRecordInformation uri = (UserRecordInformation)( red5Cache.get( token ).getValue())  ;
					uri.setPaused(true);
				}
			}
		}
	}
	
	private void notifyRecordFinished( UserRecordInformation uri ){
		uri.setNotified( true ) ;
		VideoUploadInformation vui = new VideoUploadInformation();
		vui.setToken(uri.getToken() ) ;
		vui.setUserId( uri.getUserId() ) ;
		vui.setFileAbsPath( destFolder + uri.getUserId() + "/" + uri.getToken() + CONVERTED_FILE_SUFFIX ) ;
		template.convertAndSend( destination , vui ) ;
	}
	
}

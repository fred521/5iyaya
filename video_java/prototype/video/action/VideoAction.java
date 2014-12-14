package prototype.video.action;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import javax.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import prototype.action.NothingAction;
import prototype.video.dao.IVideoManagerDAO;
import prototype.video.domain.VideoInformation;
import prototype.video.domain.VideoUploadInformation;
import prototype.video.security.ITokenSecurity;
import prototype.adapter.xwork.AuthInterceptor;

@SuppressWarnings("serial")
public class VideoAction extends NothingAction {
	private Logger logger = LoggerFactory.getLogger( VideoAction.class ) ;
	private VideoUploadInformation vui = new VideoUploadInformation() ;
	private String tokenValidateAddress= null ;
	private JmsTemplate template;
	private Queue destination;
	private IVideoManagerDAO videoManagerDAO ;	
	private VideoInformation videoInformation = new VideoInformation() ;
	
	private File filedata ;
	
    private int timeout ;

	private ITokenSecurity tokenSecurity ;
	
    private String isConvertFinished ;	
    	
	private String imageFolder ;
	
	private String supportTypes;
	
	private String imageUrl = null ;	
	
	private String splashUrl= null ;
	
	
	
	public String getSplashUrl() {
		return splashUrl;
	}

	public void setSplashUrl(String splashUrl) {
		this.splashUrl = splashUrl;
	}

	public void setPlayVideoId(Long playVideoId) {
		videoInformation.setId( playVideoId ) ;
	}

	public IVideoManagerDAO getVideoManagerDAO() {
		return videoManagerDAO;
	}
	
	public void setVideoManagerDAO(IVideoManagerDAO videoManagerDAO) {
		this.videoManagerDAO = videoManagerDAO;
	}
	
	public String getSupportTypes() {
		return supportTypes;
	}

	public void setSupportTypes(String supportTypes) {
		this.supportTypes = supportTypes;
	}

	public void setTokenSecurity(ITokenSecurity tokenSecurity) {
		this.tokenSecurity = tokenSecurity;
	}

	public String getIsConvertFinished() {
		return isConvertFinished;
	}

	public void setIsConvertFinished(String isConvertFinished) {
		this.isConvertFinished = isConvertFinished;
	}
	
	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	public void setDestination(Queue destination) {
		this.destination = destination;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setTokenValidateAddress(String tokenValidateAddress) {
		this.tokenValidateAddress = tokenValidateAddress;
	}

	public String getToken() {
		return vui.getToken();
	}
	
	public void setToken(String token) {
		vui.setToken(token) ;
	}


	public File getFiledata() {
		return filedata;
	}

	public void setFiledata(File filedata) {
		this.filedata = filedata;
	}
	
	private String playAddress;
	
	
	public String getPlayAddress() {
		return playAddress;
	}

	public void setPlayAddress(String playAddress) {
		this.playAddress = playAddress;
	}
    
	public String play(){
		if( videoInformation.getId() != null ) {
			videoInformation = videoManagerDAO.loadVideoInformation( videoInformation.getId() ) ;
			if( videoInformation != null ){
				return SUCCESS ;
			}
		}
		return ERROR ;
	}
	@SuppressWarnings("unchecked")
	public String upload(){
		if( filedata == null || !filedata.exists() ) {
			return SUCCESS ;
		}		
		try {
			Object resOfValidate = tokenSecurity.validateToken( tokenValidateAddress , getToken() , timeout ) ;
			if( resOfValidate == null ) return INPUT ;
			
			if( !( resOfValidate instanceof Map ) ) {
				throw new Exception( "validation response should be map" ) ;
			}
			
			Map mapOfValidate = (Map)resOfValidate ;
			if( !mapOfValidate.containsKey( AuthInterceptor.UserIdKey ) ) {
				throw new Exception( "validation response have not required userId information" ) ;
			} 
			vui.setUserId( mapOfValidate.get( AuthInterceptor.UserIdKey ).toString( )  ) ;			
			
			if( filedata != null && filedata.exists() ) {
				int idx = filedata.getAbsolutePath().lastIndexOf( "." ) ;
				String newFileName = null ;
				if( idx >= 0 ){
					newFileName = filedata.getAbsolutePath().substring( 0 , idx ) + vui.getToken() + filedata.getAbsolutePath().substring( idx ) ;
				} else {
					newFileName = filedata.getAbsolutePath() + vui.getToken() ;
				}
				File newFile = new File( newFileName ) ;
				if( filedata.renameTo( newFile ) ){					
				    vui.setFileAbsPath( newFile.getAbsolutePath() ) ;
					logger.info( "now notify the convert file already uploaded." + " source:" + vui.getFileAbsPath() + " token:" + vui.getToken() ) ;
					template.convertAndSend( destination , vui ) ;
				} else {
					throw new Exception( "can not rename file:" + filedata.getAbsolutePath( ) + " --> " + newFileName + " token duplicated:" + vui.getToken() ) ;
				}
			}
			return SUCCESS ;
		} catch( Exception e ){
			logger.error( "" , e ) ;
			return ERROR ;
		}
	}
    
	public String getImageFolder() {
		return imageFolder;
	}

	public void setImageFolder(String imageFolder) {
		this.imageFolder = imageFolder;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public InputStream getImageStream() throws Exception {
		if( imageUrl == null && videoInformation.getId() != null ) {
			videoInformation = videoManagerDAO.loadVideoInformation( videoInformation.getId() ) ;
			if( videoInformation != null ){
				imageUrl = videoInformation.getImageUrl() ;
			}
		}		
		if( imageUrl == null || !imageUrl.toLowerCase().endsWith( prototype.Constants.CONVERTED_IMAGE_SUFFIX ) ){
			imageUrl = getRealPath() + splashUrl ;
		} else {
			imageUrl = imageFolder + imageUrl ;
		}
		
		return new java.io.FileInputStream( new File( imageUrl ) ) ;
	}
	
	private String snapshotToken = null ;
	private Long snapshotTime = null ;	
	
	public void setSnapshotToken(String snapshotToken) {
		this.snapshotToken = snapshotToken;
	}

	public void setSnapshotTime(Long snapshotTime) {
		this.snapshotTime = snapshotTime;
	}

	public String snapshot(){
		if( snapshotToken != null && snapshotTime != null ) {
			vui.setSnapshotTime(snapshotTime) ;
			vui.setToken(snapshotToken) ;
			vui.setUserId( getUserId() ) ;
			template.convertAndSend( destination , vui ) ;
		}		
		return NONE ;
	}

	public VideoInformation getVideoInformation() {
		return videoInformation;
	}
}

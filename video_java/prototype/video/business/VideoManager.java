package prototype.video.business;

import java.util.Map;

import javax.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import prototype.video.dao.IVideoManagerDAO;
import prototype.video.domain.VideoInformation;

public class VideoManager {
	Logger logger = LoggerFactory.getLogger( VideoManager.class ) ;
	private IVideoManagerDAO videoManagerDAO ;	
	private JmsTemplate template;
	private Queue destination;	
	
	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	public void setDestination(Queue destination) {
		this.destination = destination;
	}

	public void setVideoManagerDAO(IVideoManagerDAO videoManagerDAO) {
		this.videoManagerDAO = videoManagerDAO;
	}
	
	@SuppressWarnings("unchecked")
	public void onVideoConvertFinished( Map videoInformation  ) {
		String token         = (String)videoInformation.get( "token"    ) ;
		String videoUrl      = (String)videoInformation.get( "videoUrl" ) ;
		String imageUrl      = (String)videoInformation.get( "imageUrl" ) ;
		Boolean succeed      = (Boolean)videoInformation.get( "succeed" ) ;
		Long sourceType      = (Long)videoInformation.get( "sourceType" ) ;
		String userId        = (String)videoInformation.get( "userId" ) ;
		
		try {
			if( token == null || videoUrl == null ) {
				throw new Exception( "incorrect JMS message[token:" + token + ",videoUrl:" + videoUrl + "]" ) ;
			}
			if( logger.isInfoEnabled() ){
				logger.info( 
				    "video upload&convert finished with "
				  + " sourceType:[" + sourceType + "]" 
				  + " token:[" + token + "]" 
				  + " videoUrl:[" + videoUrl + "]" 
				  + " imageUrl:[" + imageUrl + "]"
				  + " succeed:["  + succeed  + "]" 
				  + " userId:["  + userId  + "]" 
				) ;
			}
			
			VideoInformation vi = new VideoInformation() ;
			vi.setSucceed(succeed)   ;
			vi.setImageUrl(imageUrl) ;
			vi.setVideoUrl(videoUrl) ;
			vi.setToken( token ) ;
			vi.setUserId( userId ) ;
			vi.setSourceType( sourceType ) ;	
			videoManagerDAO.saveVideoInformation( vi ) ;
			
			if( logger.isDebugEnabled() ){
				logger.debug( "now start to notify the video has been succeessfully persted and record has been created" ) ;
			}	
			if( succeed ) {
				template.convertAndSend( destination , vi ) ;
			}
		} catch( Exception e ) {
			logger.error( "internal error" , e ) ;
		} 
	}
}

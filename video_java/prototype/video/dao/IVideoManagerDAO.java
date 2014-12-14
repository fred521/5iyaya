package prototype.video.dao;

import prototype.video.domain.VideoInformation;

public interface IVideoManagerDAO {
	
	public Long saveVideoInformation( VideoInformation vi ) ;
	
	public VideoInformation getLiveVideoInformation( String userId ) ;
	
	public VideoInformation loadVideoInformation( Long vid  ) ;
	
}
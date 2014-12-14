package prototype.video.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class VideoInformation extends prototype.jms.JSONMessage implements Serializable {
	private static final long serialVersionUID = -7752406218857207193L;
	
	private Long    id        ;
	private String  videoUrl  ;
	private String  imageUrl  ;
	private String  userId    ;
	private Long    sourceType;
	private String  token     ;	
	private Boolean succeed   ;
	
	public Boolean getIsLive(){
		return sourceType == prototype.Constants.VIDEO_SOURCE_TYPE_LIVE ;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Boolean getSucceed() {
		return succeed;
	}
	public void setSucceed(Boolean succeed) {
		this.succeed = succeed;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getSourceType() {
		return sourceType;
	}
	public void setSourceType(Long sourceType) {
		this.sourceType = sourceType;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }
	public boolean equals(Object other) {
        if ((this == other))
            return true;
        if (!(other instanceof VideoInformation))
            return false;
        VideoInformation castOther = (VideoInformation) other;
        return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
    }
}

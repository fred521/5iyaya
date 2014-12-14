package prototype.video.domain;

import prototype.jms.JSONMessage;

public class VideoUploadInformation extends JSONMessage {

	private String token ;
	private String fileAbsPath ;	
	private String userId ;
	private Long snapshotTime = null ;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFileAbsPath() {
		return fileAbsPath;
	}

	public void setFileAbsPath(String fileAbsPath) {
		this.fileAbsPath = fileAbsPath;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getSnapshotTime() {
		return snapshotTime;
	}

	public void setSnapshotTime(Long snapshotTime) {
		this.snapshotTime = snapshotTime;
	}
	
	
}

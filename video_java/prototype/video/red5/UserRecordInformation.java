package prototype.video.red5;

@SuppressWarnings("serial")
public class UserRecordInformation implements java.io.Serializable {

	private String  token ;
	private Long length;
	private String  userId;
	private Long  lastAccesseTime;
	private Long  accessedTime = 0L ;
	private boolean isPaused = false;
	private String mode ;
	private boolean isNotified = false ;
	
	public boolean isNotified() {
		return isNotified;
	}
	public void setNotified(boolean isNotified) {
		this.isNotified = isNotified;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public boolean isPaused() {
		return isPaused;
	}
	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getLength() {
		return length;
	}
	public void setLength(Long length) {
		this.length = length;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getLastAccesseTime() {
		return lastAccesseTime;
	}
	public void setLastAccesseTime(Long lastAccesseTime) {
		this.lastAccesseTime = lastAccesseTime;
	}
	public Long getAccessedTime() {
		return accessedTime;
	}
	public void setAccessedTime(Long accessedTime) {
		this.accessedTime = accessedTime;
	}
	
}

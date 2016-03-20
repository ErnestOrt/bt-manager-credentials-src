package org.ernest.applications.bt.db.manager.credentials.ct;

public class ValidateOutput {

	private boolean validated;
	private String userId;
	
	public boolean isValidated() {
		return validated;
	}
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
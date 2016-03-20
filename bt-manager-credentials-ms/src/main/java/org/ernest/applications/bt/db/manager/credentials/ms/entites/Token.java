package org.ernest.applications.bt.db.manager.credentials.ms.entites;

public class Token {
	
	private String _id;
	private String _rev;
	
	private String userCredentialsId;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_rev() {
		return _rev;
	}

	public void set_rev(String _rev) {
		this._rev = _rev;
	}

	public String getUserCredentialsId() {
		return userCredentialsId;
	}

	public void setUserCredentialsId(String userCredentialsId) {
		this.userCredentialsId = userCredentialsId;
	}
}
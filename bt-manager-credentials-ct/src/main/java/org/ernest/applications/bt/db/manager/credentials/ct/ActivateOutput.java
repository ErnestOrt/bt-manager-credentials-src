package org.ernest.applications.bt.db.manager.credentials.ct;

public class ActivateOutput {

	private String mail;
	private String name;
	
	public ActivateOutput(String mail, String name) {
		this.mail = mail;
		this.name = name;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

package com.main.system;

public class Session {
	private String id;	
	private String authKey;
	
	public Session(String id, String authKey) {
		this.id = id;
		this.authKey = authKey;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
}

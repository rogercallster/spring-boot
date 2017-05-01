package org.ws.web.model;

public class Messages {
	
	
	public Messages(String message, String id) {
		super();
		this.message = message;
		this.id = id;
	}
	
	private String message;
	private String id;
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}

package org.ws.web.model;

public class Tweet {

	private String message;
	private int id;
	private int personId;
	
	
	
	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public Tweet(String message, int id, int personId) {
		super();
		this.message = message;
		this.id = id;
		this.personId = personId;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}

package org.ws.web.model;

public class Tweet {

	private String message;
	private int id;
	 
	
	public Tweet(String message, int id, int personId, String name) {
		super();
		this.message = message;
		this.id = id;
		this.person = new Person(personId, name);
		
		
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	private Person person;

	
	
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

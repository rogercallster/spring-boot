package org.ws.web.model;

import java.util.LinkedHashSet;


public class User {

	private int id;
	private String name;
	LinkedHashSet <User> following;
	public User(int  id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.following = new LinkedHashSet <User>();
	}

	
	 @Override
	    public String toString() {
	        return String.format(
	                "Customer[id=%s, name='%s']",
	                id, name);
	    }

	public User() {
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}

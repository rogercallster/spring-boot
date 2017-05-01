package org.ws.web.model;

import java.util.LinkedHashSet;


public class Person {

	private int id;
	private String name;
	LinkedHashSet <Person> following;
	public Person(int  id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.following = new LinkedHashSet <Person>();
	}

	
	 @Override
	    public String toString() {
	        return String.format(
	                "Customer[id=%s, name='%s']",
	                id, name);
	    }

	public Person() {
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

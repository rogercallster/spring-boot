package org.ws.web.model;

public class Follow {
	

	private int id;
	private Person current;
	private Person follower;

	public Follow(int id, Person current, Person follower) {
		super();
		this.current = current;
		this.follower = follower;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Person getCurrent() {
		return current;
	}
	public void setCurrent(Person current) {
		this.current = current;
	}
	public Person getFollower() {
		return follower;
	}
	public void setFollower(Person follower) {
		this.follower = follower;
	}
}

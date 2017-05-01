package org.ws.web.model;

public class Followers {
	
	private Person current;
	private Person follower;

	public Followers(Person current, Person follower) {
		super();
		this.current = current;
		this.follower = follower;
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

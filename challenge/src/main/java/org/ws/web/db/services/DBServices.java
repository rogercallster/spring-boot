package org.ws.web.db.services;

import java.util.List;

import org.ws.web.model.Tweet;
import org.ws.web.model.Person;

public interface DBServices {
	


	public boolean follow(Person currentUser, Person newUser);

	public boolean unfollow(Person currentUser, Person newUser);

	public List<Person> getFollowingAndFollower(int userId);

	public List<Tweet> readTweets(int userId);

}

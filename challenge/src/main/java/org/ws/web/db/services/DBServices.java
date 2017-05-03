package org.ws.web.db.services;

import java.util.List;

import org.ws.web.model.Tweet;
import org.ws.web.model.Person;

public interface DBServices {

	public boolean follow(int currentUser, int newUser);

	public boolean unfollow(int currentUser, int newUser);

	public List<Person> getFollowingAndFollower(int userId);

	public List<Tweet> readTweets(int userId, String searchFilters);

}

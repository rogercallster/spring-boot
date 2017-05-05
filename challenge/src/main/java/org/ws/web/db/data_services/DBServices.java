package org.ws.web.db.data_services;

import java.util.List;

import org.ws.web.model.Person;
import org.ws.web.model.Tweet;

public interface DBServices {

	public List<Person> getFollowingAndFollower(String user);

	public boolean unfollow(int userToUnfollow, String currentUser);

	public boolean follow(int userToBeFollowed, String currentUser);

	public List<Tweet> readTweets(String currentUser, String keyword);

	public boolean isValidPerson(int dst);

}

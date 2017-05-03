package org.ws.web.db;

import java.util.List;

import org.ws.web.model.Person;
import org.ws.web.model.Tweet;

public interface IDAO {

	 public List<Integer> getFollowing(int id);
	 public boolean delete(int userId, int id);
	 public boolean insert(int userId, int currentuser);
	 public List<Person> queryFollowAndFollowerRecords(int id);
	 public List<Person> getUsers(int[] id);
	 public Person getUser(String username);
	 public List<Tweet> getTweets(List<Integer> userIds, String keyword);
	 
}

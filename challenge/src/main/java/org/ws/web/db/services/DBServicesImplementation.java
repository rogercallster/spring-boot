package org.ws.web.db.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ws.web.db.DAOImplementation;
import org.ws.web.model.Person;
import org.ws.web.model.Tweet;

/**
 * This class have all the services business logic
 * */

@Service
public class DBServicesImplementation implements DBServices {

	@Autowired
	DAOImplementation dao;

	public DBServicesImplementation(final DAOImplementation dbo) {
		this.dao = dbo;
	}

	/**
	 * 
	 * This method is responsible for getting all the tweets from current user and all the f
	 * 
	 * 
	 * */

	@Override
	public List<Tweet> readTweets(String currentUser, String keyword) {
		int id = getUserDetails(currentUser).getId();
		List<Integer> persons = dao.getFollowing(id);
		persons.add(id);
		return dao.getTweets(persons, keyword);

	}

	public Person getUserDetails(String username) {
		return dao.getUser(username);
	}

	@Override
	public List<Person> getFollowingAndFollower(String user) {
		int id = getUserDetails(user).getId();
		;
		List<Person> list = dao.queryFollowAndFollowerRecords(id);
		return list;
	}

	/**
	 * this method finds shortest distance between source and Destination.
	 * 
	 * 
	 * */
	public int getDistance(String currentUser, int dst) {
		int src = getUserDetails(currentUser).getId();

		// If out range if src or dst is zero or negative
		if (src <= 0 || dst <= 0)
			return Integer.MAX_VALUE;

		Map<Integer, Integer> visited = new HashMap<Integer, Integer>();
		List<Integer> queue = new ArrayList<>();

		queue.add(src);
		int current = src;
		visited.put(src, 0);

		while (queue.size() > 0) {
			current = queue.get(0);
			queue.remove(0);

			List<Integer> followingList = dao.getFollowing(current);

			for (int tempId : followingList) {

				if (visited.get(tempId) == null) {
					visited.put(tempId, visited.get(current) + 1);
				} else {
					if (visited.get(tempId) > visited.get(current) + 1)
						visited.put(tempId, visited.get(current) + 1);
				}
				if (tempId != dst && tempId != src) {
					if (visited.get(dst) != null && visited.get(current) + 1 >= visited.get(dst))
						continue;
					queue.add(tempId);
				}
			}
		}

		return visited.get(dst);
	}

	// going for two db query because http://localhost:8080/h2-console is no more working with 404 error. Some bean
	// setting to be fixed
	@Override
	public boolean follow(int userToBeFollowed, String currentUser) {

		int currentUserId = getUserDetails(currentUser).getId();
		// make sure call is idempotent
		unfollow(userToBeFollowed, currentUser);
		return dao.insert(userToBeFollowed, currentUserId);
	}

	@Override
	public boolean unfollow(int userId, String currentUser) {
		int id = getUserDetails(currentUser).getId();
		return dao.delete(userId, id);

	}

}

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
	 * This method is responsible for getting all the tweet from current user and all the followers
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
	 * this method finds shortest distance between source and destination.
	 * */
	public int getDistance(String currentUser, int destination) {

		int source = getUserDetails(currentUser).getId();
		if (!(isValidPerson(destination) && isValidPerson(source)))
			return Integer.MAX_VALUE;
		// initialize lookup distance map
		Map<Integer, Integer> visited = new HashMap<Integer, Integer>();
		// initialize iteration queue
		List<Integer> queue = new ArrayList<>();

		// add source to queue as first element to start iterate over adjacency list
		queue.add(source);
		int current = source;

		// set distance from source to source = 0 as baseline
		visited.put(source, 0);

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
				if (tempId != destination && tempId != source) {
					if (visited.get(destination) != null && visited.get(current) + 1 >= visited.get(destination))
						continue;
					queue.add(tempId);
				}
			}
		}

		return visited.get(destination) != null ? visited.get(destination) : Integer.MAX_VALUE;
	}

	public boolean isValidPerson(int dst) {
		List<Person> list = dao.queryPerson(dst);
		return list != null && list.size() == 1;
	}

	/**
	 * Note: Needed two db query because http://localhost:8080/h2-console is no more working with 404 error.
	 */

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

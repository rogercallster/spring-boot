package org.ws.web.db.data_services;

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
 * This class have all the services business logic to help RESTful services complete all the required task
 * */

@Service
public class DBServicesImplementation implements DBServices {

	@Autowired
	DAOImplementation dao;

	
	public DBServicesImplementation(final DAOImplementation dbo) {
		this.dao = dbo;
	}

	/**
	 * @Description This method is responsible for getting all the tweets from current user and all the followers of
	 *              current user
	 *              [{message: {},Person : {}} , ...]
	 * */

	@Override
	public List<Tweet> readTweets(String currentUser, String keyword) {
		int id = getUserDetails(currentUser).getId();
		List<Integer> persons = dao.getFollowing(id);
		persons.add(id);
		return dao.getTweets(persons, keyword);

	}
	
	
	/**
	 * @Description Method to find all the following and follower list from DB
	 * */
	@Override
	public List<Person> getFollowingAndFollower(String user) {
		
		int id = getUserDetails(user).getId();
		List<Person> list = dao.queryFollowAndFollowerRecords(id);
		return list;
	}

	/**
	 * @Description method "getShortestDistance" finds shortest distance between source(most probably current user) and
	 *              destination.
	 * 
	 *              In case two nodes are not connected we return Integer.MAX_VALUE If dst node is not valid it should
	 *              never reach here but in case it reaches here it will return Integer.MAX_VALUE
	 * */
	public int getShortestDistance(String currentUser, int destination) {

		int source = getUserDetails(currentUser).getId();

		if (!(isValidPerson(destination) && isValidPerson(source)))
			return Integer.MAX_VALUE;
		
		// initialize lookup distance map
		Map<Integer, Integer> visitedDistance = new HashMap<Integer, Integer>();

		// initialize iteration queue
		List<Integer> queue = new ArrayList<>();

		// add source to queue as first element to start iteration process over adjacency list
		queue.add(source);
		int currentNode = source;

		// set distance from source to source = 0 as baseline
		visitedDistance.put(source, 0);

		while (queue.size() > 0) {
			currentNode = queue.get(0);
			queue.remove(0);

			List<Integer> followingList = dao.getFollowing(currentNode);

			for (int tempNextNode : followingList) {
				// while node is first time iterated add create distance object and insert it in distance tracker
				if (visitedDistance.get(tempNextNode) == null) {
					visitedDistance.put(tempNextNode, visitedDistance.get(currentNode) + 1);
					updateQueue(destination, source, queue, tempNextNode);
				// if distance of current node is less through current node update the distance
				} else if (visitedDistance.get(tempNextNode) > visitedDistance.get(currentNode)+1) {
					visitedDistance.put(tempNextNode, visitedDistance.get(currentNode) + 1);
					updateQueue(destination, source, queue, tempNextNode);
				}
			}

		}

		return visitedDistance.get(destination) != null ? visitedDistance.get(destination) : Integer.MAX_VALUE;
	}

	private void updateQueue(int destination, int source, List<Integer> queue, int tempNextNode) {
		if (tempNextNode != source || tempNextNode != destination)
			queue.add(tempNextNode);
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
	
	@Override
	public boolean isValidPerson(int dst) {
		List<Person> list = dao.queryPerson(dst);
		return list != null && list.size() == 1;
	}
	
	private Person getUserDetails(String username) {
		return dao.getUser(username);
	}
	
	

}

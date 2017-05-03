package org.ws.web.db.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ws.web.db.DAO;
import org.ws.web.model.Person;
import org.ws.web.model.Tweet;

@Service
public class DBServicesImplementation implements DBServices {

	@Autowired
	DAO dao;

	public DBServicesImplementation(final DAO dbo) {
		this.dao = dbo;
	}

	@Override
	public List<Tweet> readTweets(int userId, String keyword) {
		List<Integer> persons = dao.getFollowing(userId);
		persons.add(userId);
		return dao.getTweets(persons, keyword );

	}



	@Override
	public boolean unfollow(int userId, int id) {
		return dao.delete(userId, id);

	}

	public Person getUserDetails(String username) {
		return dao.getUser(username);
	}

	@Override
	public List<Person> getFollowingAndFollower(int userId) {
		List<Person> list = dao.queryFollowAndFollowerRecords(userId);
		return list;
	}

	public int getDistance(int src, int dst) {
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

			List<Integer> list = dao.getFollowing(current);

			for (int i : list) {

				if (visited.get(i) == null) {
					visited.put(i, visited.get(current) + 1);
				} else {
					if (visited.get(i) > visited.get(current) + 1)
						visited.put(i, visited.get(current) + 1);
				}
				if (i != dst && i != src) {
					if (visited.get(dst) != null && visited.get(current) + 1 >= visited.get(dst))
						continue;
					queue.add(i);
				}

			}
		}

		return visited.get(dst);
	}
	@Override
	public boolean follow(int id, int username) {
		//going for two db query because http://localhost:8080/h2-console is no more working.
		unfollow(id, username);
		return dao.insert(id, username);
	}

}

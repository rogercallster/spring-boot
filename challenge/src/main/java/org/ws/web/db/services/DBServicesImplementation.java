package org.ws.web.db.services;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ws.web.db.DAO;
import org.ws.web.model.Follow;
import org.ws.web.model.Tweet;
import org.ws.web.model.Person;

@Service
public class DBServicesImplementation implements DBServices {

	@Autowired
	DAO dao;

	public DBServicesImplementation(final DAO dbo) {
		this.dao = dbo;
	}

	@Override
	public List<Tweet> readTweets(int userId) {
		List<Integer> persons = dao.queryFollowers(userId);
		persons.add(userId);
		return dao.getTweets(persons);

	}

	@Override
	public boolean follow(Person currentUser, Person newUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unfollow(Person currentUser, Person newUser) {
		// TODO Auto-generated method stub
		return false;
	}

	public Person getUserDetails(String username) {
		return dao.getUser(username);
	}
	
	@Override
	public List<Person> getFollowingAndFollower(int userId) {
		List<Person> list = dao.queryFollowAndFollowerRecords(userId);
		return list;
	}

	public boolean delete(int userId, int id) {
		 return dao.delete(userId, id);
		
	}

	public int getDistance(int src, String dst) {
		// calculate shortest distance
		return 0;
	}

	

}

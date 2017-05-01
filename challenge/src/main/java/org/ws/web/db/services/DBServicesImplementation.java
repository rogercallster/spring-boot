package org.ws.web.db.services;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ws.web.db.DAO;
import org.ws.web.model.Tweet;
import org.ws.web.model.Person;

@Service
public class DBServicesImplementation implements DBServices {

	@Autowired
	DAO dao;
	
	public DBServicesImplementation(final DAO dbo){
		this.dao=dbo;
	}  
	
	@Override
	public List<Object> readMessage(String userName) {
		return dao.query("");
		
	}

	@Override
	public List<Person> getListOfFollowersAndFollows(Person user) {
		// TODO Auto-generated method stub
		return null;
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

}

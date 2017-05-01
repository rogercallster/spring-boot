package org.ws.web.db.services;

import java.util.List;

import org.ws.web.model.Tweet;
import org.ws.web.model.Person;

public interface DBServices {
	
	public List<Object> readMessage(String string);

	public List<Person> getListOfFollowersAndFollows(Person user);

	public boolean follow(Person currentUser, Person newUser);

	public boolean unfollow(Person currentUser, Person newUser);

}

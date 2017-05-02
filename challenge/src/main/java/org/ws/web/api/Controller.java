package org.ws.web.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.ws.web.db.DAO;
import org.ws.web.db.services.DBServices;
import org.ws.web.db.services.DBServicesImplementation;
import org.ws.web.model.Follow;
import org.ws.web.model.Tweet;
import org.ws.web.model.Person;

import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;

@RestController
public class Controller {

	@Autowired
	DBServicesImplementation dbServices;

	@RequestMapping(value = "/api/messages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Tweet>> getMessages(
			@AuthenticationPrincipal UserDetails currentUser) {

		int userId = dbServices.getUserDetails(currentUser.getUsername())
				.getId();

		List<Tweet> tweets = dbServices.readTweets(userId);
		return new ResponseEntity<>(tweets, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/messages/{user}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> getShortestDistance(
			@AuthenticationPrincipal UserDetails currentUser, @PathVariable("user") String dst) {

		int src = dbServices.getUserDetails(currentUser.getUsername())
				.getId();
		
		int distance = dbServices.getDistance(src, dst);
		return new ResponseEntity<>(distance, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/followers_and_following", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Person>> getListOfPeopleUserFollows(
			@AuthenticationPrincipal UserDetails currentUser) {

		int userId = dbServices.getUserDetails(currentUser.getUsername())
				.getId();
		List<Person> users = dbServices.getFollowingAndFollower(userId);
		return new ResponseEntity<>(users, HttpStatus.OK);

	}

	@RequestMapping(value = "/api/user/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> follow(@RequestBody int id,
			@AuthenticationPrincipal UserDetails currentUser) {

		boolean sucess = false;

		//sucess = dbServices.updateFollowingList(currentUser.getUsername(), user);
		return new ResponseEntity<Boolean>(sucess, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/api/follower/remove/{id}", method = RequestMethod.DELETE, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Boolean> unfollowByID(@AuthenticationPrincipal UserDetails currentUser, @PathVariable("id") String id) {
		int userId = dbServices.getUserDetails(currentUser.getUsername()).getId();
		dbServices.delete( Integer.parseInt(id), userId);
		return new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);
	}


}

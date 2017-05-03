package org.ws.web.controllers;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ws.web.db.services.DBServicesImplementation;
import org.ws.web.model.Person;
import org.ws.web.model.Tweet;

/**
 * Usage and notes:
 * 
 * All user have username as : FirstName+Space+LastName e.g. "Rigel Young" and password = "123" -Basic auth details in
 * SecurityConfiuration class.
 * 
 * This Controller have following rest services
 * 
 * GET:
 * 
 * 1) getMessages USAGE http://<HOST:PORT>/api/messages?<search = keyword> 
 * 2) getListOfPeopleUserFollows USAGE http://<HOST:PORT>/api/followers_and_following 
 * 5) getShortestDistance USAGE  http://<HOST:PORT>/api/shortest_distance_to/{user}
 * 
 * PUT: follow USAGE http://<HOST:PORT>/api/user/follow {"id" : "7" , <other optional details>}
 * 
 * DELETE: unfollow USAGE http://<HOST:PORT>/api/follower/unfollow/{id}
 * 
 * */
@RestController
public class Controller {

	@Autowired
	DBServicesImplementation dbServices;

	/**
	 * @GET
	 *  getMessages USAGE http://<HOST:PORT>/api/messages?<search = keyword> e.g.
	 * localhost:8080/api/messages?search=dolor dapibus gravida 
	 * will get back with response as :
	 * [{"message":"ac sem ut dolor dapibus gravida. Aliquam tincidunt, nunc ac"
	 * ,"id":104,"person":{"id":1,"name":"Rigel Young"}}] *?
	 * 
	 * @param currentUser
	 * @param searchFilter
	 * @return
	 */
	@RequestMapping(value = "/api/messages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Tweet>> getMessages(@AuthenticationPrincipal UserDetails currentUser,
			@RequestParam(value = "search", required = false) String searchFilter) {

		int userId = dbServices.getUserDetails(currentUser.getUsername()).getId();

		List<Tweet> tweets = dbServices.readTweets(userId, searchFilter);
		return new ResponseEntity<>(tweets, HttpStatus.OK);
	}

	/**
	 * @GET
	 * 
	 * 
	 * 
	 * @param currentUser
	 * @return 
	 */
	@RequestMapping(value = "/api/followers_and_following", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Person>> getListOfPeopleUserFollows(@AuthenticationPrincipal UserDetails currentUser) {

		int userId = dbServices.getUserDetails(currentUser.getUsername()).getId();
		List<Person> users = dbServices.getFollowingAndFollower(userId);
		return new ResponseEntity<>(users, HttpStatus.OK);

	}

	/**
	 * @PUT : Idempotent
	 * 
	 * 
	 * 
	 * @param id
	 * @param currentUser
	 * @return 
	 */
	@RequestMapping(value = "/api/user/follow", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> follow(@RequestBody String id, @AuthenticationPrincipal UserDetails currentUser) {

		JSONObject json = new JSONObject(id);
		id = json.getString("id");
		boolean sucess = false;
		int userId = dbServices.getUserDetails(currentUser.getUsername()).getId();
		sucess = dbServices.follow(Integer.parseInt(id), userId);
		return new ResponseEntity<Boolean>(sucess, HttpStatus.CREATED);
	}

	/**
	 * @DELETE
	 * 
	 * 
	 * 
	 * @param currentUser
	 * @return 
	 */
	@RequestMapping(value = "/api/follower/unfollow/{id}", method = RequestMethod.DELETE, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Boolean> unfollowByID(@AuthenticationPrincipal UserDetails currentUser,
			@PathVariable("id") String id) {

		int userId = dbServices.getUserDetails(currentUser.getUsername()).getId();
		dbServices.unfollow(Integer.parseInt(id), userId);
		return new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);
	}

	/**
	 * @GET
	 * 
	 * 
	 * 
	 * @param source
	 * @param destination
	 * @return 
	 */
	@RequestMapping(value = "/api/shortest_distance_to/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> getShortestDistance(@AuthenticationPrincipal UserDetails source,
			@PathVariable("userId") String destination) {

		int src = dbServices.getUserDetails(source.getUsername()).getId();
		int distance = dbServices.getDistance(src, Integer.valueOf(destination));
		return new ResponseEntity<>(distance, HttpStatus.OK);
	}

}

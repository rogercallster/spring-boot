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
import org.ws.web.db.data_services.DBServicesImplementation;
import org.ws.web.model.Person;
import org.ws.web.model.Tweet;

/**
 * 
 * @ProblemStatement Title: Mini Twitter Coding Challenge
 * 
 *                   Create the backend for a mini messaging service, inspired by Twitter. It should have a RESTful API
 *                   where all endpoints require HTTP Basic authentication and generate output in JSON format. Implement
 *                   the the following basic functionality:
 * 
 *                   1 An endpoint to read the message list for the current user (as identified by their HTTP Basic
 *                   authentication credentials). Include messages they have sent and messages sent by users they
 *                   follow. Support a “search=” parameter that can be used to further filter messages based on keyword.
 * 
 *                   2 Endpoints to get the list of people the user is following as well as the followers of the user.
 * 
 *                   3 An endpoint to start following another user.
 * 
 *                   4 An endpoint to unfollow another user.
 * 
 *                   5 An endpoint that returns the current user's "shortest distance" to some other user. The shortest
 *                   distance is defined as the number of hops needed to reach a user through the users you are
 *                   following (not through your followers; direction matters). For example, if you follow user B, your
 *                   shortest distance to B is 1. If you do not follow user B, but you do follow user C who follows user
 *                   B, your shortest distance to B is 2.
 * 
 * 
 * @Notes:
 * 
 *         a) Credentials : All user have username as : FirstName+Space+LastName e.g. "Rigel Young" and password = "123"
 *         -Basic auth implementation is in SecurityConfiuration class.
 * 
 *         b) No support for Pagination as question did not have any details or requirement for it.
 * 
 *         c) Exception handling safety nets dependent on core platform exception handling
 * 
 *         d) Couple of messages do have Bad argument exception but I have not tried to Implement any Message/Error
 *         handling as might be a stretch task.
 * 
 *         e) Further enhancement will include but not limited to. : 1) Exception Handling & Implementation of Http
 *         error message if required. 2) DB layer abstraction to optimizing on query without affecting DAO object. 3)
 *         Pagination Support. 4) Other filter support based on requirements. 5) Implementing Hypertext As The Engine Of
 *         Application State by adding links and hrefs to JSON object. 6) Optimize Maturity model specially Http status
 *         code and HATEOAS
 * 
 * 
 * 
 *         Methods list:
 * 
 * @GET:
 * 
 *       1) retriveMessages 
 *       		@USAGE http://<HOST:PORT>/api/user/messages?<search = keyword>
 *       2) retriveFollowingAndFollower
 *       		@USAGE http://<HOST:PORT>/api/user/followers_and_following 
 *       3) getShortestDistance
 *       		@USAGE http://<HOST:PORT>/api/user/shortest_distance/{user}
 * 
 * @PUT: follow @USAGE http://<HOST:PORT>/api/user/follow with body as: {"id" : "7"}
 * 
 * @DELETE: unfollow 
 * 				@USAGE http://<HOST:PORT>/api/user/follow/{id}
 * 
 * 
 * @author ankursar AT buffalo DOT edu
 * */

@RestController
public class Controller {

	@Autowired
	DBServicesImplementation dbServices;

	/**
	 * @GET
	 * 
	 * @USAGE http://<HOST:PORT>/api/user/messages?<search = keyword> e.g.
	 *        http://<hostname>:8080/api/user/messages?search=dolor dapibus gravida
	 * 
	 *        should get :
	 * 
	 *        "[{"message":"ac sem ut dolor dapibus gravida. Aliquam tincidunt, nunc
	 *        ac","id":104,"person":{"id":1,"name":"Rigel Young"}}]"
	 * 
	 * @param currentUser
	 * @param searchFilter
	 * @return
	 */

	@RequestMapping(value = "/api/user/messages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Tweet>> retriveMessages(@AuthenticationPrincipal UserDetails currentUser,
			@RequestParam(value = "search", required = false) String searchFilter) {

		List<Tweet> tweets = dbServices.readTweets(currentUser.getUsername(), searchFilter);
		return new ResponseEntity<>(tweets, HttpStatus.OK);
	}

	/**
	 * @GET
	 * 
	 * @USAGE : http://<HOST:PORT>/api/followers_and_following
	 * 
	 * @param currentUser
	 * @return
	 */
	@RequestMapping(value = "/api/user/followers_and_following", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Person>> retriveFollowingAndFollower(@AuthenticationPrincipal UserDetails currentUser) {

		List<Person> users = dbServices.getFollowingAndFollower(currentUser.getUsername());
		return new ResponseEntity<>(users, HttpStatus.OK);

	}

	/**
	 * @PUT : Idempotent
	 * @RequestParam(params = {
	 * @Param(name = "id", description = "id of person to be followed by current person"), }
	 * 
	 * @USAGE: PUT: follow @USAGE http://<HOST:PORT>/api/user/follow and Body = {"id" : "7" , <other optional details>}
	 * 
	 * @param id
	 * @param currentUser
	 * @return
	 */
	@RequestMapping(value = "/api/user/follow", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> follow(@RequestBody String id, @AuthenticationPrincipal UserDetails currentUser) {

		boolean sucess = false;
		int personToFollow = getId(id);
		if (!dbServices.isValidPerson(personToFollow))
			return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);

		sucess = dbServices.follow(personToFollow, currentUser.getUsername());
		return new ResponseEntity<Boolean>(sucess, HttpStatus.CREATED);
	}

	/**
	 * @DELETE
	 * 
	 * @USAGE : http://<HOST:PORT>/api/user/follow/1
	 * 
	 * @param currentUser
	 * @return
	 */
	@RequestMapping(value = "/api/user/follow/{id}", method = RequestMethod.DELETE, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Boolean> unfollow(@AuthenticationPrincipal UserDetails currentUser, @PathVariable("id") int id) {

		if (!dbServices.isValidPerson(id))
			return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);

		dbServices.unfollow(id, currentUser.getUsername());
		return new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);
	}

	/**
	 * @GET
	 * 
	 *      userId is ID (int)
	 * 
	 * @USAGE : http://<HOST:PORT>/api/user/shortest_distance_to/10
	 * 
	 * @param source
	 * @param destination
	 * @return
	 */
	@RequestMapping(value = "/api/user/shortest_distance/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> getShortestDistance(@AuthenticationPrincipal UserDetails source,
			@PathVariable("userId") int destination) {

		if (!dbServices.isValidPerson(destination))
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);

		int distance = dbServices.getShortestDistance(source.getUsername(), destination);

		return new ResponseEntity<>(distance, HttpStatus.OK);
	}

	private int getId(String id) {
		JSONObject json = new JSONObject(id);
		// Spring keeps track of Bad Request
		return json.getInt("id");
	}
}

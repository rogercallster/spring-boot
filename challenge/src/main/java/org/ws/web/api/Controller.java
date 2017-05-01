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
import org.ws.web.model.Tweet;
import org.ws.web.model.Person;

import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;

@RestController
public class Controller {
	
	
	@Autowired
	DBServicesImplementation dbServices;

	@RequestMapping(value = "/api/message", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Object>> getMessages(@AuthenticationPrincipal UserDetails currentUser) {
		
		
		List<Object> list = dbServices.readMessage(currentUser.getUsername());
		//list.add(new Messages("Hello", 1));
		Map<String, String> messageMap = retriveMessages();
		return new ResponseEntity<List<Object>>(list,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/api/message/{sys_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getMessage(
			@PathVariable("sys_id") String sysId) {

		String id = sysId;
		if (id == null) {

			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		String message = retriveMessage(id);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Person>> getListOfPeopleUserFollows() {
		Person user = new Person();
		List<Person> list = new ArrayList<Person>();
		list.add(user);
		return new ResponseEntity<List<Person>>(list, HttpStatus.OK);
	}

	// http://MSANIT1021969.corp.service-now.com:8080//api/user/update //{"id" :
	// "1","name" :"topUser "}
	@RequestMapping(value = "/api/user/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> follow(@RequestBody Person user,
			@AuthenticationPrincipal UserDetails currentUser) {

		boolean sucess = false;

		sucess = updateFollowingList(currentUser.getUsername(), user);
		return new ResponseEntity<Boolean>(sucess, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/api/user/remove/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> unfollow() {

		return new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);
	}

	private Boolean updateFollowingList(String currentUser, Person user) {
		return true;
		// TODO Auto-generated method stub

	}

	private Map<String, String> retriveMessages() {
		Map<String, String> map = new HashMap<>();
		map.put("1", "message1");
		map.put("2", "message2");
		return map;

	}

	private String retriveMessage(String id) {
		Map<String, String> map = new HashMap<>();
		map.put("1", "message1");
		map.put("2", "message2");

		if (id != null) {
			return map.get(id);
		}
		// doSomeQuery()
		return null;

	}

}

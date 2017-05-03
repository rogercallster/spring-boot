package org.ws.web.db.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.ws.web.db.DAO;
import org.ws.web.model.Person;

@Service
public class AuthenticationService implements UserDetailsService {
	
	@Autowired
	private DAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Person userInfo = userDAO.getUser(username);
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
		String name = userInfo.getName();
		ShaPasswordEncoder encoder = new ShaPasswordEncoder();

		UserDetails userDetails = new User(name, encoder.encodePassword("123", null), Arrays.asList(authority));
		return userDetails;
	}
}
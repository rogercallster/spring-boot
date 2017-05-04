package org.ws.web.db.services.authentication;

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
import org.ws.web.db.DAOImplementation;
import org.ws.web.model.Person;

@Service
public class AuthenticationService implements UserDetailsService {

	private static final String ADMIN_ROLE = "ROLE_ADMIN";
	private static final String PASSWORD = "123";

	@Autowired
	private DAOImplementation userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Person userInfo = userDAO.getUser(username);
		GrantedAuthority authority = new SimpleGrantedAuthority(ADMIN_ROLE);
		String name = userInfo.getName();
		ShaPasswordEncoder encoder = new ShaPasswordEncoder();

		UserDetails userDetails = new User(name, encoder.encodePassword(PASSWORD, null), Arrays.asList(authority));
		return userDetails;
	}
}
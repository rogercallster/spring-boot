package org.ws.web;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.ws.web.db.services.AuthenticationService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	Environment env;
	@Autowired
	AuthenticationService authenticationService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().fullyAuthenticated();
		http.httpBasic();
		http.csrf().disable();
		// http.headers().frameOptions().disable();
		 http.authorizeRequests().antMatchers("/h2-console/*").permitAll()
		 .anyRequest().authenticated().and().formLogin()
		 .loginPage("/login").permitAll().and().logout().permitAll();
		 http.headers().frameOptions().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
//		auth.inMemoryAuthentication().withUser("user").password("password")
//				.roles("USER");
		 ShaPasswordEncoder encoder = new ShaPasswordEncoder();
         auth.userDetailsService(authenticationService).passwordEncoder(encoder);
	}
}
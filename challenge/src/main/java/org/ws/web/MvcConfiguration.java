package org.ws.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan(basePackages = "net.codejava.spring")
public
class MvcConfiguration extends WebMvcConfigurerAdapter {

	// @Override
	// public void addViewControllers(ViewControllerRegistry registry) {
	// registry.addViewController("/home").setViewName("home");
	// registry.addViewController("/").setViewName("home");
	// registry.addViewController("/hello").setViewName("hello");
	// registry.addViewController("/login").setViewName("login");
	// }
	/*
	 * 
	 * spring.datasource.url=jdbc:h2:mem:challenge
	 * spring.datasource.schema=classpath:/schema.sql
	 * spring.datasource.data=classpath:/data.sql
	 * spring.datasource.driver-class-name=com.mysql.jdbc.Driver
	 * spring.datasource.driverClassName=com.mysql.jdbc.Driver
	 */

//	public  DataSource getDataSource() {
//		
//	}
	//
	// @Bean
	// public DBOperation getContactDAO() {
	// return new DBOperation(getDataSource());
	// }
}
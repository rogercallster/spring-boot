package org.ws.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
@Configuration
@SpringBootApplication
@EnableAutoConfiguration
//@ComponentScan({"controller", "service", "restcontroller"})
//,exclude={DataSourceAutoConfiguration.class})
//@EnableAutoConfiguration ("com.server.repository")
//@ComponentScan(basePackageClasses=Controller.class)
public class ChallengeApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(ChallengeApplication.class, args);
	}
}

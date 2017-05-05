package org.ws.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @Description Class to start server
 * */
@Configuration
@SpringBootApplication
@EnableAutoConfiguration
public class SpringBootApplicationCore {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootApplicationCore.class, args);
	}
}

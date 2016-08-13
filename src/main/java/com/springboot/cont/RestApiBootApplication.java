package com.springboot.cont;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.springboot")
public class RestApiBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiBootApplication.class, args);
	}
}

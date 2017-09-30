package com.tutoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The Class OnlineTutoringApplication.
 */
@SpringBootApplication
@EnableScheduling
public class OnlineTutoringApplication {

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(OnlineTutoringApplication.class, args);
	}
}

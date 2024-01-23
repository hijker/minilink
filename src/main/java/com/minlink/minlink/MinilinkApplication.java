package com.minlink.minlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class MinilinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinilinkApplication.class, args);
	}


}

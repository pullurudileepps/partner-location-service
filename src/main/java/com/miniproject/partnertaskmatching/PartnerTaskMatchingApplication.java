package com.miniproject.partnertaskmatching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class PartnerTaskMatchingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartnerTaskMatchingApplication.class, args);
	}

}

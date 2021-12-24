package com.yakymets.creditsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@SpringBootApplication
public class CreditSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditSystemApplication.class, args);
	}

}

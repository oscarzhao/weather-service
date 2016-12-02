package com.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@RestController
public class ServiceApplication {

	@RequestMapping("/")
	public String home() {
		return "Hello World";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}
}

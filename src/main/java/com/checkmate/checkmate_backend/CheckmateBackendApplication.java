package com.checkmate.checkmate_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = { org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class })
public class CheckmateBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckmateBackendApplication.class, args);
	}

}

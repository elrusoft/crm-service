package com.polozhaev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrmserviceApplication {

	
	private static final Logger logger = LoggerFactory.getLogger(CrmserviceApplication.class);	
	
	public static void main(String[] args) {
		SpringApplication.run(CrmserviceApplication.class, args);
		logger.debug("--Application Started--");
	}
}

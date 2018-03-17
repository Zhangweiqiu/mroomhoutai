package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MroomApplication extends SpringBootServletInitializer{
	
		@Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(MroomApplication.class);
	    }

	    public static void main(String[] args) {
	        SpringApplication.run(MroomApplication.class, args);
	    }

}

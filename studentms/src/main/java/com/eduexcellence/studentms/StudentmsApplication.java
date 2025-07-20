package com.eduexcellence.studentms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class StudentmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentmsApplication.class, args);
	}
}

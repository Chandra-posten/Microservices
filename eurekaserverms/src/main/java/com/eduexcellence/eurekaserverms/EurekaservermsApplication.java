package com.eduexcellence.eurekaserverms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaservermsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaservermsApplication.class, args);
	}

}

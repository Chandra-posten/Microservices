package com.eduexcellence.fee_management_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FeeManagementMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeeManagementMsApplication.class, args);
	}

}

package com.hust.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.hust.common.entity", "com.hust.admin.user"})
public class HustGamingBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(HustGamingBackEndApplication.class, args);
	}

}

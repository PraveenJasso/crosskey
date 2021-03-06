package com.crosskey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.crosskey")
public class CrosskeyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrosskeyApplication.class, args);
	}

}

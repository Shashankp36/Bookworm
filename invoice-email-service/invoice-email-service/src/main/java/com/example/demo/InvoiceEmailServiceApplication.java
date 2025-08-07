package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.*")
@EnableDiscoveryClient 
public class InvoiceEmailServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceEmailServiceApplication.class, args);
	}

}

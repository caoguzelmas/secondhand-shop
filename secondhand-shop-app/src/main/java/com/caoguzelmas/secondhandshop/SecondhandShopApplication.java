package com.caoguzelmas.secondhandshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.caoguzelmas.secondhandshop.*"})
public class SecondhandShopApplication {
    public static void main(String[] args) {
		SpringApplication.run(SecondhandShopApplication.class, args);
	}
}

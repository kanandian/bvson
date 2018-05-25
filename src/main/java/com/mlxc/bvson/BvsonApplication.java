package com.mlxc.bvson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("com.mlxc.controller")
@ComponentScan("com.mlxc.service")
@ComponentScan("com.mlxc.config")
@EntityScan({"com.mlxc.entity", "com.mlxc.entityrelation"})
@EnableJpaRepositories("com.mlxc.dao")
@SpringBootApplication
public class BvsonApplication {

	public static void main(String[] args) {
		SpringApplication.run(BvsonApplication.class, args);
	}
}

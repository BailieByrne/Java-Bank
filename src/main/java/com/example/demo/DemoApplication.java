package com.example.demo;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

import Account.CrudAccRepository;
import Users.CrudUserRepository;



@SpringBootApplication()
@EnableJdbcRepositories(basePackageClasses = {CrudAccRepository.class,CrudUserRepository.class}) //neccessary for some reason
@ComponentScan(basePackages = {"Account", "Web","Security","Users"})
public class DemoApplication {
	
	public static final Logger log = LogManager.getLogger("MainLogger");
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		log.info("APPLICATION STARTED SUCCESSFULLY!");
	}
	
	@Bean
	CommandLineRunner runner(CrudAccRepository accountRepository) {
		return args -> {
//			CheckingAcc acc = new CheckingAcc(2, "John Doe", LocalDateTime.now(), (double) 573.27);
//			accountRepository.save(acc);
		};
	}
}
//yh



package com.vinz.energy.EnergyForge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EnergyForgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnergyForgeApplication.class, args);
	}

}

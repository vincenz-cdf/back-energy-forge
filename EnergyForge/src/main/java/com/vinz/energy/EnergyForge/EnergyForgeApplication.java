package com.vinz.energy.EnergyForge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.vinz.energy.EnergyForge")
@EnableScheduling
public class EnergyForgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnergyForgeApplication.class, args);
	}

}

package com.vinz.energy.EnergyForge.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.NonNull;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
public class AppConfig {
	
	@NonNull
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

}

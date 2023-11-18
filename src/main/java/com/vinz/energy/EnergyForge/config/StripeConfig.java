package com.vinz.energy.EnergyForge.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.stripe.Stripe;

import jakarta.annotation.PostConstruct;

@Configuration
public class StripeConfig {

	@Value("${spring.stripe.api.key}")
    private String apiKey;

    @PostConstruct
    public void setup() {
    	Stripe.apiKey = apiKey;
    }
}


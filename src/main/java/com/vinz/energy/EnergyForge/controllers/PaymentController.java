package com.vinz.energy.EnergyForge.controllers;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.vinz.energy.EnergyForge.dto.api.RequestStripePrice;
import com.vinz.energy.EnergyForge.exception.AppException;
import com.vinz.energy.EnergyForge.service.Global;
import com.vinz.energy.EnergyForge.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@Value("${spring.stripe.api.webhook}")
	private String endpointSecret;

	@Autowired
	private Global global;

	@PostMapping("/create-checkout-session")
	public String createCheckoutSession(@RequestBody RequestStripePrice stripeParams) throws StripeException {

		try {
			Long userId = global.getCurrentUserId();

			if (userId == null) {
				throw new AppException("Action cannot be performed", HttpStatus.FORBIDDEN);
			}

			Session session = paymentService.manageCheckoutSession(stripeParams, userId);

			return session.getId(); // Return the session ID
		} catch (Exception e) {
			throw new AppException(e.getMessage(), HttpStatus.FORBIDDEN);
		}

	}

	@PostMapping("/webhook")
	public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
			@RequestHeader("Stripe-Signature") String sigHeader) {
		Event event = null;

		try {
			event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
		} catch (SignatureVerificationException e) {
			// Invalid signature
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		// Handle the checkout.session.completed event
		if ("checkout.session.completed".equals(event.getType())) {
			Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
			if (session != null && session.getMetadata() != null) {
				String diamonds = session.getMetadata().get("diamonds");
				String userId = session.getMetadata().get("userId");

				if (diamonds != null && userId != null) {
					paymentService.updateUserDiamonds(Integer.valueOf(diamonds), Long.valueOf(userId));
				}
			}
		}

		return ResponseEntity.ok().build();
	}
}

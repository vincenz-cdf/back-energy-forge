package com.vinz.energy.EnergyForge.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.vinz.energy.EnergyForge.dao.UserDao;
import com.vinz.energy.EnergyForge.db.User;
import com.vinz.energy.EnergyForge.dto.api.RequestStripePrice;
import com.vinz.energy.EnergyForge.exception.AppException;

@Service
public class PaymentService {

	@Autowired
	private UserDao userDao;

	public Session manageCheckoutSession(RequestStripePrice stripeParams, Long userId) {
		try {
			List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
			lineItems.add(SessionCreateParams.LineItem
					.builder().setQuantity(
							1L)
					.setPriceData(SessionCreateParams.LineItem.PriceData.builder().setCurrency("usd")
							.setUnitAmount(stripeParams.getPrice().longValue()) // Price in cents
							.setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
									.setName(stripeParams.getQty() + " Diamonds").build())
							.build())
					.build());

			String successUrl = "http://localhost:4200/?paymentSuccess=true&qty=" + stripeParams.getQty();

			SessionCreateParams params = SessionCreateParams.builder().setSuccessUrl(successUrl)
					.setCancelUrl("http://localhost:4200/#")
					.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
					.setMode(SessionCreateParams.Mode.PAYMENT).addAllLineItem(lineItems)
					.putMetadata("userId", userId.toString()).putMetadata("diamonds", stripeParams.getQty().toString())
					.build();

			return Session.create(params);
		} catch (Exception e) {
			throw new AppException("Payment problem unexpected", HttpStatus.FORBIDDEN);
		}
	}

	public void updateUserDiamonds(Integer diamonds, Long userId) {

		User user = userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		user.setDiamond(user.getDiamond() + diamonds);

		userDao.save(user);

	}
}

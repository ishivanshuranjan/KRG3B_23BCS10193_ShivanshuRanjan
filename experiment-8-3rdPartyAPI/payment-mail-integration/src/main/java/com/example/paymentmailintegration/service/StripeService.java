package com.example.paymentmailintegration.service;

import com.example.paymentmailintegration.dto.ProductRequest;
import com.example.paymentmailintegration.dto.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secretKey}")
    private String secretKey;

    public StripeResponse checkoutProducts(ProductRequest productRequest) {
        Stripe.apiKey = secretKey;

        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(productRequest.getName())
                        .build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(productRequest.getCurrency() == null ? "inr" : productRequest.getCurrency().toLowerCase())
                        .setUnitAmount(productRequest.getAmount())
                        .setProductData(productData)
                        .build();

        SessionCreateParams.LineItem lineItem =
                SessionCreateParams.LineItem.builder()
                        .setQuantity(productRequest.getQuantity())
                        .setPriceData(priceData)
                        .build();

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(
                                "http://localhost:8080/success"
                                        + "?email=" + productRequest.getEmail()
                                        + "&name=" + productRequest.getName()
                                        + "&amount=" + productRequest.getAmount()
                                        + "&quantity=" + productRequest.getQuantity()
                        )
                        .setCancelUrl("http://localhost:8080/cancel")
                        .addLineItem(lineItem)
                        .build();

        Session session = null;

        try {
            session = Session.create(params);
        } catch (StripeException ex) {
            System.out.println(ex.getMessage());
        }

        return StripeResponse.builder()
                .status(session != null ? "SUCCESS" : "FAILED")
                .message(session != null ? "Payment session created" : "Payment session failed")
                .sessionId(session != null ? session.getId() : null)
                .sessionUrl(session != null ? session.getUrl() : null)
                .build();
    }
}
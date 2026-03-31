package com.example.paymentmailintegration.controller;

import com.example.paymentmailintegration.dto.ProductRequest;
import com.example.paymentmailintegration.dto.StripeResponse;
import com.example.paymentmailintegration.service.StripeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/v1")
public class ProductCheckoutController {

    private StripeService stripeService;

    public ProductCheckoutController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts(
            @RequestBody ProductRequest productRequest,
            HttpSession session
    ) {

        String email = (String) session.getAttribute("userEmail");

        productRequest.setEmail(email);

        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);

        return ResponseEntity.ok(stripeResponse);
    }
}

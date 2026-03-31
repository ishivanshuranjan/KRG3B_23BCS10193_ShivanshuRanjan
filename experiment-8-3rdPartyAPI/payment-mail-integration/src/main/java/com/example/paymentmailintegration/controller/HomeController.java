package com.example.paymentmailintegration.controller;

import com.example.paymentmailintegration.entity.Transaction;
import com.example.paymentmailintegration.repository.TransactionRepository;
import com.example.paymentmailintegration.service.SendEmailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.NumberFormat;
import java.util.Locale;

@Controller
public class HomeController {

    private final SendEmailService sendEmailService;
    private final TransactionRepository transactionRepository;

    public HomeController(SendEmailService sendEmailService,
                          TransactionRepository transactionRepository) {
        this.sendEmailService = sendEmailService;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/success")
    public String success(
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam Long amount,
            @RequestParam Long quantity
    ) {

        Transaction txn = new Transaction();
        txn.setEmail(email);
        txn.setProductName(name);
        txn.setAmount(amount);
        txn.setQuantity(quantity);

        transactionRepository.save(txn);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        String formattedAmount = formatter.format(amount / 100.0);

        String body = "Payment Successful!\n\n"
                + "Product: " + name + "\n"
                + "Amount: " + formattedAmount + "\n"
                + "Quantity: " + quantity;

        sendEmailService.sendEmail(email, body, "Payment Successful");

        return "success";
    }
}

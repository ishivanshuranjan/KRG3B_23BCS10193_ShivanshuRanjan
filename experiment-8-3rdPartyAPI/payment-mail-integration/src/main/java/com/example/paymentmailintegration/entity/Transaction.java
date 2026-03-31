package com.example.paymentmailintegration.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private Long amount;
    private Long quantity;
    private String email;
}
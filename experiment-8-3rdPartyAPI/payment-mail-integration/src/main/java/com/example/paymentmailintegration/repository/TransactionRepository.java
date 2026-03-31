package com.example.paymentmailintegration.repository;

import com.example.paymentmailintegration.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
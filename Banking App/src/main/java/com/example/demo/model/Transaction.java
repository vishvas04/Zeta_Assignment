package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    private String transactionId;
    private String accountId;
    private String type; // "debit" or "credit"
    private double amount;
    private LocalDateTime timestamp;
    // Getters and setters
}
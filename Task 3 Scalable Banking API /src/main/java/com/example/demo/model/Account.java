package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.DialectOverride;

@Entity
public class Account {
    @Id
    private String accountId;
    private double balance;
    @DialectOverride.Version(major = 0)
    private int version;

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }
}
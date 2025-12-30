package com.university.finance.model;

import java.util.Date;

public class Transaction {
    private String transactionId;
    private String accountId;
    private String transactionType;
    private double amount;
    private Date transactionDate;
    private String description;
    
    public Transaction(String transactionId, String accountId, String transactionType, double amount, String description) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = new Date();
        this.description = description;
    }
    
    // Getters
    public String getTransactionId() {
        return transactionId;
    }
    
    public String getAccountId() {
        return accountId;
    }
    
    public String getTransactionType() {
        return transactionType;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public Date getTransactionDate() {
        return transactionDate;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", description='" + description + '\'' +
                '}';
    }
}
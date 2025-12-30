package com.university.finance.model;

public class ConcreteAccount implements Account {
    private String accountId;
    private double balance;
    
    public ConcreteAccount(String accountId, double initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
    }
    
    @Override
    public void deposit(double amount) {
        this.balance += amount;
    }
    
    @Override
    public void withdraw(double amount) {
        this.balance -= amount;
    }
    
    @Override
    public void transfer(Account recipient, double amount) {
        this.withdraw(amount);
        recipient.deposit(amount);
    }
    
    @Override
    public double getBalance() {
        return this.balance;
    }
    
    @Override
    public String getAccountId() {
        return this.accountId;
    }
}
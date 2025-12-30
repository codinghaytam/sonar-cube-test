package com.university.finance.model;

public interface Account {
    // Abstraction des comportements d'un compte
    public void deposit(double amount);
    public void withdraw(double amount);
    public void transfer(Account recipient, double amount);
    public double getBalance();
    public String getAccountId();
}
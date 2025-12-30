package com.university.finance.model;

public class ConcreteUser implements User {
    private String username;
    private String password;
    
    public ConcreteUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    @Override
    public String getUsername() {
        return this.username;
    }
    
    @Override
    public String getPassword() {
        return this.password;
    }
}
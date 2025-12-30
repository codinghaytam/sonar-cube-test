package com.university.finance.config;

public class AppConfig {
    private AppConfig() {}
    
    private static class Holder {
        private static final AppConfig INSTANCE = new AppConfig();
    }
    
    public static AppConfig getInstance() {
        return Holder.INSTANCE;
    }

    // Devops : Ajoute configuration pour envoie des notification par email
}


package com.isc.transfer_scheduler.seed;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.seed")
public class SeedConfig {
    private int numberOfUsers = 5;
    private int accountsPerUser = 1;
    private int transfersPerAccount = 4;

    // Getters and setters
    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    public int getAccountsPerUser() {
        return accountsPerUser;
    }

    public void setAccountsPerUser(int accountsPerUser) {
        this.accountsPerUser = accountsPerUser;
    }

    public int getTransfersPerAccount() {
        return transfersPerAccount;
    }

    public void setTransfersPerAccount(int transfersPerAccount) {
        this.transfersPerAccount = transfersPerAccount;
    }
}
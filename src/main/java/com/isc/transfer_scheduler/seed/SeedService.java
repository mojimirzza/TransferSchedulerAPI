package com.isc.transfer_scheduler.seed;

import com.isc.transfer_scheduler.auth.dto.RegisterRequest;
import com.isc.transfer_scheduler.auth.service.AuthService;
import com.isc.transfer_scheduler.dto.AccountDto;
import com.isc.transfer_scheduler.dto.TransferDto;
import com.isc.transfer_scheduler.model.Account;
import com.isc.transfer_scheduler.model.Transfer;
import com.isc.transfer_scheduler.service.AccountService;
import com.isc.transfer_scheduler.service.TransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SeedService {
    private static final Logger logger = LoggerFactory.getLogger(SeedService.class);

    private final AuthService authService;
    private final AccountService accountService;
    private final TransferService transferService;
    private final SeedConfig seedConfig;

    public SeedService(AuthService authService, AccountService accountService, TransferService transferService, SeedConfig seedConfig) {
        this.authService = authService;
        this.accountService = accountService;
        this.transferService = transferService;
        this.seedConfig = seedConfig;
    }

    public void seedData() {
        try {
            logger.info("Starting seeding process...");

            List<String> usernames = new ArrayList<>();
            List<Account> accounts = new ArrayList<>();

            // Create users
            for (int i = 1; i <= seedConfig.getNumberOfUsers(); i++) {
                String username = "user" + i;
                String password = "password" + i;

                RegisterRequest registerRequest = new RegisterRequest();
                registerRequest.setUsername(username);
                registerRequest.setPassword(password);

                authService.register(registerRequest); // Use AuthService to register users
                usernames.add(username);
                logger.info("Created user: {}", username);
            }


                    // Create accounts
                    for (String username : usernames) {
                        for (int j = 1; j <= seedConfig.getAccountsPerUser(); j++) {
                            AccountDto accountDto = new AccountDto();
                            accountDto.setAccountNumber("ACC" + username + j);
                            accountDto.setBalance(BigDecimal.valueOf(new double[]{10000,20000,30000,40000,50000}[ThreadLocalRandom.current().nextInt(5)]));
                            Account account = accountService.createAccount(accountDto, username);
                            accounts.add(account);
                            logger.info("Created account: {} -> {}", account.getAccountNumber(), account.getBalance());
                        }
                    }

                    // Log initial account balances
                    logger.info("Initial account balances:");
                    for (Account account : accounts) {
                        logger.info("Account {}: {}", account.getAccountNumber(), account.getBalance());
                    }

            for (int i = 0; i < seedConfig.getTransfersPerAccount(); i++) {
                for (Account sourceAccount : accounts) {
                    for (Account destinationAccount : accounts) {
                        if (!sourceAccount.getId().equals(destinationAccount.getId())) {
                            TransferDto transferDto = new TransferDto();
                            transferDto.setSourceAccountId(sourceAccount.getId());
                            transferDto.setDestinationAccountId(destinationAccount.getId());
                            transferDto.setAmount(BigDecimal.valueOf(new double[]{5000, 10000, 15000, 20000}[ThreadLocalRandom.current().nextInt(4)]));

                            // Set createdTime to 5 minutes before now
                            transferDto.setCreatedTime(LocalDateTime.now().minusMinutes(5));

                            // Set scheduledTime to 2 minutes before now
                            transferDto.setScheduledTime(LocalDateTime.now().minusMinutes(2));

                            // Log transfer details
                            logger.info("Creating transfer: {} -> {} (Amount: {})",
                                    sourceAccount.getAccountNumber(), destinationAccount.getAccountNumber(), transferDto.getAmount());

                            // Create the transfer
                            Transfer transfer = transferService.createTransfer(transferDto, sourceAccount.getUsername());

                            logger.info("Created transfer: {} -> {}", sourceAccount.getAccountNumber(), destinationAccount.getAccountNumber());
                        }
                    }
                }
            }




            logger.info("Seeding process completed successfully.");
        } catch (Exception e) {
            logger.error("Error during seeding process: {}", e.getMessage(), e);
        }
    }


}
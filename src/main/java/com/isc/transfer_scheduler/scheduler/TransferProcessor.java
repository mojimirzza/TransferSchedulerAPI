package com.isc.transfer_scheduler.scheduler;

import com.isc.transfer_scheduler.model.Account;
import com.isc.transfer_scheduler.model.Transfer;
import com.isc.transfer_scheduler.repository.AccountRepository;
import com.isc.transfer_scheduler.repository.TransferRepository;
import com.isc.transfer_scheduler.service.AccountService;
import com.isc.transfer_scheduler.service.TransferStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.retry.annotation.Backoff;
//import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransferProcessor {
    private static final Logger logger = LoggerFactory.getLogger(TransferProcessor.class);
    private final AccountService accountService;
    private final TransferStatusService transferStatusService;
    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;

    public TransferProcessor(AccountService accountService, TransferStatusService transferStatusService,
                             AccountRepository accountRepository, TransferRepository transferRepository) {
        this.accountService = accountService;
        this.transferStatusService = transferStatusService;
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
    }

    @Transactional
    public void processTransfer(Transfer transfer) {
        try {
            // Simulate random failure
            if (!simulateRandomFailure()) {
                throw new RuntimeException("Simulated random failure during transfer processing.");
            }

            // Log initial balances
            logger.info("Initial balances:");
            logger.info("Source account {}: {}", transfer.getSourceAccount().getId(), transfer.getSourceAccount().getBalance());
            logger.info("Destination account {}: {}", transfer.getDestinationAccount().getId(), transfer.getDestinationAccount().getBalance());

            // 1. Validate and get updated balances
            BigDecimal sourceAccountNewBalance = accountService.validateAndGetBalanceForDebit(transfer.getSourceAccount().getId(), transfer.getAmount());
            BigDecimal destinationAccountNewBalance = accountService.validateAndGetBalanceForCredit(transfer.getDestinationAccount().getId(), transfer.getAmount());

            // 2. Update account balances in the database
            Account sourceAccount = transfer.getSourceAccount();
            sourceAccount.setBalance(sourceAccountNewBalance);
            accountRepository.save(sourceAccount);

            Account destinationAccount = transfer.getDestinationAccount();
            destinationAccount.setBalance(destinationAccountNewBalance);
            accountRepository.save(destinationAccount);

            // Log updated balances
            logger.info("Updated balances:");
            logger.info("Source account {}: {}", sourceAccount.getId(), sourceAccount.getBalance());
            logger.info("Destination account {}: {}", destinationAccount.getId(), destinationAccount.getBalance());

            // 3. Update transfer status
            transferStatusService.markTransferAsCompleted(transfer);
            transferRepository.save(transfer);

            logger.info("Transfer {} processed successfully.", transfer.getId());
        } catch (Exception e) {
            logger.error("Failed to process transfer {}: {}", transfer.getId(), e.getMessage());
            transferStatusService.markTransferAsFailed(transfer, e.getMessage());
            transferRepository.save(transfer);
            throw e; // Re-throw to trigger retry
        }
    }

    private boolean simulateRandomFailure() {
        // Simulate a 30% chance of failure
        return Math.random() > 0.3;
    }
}


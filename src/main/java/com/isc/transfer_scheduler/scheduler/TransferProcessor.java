package com.isc.transfer_scheduler.scheduler;

import com.isc.transfer_scheduler.model.Transfer;
import com.isc.transfer_scheduler.service.AccountService;
import com.isc.transfer_scheduler.service.TransferStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.retry.annotation.Backoff;
//import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferProcessor {
    private static final Logger logger = LoggerFactory.getLogger(TransferProcessor.class);
    private final AccountService accountService;
    private final TransferStatusService transferStatusService;

    public TransferProcessor(AccountService accountService, TransferStatusService transferStatusService) {
        this.accountService = accountService;
        this.transferStatusService = transferStatusService;
    }

//    @Retryable(
//            maxAttempts = 3,
//            backoff = @Backoff(delay = 1000, multiplier = 2)
//    )
    @Transactional
    public void processTransfer(Transfer transfer) {
        try {
            // 1. Deduct from source account
            accountService.debitAccount(transfer.getSourceAccount().getId(), transfer.getAmount());

            // 2. Credit destination account
            accountService.creditAccount(transfer.getDestinationAccount().getId(), transfer.getAmount());

            // 3. Update transfer status to COMPLETED
            transferStatusService.markTransferAsCompleted(transfer);

            logger.info("Transfer {} processed successfully.", transfer.getId());
        } catch (Exception e) {
            logger.error("Failed to process transfer {}: {}", transfer.getId(), e.getMessage());
            transferStatusService.markTransferAsFailed(transfer, e.getMessage());
            throw e; // Re-throw to trigger retry
        }
    }
}
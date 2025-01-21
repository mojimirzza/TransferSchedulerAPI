package com.isc.transfer_scheduler.scheduler;

import com.isc.transfer_scheduler.model.Transfer;
import com.isc.transfer_scheduler.model.TransferStatus;
import com.isc.transfer_scheduler.repository.TransferRepository;
import com.isc.transfer_scheduler.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransferSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(TransferSchedulerService.class);

    private final TransferRepository transferRepository;
    private final AccountService accountService;

    public TransferSchedulerService(TransferRepository transferRepository, AccountService accountService) {
        this.transferRepository = transferRepository;
        this.accountService = accountService;
    }

    @Transactional
    public void processScheduledTransfers() {
        logger.info("Processing scheduled transfers...");

        // Fetch transfers that are PENDING and have a scheduledTime in the past
        List<Transfer> pendingTransfers = transferRepository
                .findByStatusAndScheduledTimeBefore(TransferStatus.PENDING, LocalDateTime.now());

        for (Transfer transfer : pendingTransfers) {
            try {
                // Debit from source account
                BigDecimal newSourceBalance = accountService.validateAndGetBalanceForDebit(
                        transfer.getSourceAccount().getId(), transfer.getAmount());

                // Credit to destination account
                BigDecimal newDestinationBalance = accountService.validateAndGetBalanceForCredit(
                        transfer.getDestinationAccount().getId(), transfer.getAmount());

                // Update account balances
                transfer.getSourceAccount().setBalance(newSourceBalance);
                transfer.getDestinationAccount().setBalance(newDestinationBalance);

                // Update transfer status and execution time
                transfer.setStatus(TransferStatus.COMPLETED);
                transfer.setExecutionTime(LocalDateTime.now());

                // Save the updated transfer
                transferRepository.save(transfer);

                logger.info("Transfer {} processed successfully.", transfer.getId());
            } catch (Exception e) {
                // Handle exceptions (e.g., insufficient balance, account not found)
                transfer.setStatus(TransferStatus.FAILED);
                transfer.setErrorMessage(e.getMessage());
                transferRepository.save(transfer);

                logger.error("Failed to process transfer {}: {}", transfer.getId(), e.getMessage());
            }
        }

        logger.info("Finished processing scheduled transfers.");
    }
}
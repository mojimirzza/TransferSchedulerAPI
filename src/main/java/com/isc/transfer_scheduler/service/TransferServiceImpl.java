package com.isc.transfer_scheduler.service;

import com.isc.transfer_scheduler.dto.TransferDto;
import com.isc.transfer_scheduler.exception.AccountNotFoundException;
import com.isc.transfer_scheduler.exception.InsufficientBalanceException;
import com.isc.transfer_scheduler.model.Account;
import com.isc.transfer_scheduler.model.Transfer;
import com.isc.transfer_scheduler.model.TransferStatus;
import com.isc.transfer_scheduler.repository.TransferRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final AccountService accountService;

    public TransferServiceImpl(TransferRepository transferRepository, AccountService accountService) {
        this.transferRepository = transferRepository;
        this.accountService = accountService;
    }

    @Override
    public Transfer createTransfer(TransferDto transferDto, String username) {
        // Fetch accounts with username validation
        Account sourceAccount = accountService.getAccountById(transferDto.getSourceAccountId(), username)
                .orElseThrow(() -> new AccountNotFoundException("Source account not found or does not belong to the user."));
        Account destinationAccount = accountService.getAccountById(transferDto.getDestinationAccountId(), username)
                .orElseThrow(() -> new AccountNotFoundException("Destination account not found or does not belong to the user."));

        // Validate source account balance
        if (sourceAccount.getBalance().compareTo(transferDto.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance in the source account.");
        }

        // Perform the transfer logic
        Transfer transfer = new Transfer();
        transfer.setSourceAccount(sourceAccount);
        transfer.setDestinationAccount(destinationAccount);
        transfer.setAmount(transferDto.getAmount());
        transfer.setScheduledTime(transferDto.getScheduledTime());
        transfer.setStatus(TransferStatus.PENDING); // Set initial status
        transfer.setCreatedTime(LocalDateTime.now()); // Set creation time

        // Save the transfer to the database
        return transferRepository.save(transfer);
    }
}
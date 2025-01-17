package com.isc.transfer_scheduler.service;


import com.isc.transfer_scheduler.dto.TransferDto;
import com.isc.transfer_scheduler.model.Account;
import com.isc.transfer_scheduler.model.Transfer;
import com.isc.transfer_scheduler.repository.TransferRepository;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final AccountService accountService;

    public TransferServiceImpl(TransferRepository transferRepository, AccountService accountService) {
        this.transferRepository = transferRepository;
        this.accountService = accountService;
    }

    @Override
    public Transfer createTransfer(TransferDto transferDto) {
        // Map TransferDto to Transfer entity
        Transfer transfer = new Transfer();
        Account sourceAccount = accountService.getAccountById(transferDto.getSourceAccountId()).orElse(null);
        Account destinationAccount = accountService.getAccountById(transferDto.getDestinationAccountId()).orElse(null);
        transfer.setSourceAccount(sourceAccount);
        transfer.setDestinationAccount(destinationAccount);
        transfer.setAmount(transferDto.getAmount());
        transfer.setScheduledTime(transferDto.getScheduledTime());
    
        // Save the transfer to the database using TransferRepository
        return transferRepository.save(transfer);
    }
}
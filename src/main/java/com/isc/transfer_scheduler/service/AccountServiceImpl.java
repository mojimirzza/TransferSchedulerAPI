package com.isc.transfer_scheduler.service;

import com.isc.transfer_scheduler.dto.AccountDto;
import com.isc.transfer_scheduler.model.Account;
import com.isc.transfer_scheduler.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private static final Logger logger = Logger.getLogger(AccountServiceImpl.class.getName());

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Account createAccount(AccountDto accountDto) {
        Account account = new Account();
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setBalance(accountDto.getBalance());
        logger.info("Account created successfully: " + account);
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    // Implement other methods from the interface here if needed
    /*
    @Override
    public Account updateAccount(Long id, AccountDto accountDto) {
        return accountRepository.findById(id)
                .map(account -> {
                    account.setAccountNumber(accountDto.getAccountNumber());
                    account.setBalance(accountDto.getBalance());
                    return accountRepository.save(account);
                })
                .orElseThrow(() -> new RuntimeException("Account not found")); // Or a custom exception
    }
    */
}
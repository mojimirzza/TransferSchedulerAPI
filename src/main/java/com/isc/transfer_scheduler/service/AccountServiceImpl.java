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
    public Account createAccount(AccountDto accountDto, String username) {
        Account account = new Account();
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setBalance(accountDto.getBalance());
        account.setUsername(username); // Associate the account with the logged-in user
        logger.info("Account created successfully: " + account);
        return accountRepository.save(account);
    }
//
//    @Override
//    public Optional<Account> getAccountById(Long id, String username) {
//        Optional<Account> account = accountRepository.findById(id);
//        if (account.isPresent() && account.get().getUsername().equals(username)) {
//            return account; // Return the account only if it belongs to the logged-in user
//        } else {
//            return Optional.empty(); // Return empty if the account does not belong to the user
//        }
//    }

    @Override
    public Optional<Account> getAccountByIdForUser(Long id, String username) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent() && account.get().getUsername().equals(username)) {
            return account; // Return the account only if it belongs to the logged-in user
        } else {
            return Optional.empty(); // Return empty if the account does not belong to the user
        }
    }

    @Override
    public Optional<Account> getAccountByIdWithoutValidation(Long id) {
        return accountRepository.findById(id); // Fetch account without username validation
    }
}
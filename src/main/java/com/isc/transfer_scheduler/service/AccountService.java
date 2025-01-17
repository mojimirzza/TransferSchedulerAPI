package com.isc.transfer_scheduler.service;

import com.isc.transfer_scheduler.dto.AccountDto;
import com.isc.transfer_scheduler.model.Account;

import java.util.Optional;

public interface AccountService {

    Account createAccount(AccountDto accountDto);

    Optional<Account> getAccountById(Long id);

    // Other potential methods:
    // Account updateAccount(Long id, AccountDto accountDto);
    // void deleteAccount(Long id);
    // List<Account> getAllAccounts();
}
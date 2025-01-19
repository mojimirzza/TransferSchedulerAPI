package com.isc.transfer_scheduler.service;

import com.isc.transfer_scheduler.dto.AccountDto;
import com.isc.transfer_scheduler.model.Account;

import java.util.Optional;

public interface AccountService {

    Account createAccount(AccountDto accountDto, String username); // Add username parameter

    Optional<Account> getAccountById(Long id, String username); // Add username parameter
}
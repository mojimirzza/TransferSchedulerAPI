package com.isc.transfer_scheduler.service;

import com.isc.transfer_scheduler.dto.AccountDto;
import com.isc.transfer_scheduler.model.Account;

import java.math.BigDecimal;
import java.util.Optional;



public interface AccountService {
    Optional<Account> getAccountByIdForUser(Long id, String username); // For source account (with validation)
    Optional<Account> getAccountByIdWithoutValidation(Long id); // For destination account (no validation)
    Account createAccount(AccountDto accountDto, String username);

    BigDecimal validateAndGetBalanceForDebit(Long accountId, BigDecimal amount);

    BigDecimal validateAndGetBalanceForCredit(Long accountId, BigDecimal amount);
}
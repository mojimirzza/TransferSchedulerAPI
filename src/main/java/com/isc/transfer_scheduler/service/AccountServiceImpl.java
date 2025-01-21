package com.isc.transfer_scheduler.service;

import com.isc.transfer_scheduler.dto.AccountDto;
import com.isc.transfer_scheduler.exception.AccountNotFoundException;
import com.isc.transfer_scheduler.exception.InsufficientBalanceException;
import com.isc.transfer_scheduler.model.Account;
import com.isc.transfer_scheduler.repository.AccountRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AccountServiceImpl implements AccountService {


    private final AccountRepository accountRepository;
    private final EntityManager entityManager; // Inject EntityManager

    private static final Logger logger = Logger.getLogger(AccountServiceImpl.class.getName());

    public AccountServiceImpl(AccountRepository accountRepository, EntityManager entityManager) {
        this.accountRepository = accountRepository;
        this.entityManager = entityManager;
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



    @Override
    public BigDecimal validateAndGetBalanceForDebit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        return account.getBalance().subtract(amount); // Return updated balance
    }

    @Override
    public BigDecimal validateAndGetBalanceForCredit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return account.getBalance().add(amount); // Return updated balance
    }
}

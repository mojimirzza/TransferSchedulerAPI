

package com.isc.transfer_scheduler.controller;

import com.isc.transfer_scheduler.dto.AccountDto;
import com.isc.transfer_scheduler.model.Account;
import com.isc.transfer_scheduler.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody AccountDto accountDto) {
        Account account =  accountService.createAccount(accountDto);
        return new ResponseEntity<>(account, HttpStatus.CREATED); // Use HttpStatus.CREATED

    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Optional<Account> account = accountService.getAccountById(id);
        if (account.isPresent()) {
            return ResponseEntity.ok(account.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
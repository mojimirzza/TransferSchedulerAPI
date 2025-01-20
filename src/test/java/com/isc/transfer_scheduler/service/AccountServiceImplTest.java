package com.isc.transfer_scheduler.service;

import com.isc.transfer_scheduler.dto.AccountDto;
import com.isc.transfer_scheduler.model.Account;
import com.isc.transfer_scheduler.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount() {
        // Arrange
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber("1234567890");
        accountDto.setBalance(BigDecimal.valueOf(1000.00));

        Account savedAccount = new Account();
        savedAccount.setId(1L);
        savedAccount.setAccountNumber("1234567890");
        savedAccount.setBalance(BigDecimal.valueOf(1000.00));
        savedAccount.setUsername("testuser");

        when(accountRepository.save(any(Account.class))).thenReturn(savedAccount);

        // Act
        Account result = accountService.createAccount(accountDto, "testuser");

        // Assert
        assertNotNull(result);
        assertEquals("1234567890", result.getAccountNumber());
        assertEquals(BigDecimal.valueOf(1000.00), result.getBalance());
        assertEquals("testuser", result.getUsername());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void testGetAccountById_Success() {
        // Arrange
        Long accountId = 1L;
        String username = "testuser";

        Account account = new Account();
        account.setId(accountId);
        account.setAccountNumber("1234567890");
        account.setBalance(BigDecimal.valueOf(1000.00));
        account.setUsername(username);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Act
        Optional<Account> result = accountService.getAccountById(accountId, username);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(accountId, result.get().getId());
        assertEquals(username, result.get().getUsername());
        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void testGetAccountById_NotFound() {
        // Arrange
        Long accountId = 1L;
        String username = "testuser";

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act
        Optional<Account> result = accountService.getAccountById(accountId, username);

        // Assert
        assertFalse(result.isPresent());
        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void testGetAccountById_Unauthorized() {
        // Arrange
        Long accountId = 1L;
        String username = "testuser";

        Account account = new Account();
        account.setId(accountId);
        account.setAccountNumber("1234567890");
        account.setBalance(BigDecimal.valueOf(1000.00));
        account.setUsername("anotheruser"); // Different username

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Act
        Optional<Account> result = accountService.getAccountById(accountId, username);

        // Assert
        assertFalse(result.isPresent());
        verify(accountRepository, times(1)).findById(accountId);
    }
}
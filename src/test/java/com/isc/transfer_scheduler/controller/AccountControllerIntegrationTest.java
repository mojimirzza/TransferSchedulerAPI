package com.isc.transfer_scheduler.controller;

import com.isc.transfer_scheduler.auth.config.SecurityConfig;
import com.isc.transfer_scheduler.auth.security.CustomUserDetailsService;
import com.isc.transfer_scheduler.auth.security.JwtAuthEntryPoint;
import com.isc.transfer_scheduler.dto.AccountDto;
import com.isc.transfer_scheduler.model.Account;
import com.isc.transfer_scheduler.service.AccountService;
import com.isc.transfer_scheduler.utils.SecurityTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@Import(SecurityConfig.class) // Import SecurityConfig to load security configurations
class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // Mock the AccountService interface
    private AccountService accountService;

    @MockBean // Mock JwtAuthEntryPoint
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @MockBean // Mock CustomUserDetailsService
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        // Simulate a JWT-authenticated user before each test
        SecurityTestUtils.mockJwtAuthentication("testuser", "USER");
    }

    // Test Case 1: Successful Account Creation
    @Test
    void testCreateAccount_Success() throws Exception {
        // Arrange
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber("1234567890");
        accountDto.setBalance(BigDecimal.valueOf(1000.00));

        Account account = new Account();
        account.setId(1L);
        account.setAccountNumber("1234567890");
        account.setBalance(BigDecimal.valueOf(1000.00));
        account.setUsername("testuser");

        // Mock the behavior of the AccountService interface
        when(accountService.createAccount(any(AccountDto.class), eq("testuser"))).thenReturn(account);

        // Act & Assert
        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\": \"1234567890\", \"balance\": 1000.00}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountNumber").value("1234567890"))
                .andExpect(jsonPath("$.balance").value(1000.00));
    }

    // Test Case 2: Get Account by ID - Success
    @Test
    void testGetAccountById_Success() throws Exception {
        // Arrange
        Long accountId = 1L;
        String username = "testuser";

        Account account = new Account();
        account.setId(accountId);
        account.setAccountNumber("1234567890");
        account.setBalance(BigDecimal.valueOf(1000.00));
        account.setUsername(username);

        // Mock the behavior of the AccountService interface
        when(accountService.getAccountById(accountId, username)).thenReturn(Optional.of(account));

        // Act & Assert
        mockMvc.perform(get("/api/v1/accounts/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountId))
                .andExpect(jsonPath("$.accountNumber").value("1234567890"))
                .andExpect(jsonPath("$.balance").value(1000.00));
    }

    // Test Case 3: Get Account by ID - Not Found
    @Test
    void testGetAccountById_NotFound() throws Exception {
        // Arrange
        Long accountId = 1L;
        String username = "testuser";

        // Mock the behavior of the AccountService interface
        when(accountService.getAccountById(accountId, username)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/v1/accounts/{id}", accountId))
                .andExpect(status().isNotFound());
    }

    // Test Case 4: Get Account by ID - Unauthorized (Account does not belong to the user)
    @Test
    void testGetAccountById_Unauthorized() throws Exception {
        // Arrange
        Long accountId = 1L;
        String username = "testuser";

        Account account = new Account();
        account.setId(accountId);
        account.setAccountNumber("1234567890");
        account.setBalance(BigDecimal.valueOf(1000.00));
        account.setUsername("anotheruser"); // Different username

        // Mock the behavior of the AccountService interface
        when(accountService.getAccountById(accountId, username)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/v1/accounts/{id}", accountId))
                .andExpect(status().isNotFound());
    }
}
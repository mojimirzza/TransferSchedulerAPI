package com.isc.transfer_scheduler.controller;

import com.isc.transfer_scheduler.auth.config.SecurityConfig;
import com.isc.transfer_scheduler.auth.security.CustomUserDetailsService;
import com.isc.transfer_scheduler.auth.security.JwtAuthEntryPoint;
import com.isc.transfer_scheduler.dto.TransferDto;
import com.isc.transfer_scheduler.model.Account;
import com.isc.transfer_scheduler.model.Transfer;
import com.isc.transfer_scheduler.model.TransferStatus;
import com.isc.transfer_scheduler.service.TransferService;
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
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransferController.class)
@Import(SecurityConfig.class) // Import SecurityConfig to load security configurations
class TransferControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // Mock the TransferService interface
    private TransferService transferService;

    @MockBean // Mock JwtAuthEntryPoint
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @MockBean // Mock CustomUserDetailsService
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        // Simulate a JWT-authenticated user before each test
        SecurityTestUtils.mockJwtAuthentication("testuser", "USER");
    }

    // Test Case: Successful Transfer Creation
    @Test
    void testCreateTransfer_Success() throws Exception {
        // Arrange
        TransferDto transferDto = new TransferDto();
        transferDto.setSourceAccountId(1L);
        transferDto.setDestinationAccountId(2L);
        transferDto.setAmount(BigDecimal.valueOf(100.00));
        transferDto.setScheduledTime(LocalDateTime.now());

        Account sourceAccount = new Account();
        sourceAccount.setId(1L);
        sourceAccount.setAccountNumber("1234567890");
        sourceAccount.setBalance(BigDecimal.valueOf(1000.00));
        sourceAccount.setUsername("testuser");

        Account destinationAccount = new Account();
        destinationAccount.setId(2L);
        destinationAccount.setAccountNumber("0987654321");
        destinationAccount.setBalance(BigDecimal.valueOf(500.00));
        destinationAccount.setUsername("testuser");

        Transfer transfer = new Transfer();
        transfer.setId(1L);
        transfer.setSourceAccount(sourceAccount);
        transfer.setDestinationAccount(destinationAccount);
        transfer.setAmount(BigDecimal.valueOf(100.00));
        transfer.setScheduledTime(LocalDateTime.now());
        transfer.setStatus(TransferStatus.PENDING);
        transfer.setCreatedTime(LocalDateTime.now());

        // Mock the behavior of the TransferService interface
        when(transferService.createTransfer(any(TransferDto.class), eq("testuser"))).thenReturn(transfer);

        // Act & Assert
        mockMvc.perform(post("/api/v1/transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sourceAccountId\": 1, \"destinationAccountId\": 2, \"amount\": 100.00, \"scheduledTime\": \"2023-10-01T12:00:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.sourceAccount.id").value(1L))
                .andExpect(jsonPath("$.destinationAccount.id").value(2L))
                .andExpect(jsonPath("$.amount").value(100.00))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }
}
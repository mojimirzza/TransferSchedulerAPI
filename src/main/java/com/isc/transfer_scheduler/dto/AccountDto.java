package com.isc.transfer_scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    @NotBlank(message = "Account number is required") // Validation
    private String accountNumber;

    @NotNull(message = "Balance is required") // Validation
    @PositiveOrZero(message = "Balance must be positive or zero") // Validation
    private BigDecimal balance;
}
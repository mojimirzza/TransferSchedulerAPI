package com.isc.transfer_scheduler.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(
            description = "The account number associated with the account",
            example = "1234567890",
            required = true
    )
    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @Schema(
            description = "The current balance of the account",
            example = "1000.00",
            required = true
    )
    @NotNull(message = "Balance is required")
    @PositiveOrZero(message = "Balance must be positive or zero")
    private BigDecimal balance;
}
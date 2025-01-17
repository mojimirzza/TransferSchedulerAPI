package com.isc.transfer_scheduler.dto;

import jakarta.validation.constraints.NotNull; // Use @NotNull instead of @NotBlank for Long
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {
    @NotNull(message = "Source account ID is required")
    @Positive(message = "Source account ID must be positive")
    private Long sourceAccountId;

    @NotNull(message = "Destination account ID is required")
    @Positive(message = "Destination account ID must be positive")
    private Long destinationAccountId;

    @NotNull(message = "Transfer amount is required")
    @PositiveOrZero(message = "Transfer amount must be positive or zero")
    private BigDecimal amount;

    @NotNull(message = "Scheduled time is required")
    private LocalDateTime scheduledTime;
}
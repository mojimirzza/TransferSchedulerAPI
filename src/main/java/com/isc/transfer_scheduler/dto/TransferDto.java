package com.isc.transfer_scheduler.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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

    @Schema(
            description = "The ID of the source account for the transfer",
            example = "1",
            required = true
    )
    @NotNull(message = "Source account ID is required")
    @Positive(message = "Source account ID must be positive")
    private Long sourceAccountId;

    @Schema(
            description = "The ID of the destination account for the transfer",
            example = "2",
            required = true
    )
    @NotNull(message = "Destination account ID is required")
    @Positive(message = "Destination account ID must be positive")
    private Long destinationAccountId;

    @Schema(
            description = "The amount to transfer",
            example = "100.00",
            required = true
    )
    @NotNull(message = "Transfer amount is required")
    @PositiveOrZero(message = "Transfer amount must be positive or zero")
    private BigDecimal amount;

    @Schema(
            description = "The scheduled time for the transfer",
            example = "2023-12-31T23:59:59",
            required = true
    )
    @NotNull(message = "Scheduled time is required")
    private LocalDateTime scheduledTime;

    @Schema(
            description = "The time when the transfer was created",
            example = "2023-12-31T23:59:59"
    )
    private LocalDateTime createdTime; // Add this field
}
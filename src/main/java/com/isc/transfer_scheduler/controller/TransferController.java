package com.isc.transfer_scheduler.controller;

import com.isc.transfer_scheduler.event.TransferCreatedEvent;
import com.isc.transfer_scheduler.exception.AccountNotFoundException;
import com.isc.transfer_scheduler.exception.InsufficientBalanceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.isc.transfer_scheduler.dto.TransferDto;
import com.isc.transfer_scheduler.model.Transfer;
import com.isc.transfer_scheduler.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transfers")
@Tag(name = "Transfer Management", description = "Endpoints for managing transfers") // Group all endpoints under this tag
@SecurityRequirement(name = "bearerAuth") // Indicates that all endpoints in this controller require JWT authentication
public class TransferController {
    private static final Logger logger = LoggerFactory.getLogger(TransferController.class);
    private final TransferService transferService;
    private final ApplicationEventPublisher eventPublisher;

    public TransferController(TransferService transferService, ApplicationEventPublisher eventPublisher) {
        this.transferService = transferService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    @Operation(
            summary = "Create a new transfer",
            description = "Allows a user to create a new transfer. Requires authentication."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transfer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "403", description = "Forbidden (insufficient permissions)"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> createTransfer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Transfer details to create", required = true)
            @RequestBody TransferDto transferDto,
            @Parameter(hidden = true) // Hide this parameter from Swagger UI (it's automatically injected by Spring Security)
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        logger.info("Received request to create transfer: {}", transferDto);
        logger.info("Authenticated user: {}", userDetails.getUsername());

        try {
            String username = userDetails.getUsername();
            Transfer transfer = transferService.createTransfer(transferDto, username);
            // Publish the event
            eventPublisher.publishEvent(new TransferCreatedEvent(this, transfer));
            logger.info("Transfer created successfully: {}", transfer);
            return ResponseEntity.ok(transfer);
        } catch (AccountNotFoundException ex) {
            logger.error("Account not found: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (InsufficientBalanceException ex) {
            logger.error("Insufficient balance: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("Error creating transfer: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}
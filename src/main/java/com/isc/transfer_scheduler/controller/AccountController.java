
package com.isc.transfer_scheduler.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.isc.transfer_scheduler.dto.AccountDto;
import com.isc.transfer_scheduler.model.Account;
import com.isc.transfer_scheduler.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name = "Account Management", description = "Endpoints for managing user accounts") // Group all endpoints under this tag
@SecurityRequirement(name = "bearerAuth") // Indicates that all endpoints in this controller require JWT authentication
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Create a new account",
            description = "Allows a user to create a new account. Requires ROLE_USER."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "403", description = "Forbidden (insufficient permissions)")
    })
    public ResponseEntity<Account> createAccount(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Account details to create", required = true)
            @RequestBody AccountDto accountDto,
            @Parameter(hidden = true) // Hide this parameter from Swagger UI (it's automatically injected by Spring Security)
            @AuthenticationPrincipal UserDetails userDetails
    ) {



           logger.info("Received request to create account: {}", accountDto);
        logger.info("Authenticated user: {}", userDetails.getUsername());

        try {
        String username = userDetails.getUsername();
        Account account = accountService.createAccount(accountDto, username);
        logger.info("Account created successfully: {}", account);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    } catch (Exception e) {
        logger.error("Error creating account: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Get account by ID",
            description = "Retrieves an account by its ID. Requires ROLE_USER."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "403", description = "Forbidden (insufficient permissions)"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    public ResponseEntity<Account> getAccountById(
            @Parameter(description = "ID of the account to retrieve", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(hidden = true) // Hide this parameter from Swagger UI (it's automatically injected by Spring Security)
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String username = userDetails.getUsername();
        Optional<Account> account = accountService.getAccountByIdForUser(id, username);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
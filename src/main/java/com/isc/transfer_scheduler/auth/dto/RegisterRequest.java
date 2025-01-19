package com.isc.transfer_scheduler.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @Schema(
            description = "The username for the new user",
            example = "moji", // Default username for Swagger UI
            required = true
    )
    @NotBlank(message = "Username is required")
    private String username;

    @Schema(
            description = "The password for the new user",
            example = "pass@123", // Default password for Swagger UI
            required = true
    )
    @NotBlank(message = "Password is required")
    private String password;
}
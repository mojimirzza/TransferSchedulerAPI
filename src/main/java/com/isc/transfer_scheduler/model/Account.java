package com.isc.transfer_scheduler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"\"accountNumber\""}) // Quoted here!
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "\"accountNumber\"") // Quoted here
    private String accountNumber;

    @Column(name = "\"balance\"") // Quoted here
    private BigDecimal balance;

    @Column(name = "\"username\"", nullable = false) // Add username field (quoted)
    private String username; // Associate the account with a user

    @Version
    private Long version;
}
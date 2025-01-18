package com.isc.transfer_scheduler.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "\"sourceAccountId\"", nullable = false) // Quoted identifier
    private Account sourceAccount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "\"destinationAccountId\"", nullable = false) // Quoted identifier
    private Account destinationAccount;

    @Min(value = 0, message = "Amount must be greater than zero")
    @Column(name = "\"amount\"") // Quoted identifier
    private BigDecimal amount;

    @Column(name = "\"scheduledTime\"") // Quoted identifier
    private LocalDateTime scheduledTime;

    @Column(name = "\"executionTime\"") // Quoted identifier
    private LocalDateTime executionTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"status\"") // Quoted identifier
    private TransferStatus status = TransferStatus.PENDING;

    @Column(name = "\"createdTime\"") // Quoted identifier
    private LocalDateTime createdTime;

    @Column(name = "\"updatedTime\"") // Quoted identifier
    private LocalDateTime updatedTime;

    @Version
    @Column(name = "\"version\"") // Quoted identifier
    private Long version;
}
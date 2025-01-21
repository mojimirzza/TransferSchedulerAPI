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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE) // Use CascadeType.MERGE
    @JoinColumn(name = "\"sourceAccountId\"", nullable = false)
    private Account sourceAccount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE) // Use CascadeType.MERGE
    @JoinColumn(name = "\"destinationAccountId\"", nullable = false)
    private Account destinationAccount;

    @Min(value = 0, message = "Amount must be greater than zero")
    @Column(name = "\"amount\"")
    private BigDecimal amount;

    @Column(name = "\"scheduledTime\"")
    private LocalDateTime scheduledTime;

    @Column(name = "\"executionTime\"")
    private LocalDateTime executionTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"status\"")
    private TransferStatus status = TransferStatus.PENDING;

    @Column(name = "\"createdTime\"")
    private LocalDateTime createdTime;

    @Column(name = "\"updatedTime\"")
    private LocalDateTime updatedTime;

    @Version
    @Column(name = "\"version\"")
    private Long version;


    @Column(name = "\"errorMessage\"")
    private String errorMessage;

    @Column(name = "\"retryCount\"")
    private int retryCount = 0;
}
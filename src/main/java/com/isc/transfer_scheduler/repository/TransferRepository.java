package com.isc.transfer_scheduler.repository;

import com.isc.transfer_scheduler.model.Transfer;
import com.isc.transfer_scheduler.model.TransferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    // You can add custom query methods here if needed
    List<Transfer> findByStatusAndScheduledTimeBefore(TransferStatus status, LocalDateTime scheduledTime);
}
package com.isc.transfer_scheduler.repository;

import com.isc.transfer_scheduler.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    // You can add custom query methods here if needed
}
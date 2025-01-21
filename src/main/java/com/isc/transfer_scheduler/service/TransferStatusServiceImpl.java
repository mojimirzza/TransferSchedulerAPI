package com.isc.transfer_scheduler.service;
import com.isc.transfer_scheduler.model.TransferStatus;
import com.isc.transfer_scheduler.model.Transfer;
import com.isc.transfer_scheduler.repository.TransferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransferStatusServiceImpl implements TransferStatusService {
    private final TransferRepository transferRepository;

    public TransferStatusServiceImpl(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Override
    @Transactional
    public void markTransferAsCompleted(Transfer transfer) {
        transfer.setStatus(TransferStatus.SUCCESS);
        transfer.setExecutionTime(LocalDateTime.now());
        transferRepository.save(transfer);
    }

    @Override
    @Transactional
    public void markTransferAsFailed(Transfer transfer, String errorMessage) {
        transfer.setStatus(TransferStatus.FAILED);
        transfer.setErrorMessage(errorMessage);
        transferRepository.save(transfer);
    }
}
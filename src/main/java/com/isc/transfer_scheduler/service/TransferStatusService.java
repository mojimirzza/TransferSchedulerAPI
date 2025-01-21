package com.isc.transfer_scheduler.service;

import com.isc.transfer_scheduler.model.Transfer;

public interface TransferStatusService {
    void markTransferAsCompleted(Transfer transfer);
    void markTransferAsFailed(Transfer transfer, String errorMessage);
}
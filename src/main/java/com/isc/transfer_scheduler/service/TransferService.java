package com.isc.transfer_scheduler.service;

import com.isc.transfer_scheduler.dto.TransferDto;
import com.isc.transfer_scheduler.model.Transfer;

public interface TransferService {
    Transfer createTransfer(TransferDto transferDto, String username); // Add username parameter
    // Other methods like getTransfer, updateTransfer, deleteTransfer 
}
package com.isc.transfer_scheduler.controller;

import com.isc.transfer_scheduler.dto.TransferDto;
import com.isc.transfer_scheduler.model.Transfer;
import com.isc.transfer_scheduler.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers") 
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Transfer> createTransfer(@RequestBody TransferDto transferDto) {
        Transfer createdTransfer = transferService.createTransfer(transferDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransfer); 
    }

    // Add other controller methods (e.g., getTransfer, updateTransfer, deleteTransfer) 
}
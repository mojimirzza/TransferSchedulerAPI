package com.isc.transfer_scheduler.event;

import com.isc.transfer_scheduler.model.Transfer;
import com.isc.transfer_scheduler.scheduler.TransferProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TransferEventListener {
    private static final Logger logger = LoggerFactory.getLogger(TransferEventListener.class);
    private final TransferProcessor transferProcessor;

    public TransferEventListener(TransferProcessor transferProcessor) {
        this.transferProcessor = transferProcessor;
    }

    @Async
    @EventListener
    public void handleTransferCreatedEvent(TransferCreatedEvent event) {
        Transfer transfer = event.getTransfer();
        logger.info("Processing transfer {} asynchronously.", transfer.getId());
        transferProcessor.processTransfer(transfer);
    }
}
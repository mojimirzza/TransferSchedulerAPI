package com.isc.transfer_scheduler.event;

import com.isc.transfer_scheduler.model.Transfer;
import org.springframework.context.ApplicationEvent;

public class TransferCreatedEvent extends ApplicationEvent {
    private final Transfer transfer;

    public TransferCreatedEvent(Object source, Transfer transfer) {
        super(source);
        this.transfer = transfer;
    }

    public Transfer getTransfer() {
        return transfer;
    }
}
package com.isc.transfer_scheduler.job;

import com.isc.transfer_scheduler.scheduler.TransferSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TransferSchedulerJob {

    private static final Logger logger = LoggerFactory.getLogger(TransferSchedulerJob.class);

    private final TransferSchedulerService transferSchedulerService;

    public TransferSchedulerJob(TransferSchedulerService transferSchedulerService) {
        this.transferSchedulerService = transferSchedulerService;
    }

    // Run every 5 minutes (adjust as needed)
    @Scheduled(fixedRate = 10000)
    public void runScheduledTransfers() {
        logger.info("Running scheduled transfers job...");
        transferSchedulerService.processScheduledTransfers();
    }
}
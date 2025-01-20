package com.isc.transfer_scheduler.seed;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("staging") // Run only in the "staging" profile
public class SeedRunner implements CommandLineRunner {
    private final SeedService seedService;

    public SeedRunner(SeedService seedService) {
        this.seedService = seedService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedService.seedData();
    }
}
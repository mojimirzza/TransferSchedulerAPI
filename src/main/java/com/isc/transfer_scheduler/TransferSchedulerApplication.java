package com.isc.transfer_scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
//@EnableOpenApi
public class TransferSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransferSchedulerApplication.class, args);
	}

}

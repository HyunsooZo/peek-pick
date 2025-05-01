package com.peekpick.stock.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class StockScheduler {
    private final JobLauncher jobLauncher;
    private final Job peekPickStockJob;
    private final Logger logger;

    public StockScheduler(
            JobLauncher jobLauncher,
            Job peekPickStockJob
    ) {
        this.jobLauncher = jobLauncher;
        this.peekPickStockJob = peekPickStockJob;
        this.logger = LoggerFactory.getLogger(StockScheduler.class);
    }

//    @PostConstruct
    void init() {
        runStockBatch();
    }

    @Scheduled(cron = "1 * * * * MON-FRI")
    public void runStockBatch() {
        try {
            logger.info("[STOCK SCHEDULER] Running stock batch...");
            var params = new JobParametersBuilder().addLong("timestamp", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(peekPickStockJob, params);
            logger.info("[STOCK SCHEDULER] Successfully ran stock batch");
        } catch (Exception e) {
            logger.error("[STOCK SCHEDULER] Failed to run stock batch", e);
            throw new RuntimeException("[STOCK SCHEDULER] Failed to run stock batch", e);
        }
    }
}

package com.peekpick.stock.configuration;

import com.peekpick.stock.application.FetchStockTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class PeekPickStockBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final FetchStockTasklet tasklet;

    public PeekPickStockBatchConfig(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FetchStockTasklet tasklet
    ) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.tasklet = tasklet;
    }

    @Bean
    public Job peekPickStockJob() {
        return new JobBuilder("peekPickStockJob", jobRepository)
                .start(fetchStockStep())
                .next(analyzeStockStep())
                .next(saveAllStockStep())
                .build();
    }

    @Bean
    public Step fetchStockStep() {
        return new StepBuilder("fetchStockStep", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }

    @Bean
    public Step analyzeStockStep() {
        return new StepBuilder("analyzeStockStep", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }

    @Bean
    public Step saveAllStockStep() {
        return new StepBuilder("saveAllStockStep", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }
}

package com.peekpick.stock.configuration;

import com.peekpick.stock.application.analyzing.StockAnalysisTasklet;
import com.peekpick.stock.application.fetching.StockFetchingTasklet;
import com.peekpick.stock.application.saving.StockSavingTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@EnableTransactionManagement
public class PeekPickStockBatchConfig {

    private final StockFetchingTasklet fetchingTasklet;
    private final StockAnalysisTasklet analysisTasklet;
    private final StockSavingTasklet savingTasklet;
    private final MongoTransactionManager mongoTransactionManager;

    public PeekPickStockBatchConfig(
            StockFetchingTasklet fetchingTasklet,
            StockAnalysisTasklet analysisTasklet,
            StockSavingTasklet savingTasklet,
            MongoTransactionManager mongoTransactionManager
    ) {
        this.fetchingTasklet = fetchingTasklet;
        this.analysisTasklet = analysisTasklet;
        this.savingTasklet = savingTasklet;
        this.mongoTransactionManager = mongoTransactionManager;
    }

    @Bean
    public JobRepository jobRepository(
            PlatformTransactionManager batchTransactionManager,
            DataSource dataSource
    ) throws Exception {
        final var factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(batchTransactionManager);
        factory.setDatabaseType("h2");
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager batchTransactionManager() {
        return new ResourcelessTransactionManager();
    }

    // TODO : Use a real database for production environment batch metadata
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("BATCH_METADATA")
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
                .build();
    }

    @Bean
    @Profile("dev")
    public Job peekPickStockJobDev(
            JobRepository jobRepository,
            PlatformTransactionManager batchTransactionManager
    ) {
        return new JobBuilder("peekPickStockJob", jobRepository)
                .start(this.fetchStockStepDev(jobRepository, batchTransactionManager))
                .next(this.analyzeStockStepDev(jobRepository, batchTransactionManager))
                .next(this.saveAllStockStepDev(jobRepository, batchTransactionManager))
                .build();
    }

    @Bean
    @Profile("prod")
    public Job peekPickStockJob(JobRepository jobRepository) {
        return new JobBuilder("peekPickStockJob", jobRepository)
                .start(this.fetchStockStep(jobRepository))
                .next(this.analyzeStockStep(jobRepository))
                .next(this.saveAllStockStep(jobRepository))
                .build();
    }

    @Bean
    @Profile("dev")
    public Step fetchStockStepDev(
            JobRepository jobRepository,
            PlatformTransactionManager batchTransactionManager
    ) {
        return new StepBuilder("fetchStockStep", jobRepository)
                .tasklet(fetchingTasklet, batchTransactionManager)
                .build();
    }

    @Bean
    @Profile("dev")
    public Step analyzeStockStepDev(
            JobRepository jobRepository,
            PlatformTransactionManager batchTransactionManager
    ) {
        return new StepBuilder("analyzeStockStep", jobRepository)
                .tasklet(analysisTasklet, batchTransactionManager)
                .build();
    }

    @Bean
    @Profile("dev")
    public Step saveAllStockStepDev(
            JobRepository jobRepository,
            PlatformTransactionManager batchTransactionManager
    ) {
        return new StepBuilder("saveAllStockStep", jobRepository)
                .tasklet(savingTasklet, batchTransactionManager)
                .build();
    }

    @Bean
    @Profile("prod")
    public Step fetchStockStep(JobRepository jobRepository) {
        return new StepBuilder("fetchStockStep", jobRepository)
                .tasklet(fetchingTasklet, mongoTransactionManager)
                .build();
    }

    @Bean
    @Profile("prod")
    public Step analyzeStockStep(JobRepository jobRepository) {
        return new StepBuilder("analyzeStockStep", jobRepository)
                .tasklet(analysisTasklet, mongoTransactionManager)
                .build();
    }

    @Bean
    @Profile("prod")
    public Step saveAllStockStep(JobRepository jobRepository) {
        return new StepBuilder("saveAllStockStep", jobRepository)
                .tasklet(savingTasklet, mongoTransactionManager)
                .build();
    }
}
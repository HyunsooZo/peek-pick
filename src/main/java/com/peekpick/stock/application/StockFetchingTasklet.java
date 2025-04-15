package com.peekpick.stock.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
public class StockFetchingTasklet implements Tasklet {

    private final StockQueryService stockQueryService;
    private final RetryTemplate retryTemplate;
    private final Logger logger;

    public StockFetchingTasklet(StockQueryService stockQueryService) {
        this.stockQueryService = stockQueryService;
        this.retryTemplate = RetryTemplate.builder().maxAttempts(3).fixedBackoff(1000).build();
        this.logger = LoggerFactory.getLogger(StockFetchingTasklet.class);
    }

    @Override
    public RepeatStatus execute(
            StepContribution contribution,
            ChunkContext chunkContext
    ) {
        retryTemplate.execute(context -> {
            try {
                logger.info("[FETCH STOCK TASKLET] Fetching stocks...");
                var stocks = stockQueryService.fetchAll();
                chunkContext.getStepContext().getStepExecution().getExecutionContext().put("stocks", stocks);
                logger.info("[FETCH STOCK TASKLET] Successfully fetched stocks");
                return null;
            } catch (Exception e) {
                logger.error("[FETCH STOCK TASKLET] Failed to fetch stocks. attempt : {} time(s)", context.getRetryCount(), e);
                throw e;
            }
        });
        return RepeatStatus.FINISHED;
    }
}

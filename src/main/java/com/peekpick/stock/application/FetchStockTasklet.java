package com.peekpick.stock.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class FetchStockTasklet implements Tasklet {

    private final Logger logger;
    private final StockQueryService stockQueryService;

    public FetchStockTasklet(StockQueryService stockQueryService) {
        this.logger = LoggerFactory.getLogger(FetchStockTasklet.class);
        this.stockQueryService = stockQueryService;
    }

    @Override
    public RepeatStatus execute(
            StepContribution contribution,
            ChunkContext chunkContext
    ) {
        logger.info("[FETCH STOCK TASKLET] Fetching stocks...");
        var stocks = stockQueryService.fetchAll();
        chunkContext.getStepContext().getStepExecution().getExecutionContext().put("stocks", stocks);
        logger.info("[FETCH STOCK TASKLET] Successfully fetched stocks");
        return RepeatStatus.FINISHED;
    }
}

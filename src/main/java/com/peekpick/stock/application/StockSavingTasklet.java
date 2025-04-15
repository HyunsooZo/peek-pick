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
public class StockSavingTasklet implements Tasklet {

    private final StockCommandService stockCommandService;
    private final RetryTemplate retryTemplate;
    private final Logger logger;

    public StockSavingTasklet(StockCommandService stockCommandService) {
        this.stockCommandService = stockCommandService;
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
                logger.info("[SAVE STOCK TASKLET] Saving stocks...");
                var stockAnalysisResult = chunkContext.getStepContext().getStepExecution().getExecutionContext().get("stockAnalysisResult");
                if (stockAnalysisResult instanceof StockApplicationData.StockAnalysisResult) {
                    stockCommandService.updateAll((StockApplicationData.StockAnalysisResult) stockAnalysisResult);
                    logger.info("[SAVE STOCK TASKLET] Successfully saved stocks");
                } else {
                    logger.warn("[SAVE STOCK TASKLET] No stock analysis result found to save");
                }
                return null;
            } catch (Exception e) {
                logger.error("[SAVE STOCK TASKLET] Failed to save stocks. attempt : {} time(s)", context.getRetryCount(), e);
                throw e;
            }
        });
        return RepeatStatus.FINISHED;
    }
}
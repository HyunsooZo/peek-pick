package com.peekpick.stock.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class SaveStockTasklet implements Tasklet {

    private final Logger logger;
    private final StockCommandService stockCommandService;

    public SaveStockTasklet(StockCommandService stockCommandService) {
        this.logger = LoggerFactory.getLogger(FetchStockTasklet.class);
        this.stockCommandService = stockCommandService;
    }

    @Override
    public RepeatStatus execute(
            StepContribution contribution,
            ChunkContext chunkContext
    ) {
        logger.info("[SAVE STOCK TASKLET] Saving stocks...");
        var stockAnalysisResult = chunkContext.getStepContext().getStepExecution().getExecutionContext().get("stockAnalysisResult");
        if (stockAnalysisResult instanceof StockAnalysisResult) {
            stockCommandService.updateAll( (StockAnalysisResult) stockAnalysisResult);
            logger.info("[SAVE STOCK TASKLET] Successfully saved stocks");
        } else {
            logger.warn("[SAVE STOCK TASKLET] No stock analysis result found to save");
        }
        return RepeatStatus.FINISHED;
    }
}
package com.peekpick.stock.application;

import com.peekpick.stock.domain.model.Stock;
import org.slf4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StockAnalysisTasklet implements Tasklet {

    private final StockAnalysisService stockAnalysisService;
    private final RetryTemplate retryTemplate;
    private final Logger logger;


    public StockAnalysisTasklet(StockAnalysisService stockAnalysisService) {
        this.stockAnalysisService = stockAnalysisService;
        this.retryTemplate = RetryTemplate.builder().maxAttempts(3).fixedBackoff(1000).build();
        this.logger = org.slf4j.LoggerFactory.getLogger(StockAnalysisTasklet.class);
    }

    @Override
    public RepeatStatus execute(
            StepContribution contribution,
            ChunkContext chunkContext
    ) {
        retryTemplate.execute(context -> {
            try {
                var raw = chunkContext.getStepContext().getStepExecution().getExecutionContext().get("stocks");
                if (raw instanceof List<?> rawList && !rawList.isEmpty() && rawList.getFirst() instanceof Stock) {
                    @SuppressWarnings("unchecked")
                    var stocks = (List<Stock>) rawList;
                    var indexNames = stocks.stream().map(Stock::market).map(Enum::name).toList();
                    logger.info("[ANALYZE STOCK TASKLET] Analyzing stocks...");
                    var command = new StockApplicationData.StockAnalysisCommand(indexNames, "init", LocalDateTime.now());
                    var stockAnalysisResult = stockAnalysisService.analyzeIndex(command);
                    chunkContext.getStepContext().getStepExecution().getExecutionContext().put("stockAnalysisResult", stockAnalysisResult);
                    logger.info("[ANALYZE STOCK TASKLET] Successfully analyzed stocks");
                }
                return null;
            } catch (Exception e) {
                logger.error("[ANALYZE STOCK TASKLET] Failed to analyze stocks. attempt : {} time(s)", context.getRetryCount(), e);
                throw e;
            }
        });
        return RepeatStatus.FINISHED;
    }
}

package com.peekpick.stock.application;

import com.peekpick.stock.domain.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StockCommandService {
    private final Logger logger;
    private final StockRepository stockRepository;

    public StockCommandService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
        this.logger = LoggerFactory.getLogger(StockQueryService.class);
    }

    public void updateAll(StockAnalysisResult stockAnalysisResult) {
        logger.info("[STOCK COMMAND SERVICE] Updating stocks...");
        stockRepository.updateAll(stockAnalysisResult.indexAnalysis());
        logger.info("[STOCK COMMAND SERVICE] Successfully updated stocks");
    }
}

package com.peekpick.stock.application;

import com.peekpick.stock.application.data.StockResult;
import com.peekpick.stock.domain.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StockQueryService {
    private final Logger logger;
    private final StockRepository stockRepository;

    public StockQueryService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
        this.logger = LoggerFactory.getLogger(StockQueryService.class);
    }

    public StockResult fetchAll() {
        logger.info("[STOCK QUERY SERVICE] Fetching stocks...");
        final var stocks = stockRepository.fetchAll();
        logger.info("[STOCK QUERY SERVICE] Successfully fetched stocks");
        final var stockInformation = stocks.stream().map(stock -> new StockResult.StockInformation(
                stock.id(),
                stock.code(),
                stock.market().name()
        )).toList();
        return new StockResult(stockInformation);
    }
}

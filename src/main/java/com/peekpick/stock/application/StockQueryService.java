package com.peekpick.stock.application;

import com.peekpick.stock.domain.model.Stock;
import com.peekpick.stock.domain.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockQueryService {
    private final Logger logger;
    private final StockRepository stockRepository;

    public StockQueryService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
        this.logger = LoggerFactory.getLogger(StockQueryService.class);
    }

    public List<Stock> fetchAll() {
        logger.info("[STOCK QUERY SERVICE] Fetching stocks...");
        final var stocks = stockRepository.fetchAll();
        logger.info("[STOCK QUERY SERVICE] Successfully fetched stocks");
        return stocks;
    }
}

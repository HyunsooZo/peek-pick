package com.peekpick.stock.infrastructure.persistence.mongo;

import com.peekpick.stock.domain.model.Stock;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "stocks")
public record StockDocument(
        @Id
        String id,
        String code,
        StockMarketDocument stockMarket,
        StockMarketAnalysisDocument analysis,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
        public Stock toDomain(){
                return new Stock(
                        this.id,
                        this.code,
                        this.stockMarket.market(),
                        this.stockMarket.index(),
                        this.stockMarket.fluctuation(),
                        this.analysis.marketName(),
                        this.analysis.marketAnalysis(),
                        this.createdAt,
                        this.updatedAt
                );
        }
}

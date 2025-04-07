package com.peekpick.stock.domain.model;

import java.time.LocalDateTime;

public record Stock(
        String id,
        String code,
        StockMarket attributes,
        StockMarketAnalysis analysis,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public Stock(
            String id,
            String code,
            IndexType market,
            Double index,
            Double fluctuation,
            String marketName,
            String marketAnalysis,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this(
                id,
                code,
                new StockMarket(market, index, fluctuation),
                new StockMarketAnalysis(marketName, marketAnalysis),
                createdAt,
                updatedAt
        );
    }

    public IndexType market() {
        return this.attributes.market();
    }

    public boolean hasRisen() {
        return attributes.fluctuation() > 0;
    }

    public String index() {
        return String.format("%.2f", this.attributes().index());
    }

    public String fluctuation() {
        return String.format("%.2f", this.attributes().fluctuation());
    }

    public String analysisContent() {
        return this.analysis.toHtmlString();
    }

}

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

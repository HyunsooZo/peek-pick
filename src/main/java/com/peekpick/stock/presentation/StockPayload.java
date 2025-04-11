package com.peekpick.stock.presentation;

import java.util.List;

public record StockPayload() {
    public record StockResponse(
            List<StockInformation> stockInformation
    ) {
        public record StockInformation(
                String stockId,
                String stockCode,
                String stockMarket
        ) {
            public static StockInformation of(
                    String stockId,
                    String stockCode,
                    String stockMarket
            ) {
                return new StockInformation(stockId, stockCode, stockMarket);
            }
        }
    }
}

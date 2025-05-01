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
        ) {}
    }
}

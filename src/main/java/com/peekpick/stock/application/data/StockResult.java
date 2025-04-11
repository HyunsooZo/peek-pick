package com.peekpick.stock.application.data;

import java.util.List;

public record StockResult(
        List<StockInformation> stockInformation
) {
    public record StockInformation(
            String stockId,
            String stockCode,
            String stockMarket
    ) {
    }
}

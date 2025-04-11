package com.peekpick.stock.presentation;

import com.peekpick.stock.application.data.StockResult;

public class StockMapper {
    public static StockPayload.StockResponse mapToResponse(StockResult stocks) {
        return new StockPayload.StockResponse(
                stocks.stockInformation()
                        .stream()
                        .map(stock -> new StockPayload.StockResponse.StockInformation(
                                stock.stockId(),
                                stock.stockCode(),
                                stock.stockMarket()
                        )).toList()
        );
    }
}

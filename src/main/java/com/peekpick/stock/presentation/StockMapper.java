package com.peekpick.stock.presentation;

import com.peekpick.stock.application.StockApplicationData;

public class StockMapper {
    public static StockPayload.StockResponse mapToResponse(StockApplicationData.StockResult stocks) {
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

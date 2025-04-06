package com.peekpick.stock.infrastructure;

public interface StockAnalysisGateway {
    StockAnalysisResponse analyze(StockAnalysisRequest request);
}

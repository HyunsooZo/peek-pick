package com.peekpick.stock.infrastructure.gateway;

public interface StockAnalysisGateway {
    StockAnalysisResponse analyze(StockAnalysisRequest request);
}

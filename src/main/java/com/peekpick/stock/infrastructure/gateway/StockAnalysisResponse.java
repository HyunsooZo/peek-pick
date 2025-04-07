package com.peekpick.stock.infrastructure.gateway;

import java.util.Map;

public interface StockAnalysisResponse {
    Map<String, String> indexAnalysis();
    String indexName();
}

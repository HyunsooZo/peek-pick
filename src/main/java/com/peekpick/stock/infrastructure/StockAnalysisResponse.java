package com.peekpick.stock.infrastructure;

import java.util.Map;

public interface StockAnalysisResponse {
    Map<String, String> indexAnalysis();
    String indexName();
}

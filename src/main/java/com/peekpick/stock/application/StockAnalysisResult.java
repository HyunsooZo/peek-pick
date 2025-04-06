package com.peekpick.stock.application;

import java.util.List;

public record StockAnalysisResult(List<IndexAnalysis> indexAnalysis) {
    public record IndexAnalysis(
            String indexName,
            String indexAnalysis
    ) {}
}

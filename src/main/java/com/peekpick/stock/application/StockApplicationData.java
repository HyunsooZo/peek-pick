package com.peekpick.stock.application;

import com.peekpick.stock.infrastructure.gateway.GeminiApiGateway;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record StockApplicationData() {

    public record StockResult(
            List<StockInformation> stockInformation
    ) implements Serializable {
        public record StockInformation(
                String stockId,
                String stockCode,
                String stockMarket
        ) implements Serializable {}
    }

    public record StockAnalysisCommand(
            List<String> indexTypes,
            String prompt,
            LocalDateTime currentDateTime
    ) {
        public GeminiApiGateway.GeminiRequest toApiRequest() {
            var parts = indexTypes.stream()
                    .map(indexType ->
                            new GeminiApiGateway.GeminiRequest.Part(String.format(prompt, currentDateTime, indexTypes))
                    )
                    .toList();
            return new GeminiApiGateway.GeminiRequest(List.of(new GeminiApiGateway.GeminiRequest.Content(parts)));
        }

        public StockApplicationData.StockAnalysisCommand copyWithPrompt(String prompt) {
            return new StockApplicationData.StockAnalysisCommand(
                    this.indexTypes,
                    prompt,
                    this.currentDateTime
            );
        }
    }

    public record StockAnalysisResult(List<StockApplicationData.StockAnalysisResult.IndexAnalysis> indexAnalysis) {
        public record IndexAnalysis(String indexName, String indexAnalysis) {}
    }

    public record StockAnalysisInfoResult(
            String stockId,
            String stockCode,
            String stockMarket,
            String indexName,
            String indexAnalysis
    ) {}
}


package com.peekpick.stock.application;

import com.peekpick.stock.infrastructure.GeminiApiGateway;

import java.time.LocalDateTime;
import java.util.List;

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
}

package com.peekpick.stock.application;

import com.peekpick.stock.infrastructure.StockAnalysisGateway;
import org.springframework.stereotype.Service;

@Service
public class StockAnalysisService {
    private final StockAnalysisGateway stockAnalysisGateway;
    private final String PROMPT = "%s 현재시각 기준 %s 지수에 대한 지수분석, 투자 이슈와 추천하는 투자 방향에 대해 말해줘.";

    public StockAnalysisService(StockAnalysisGateway stockAnalysisGateway) {
        this.stockAnalysisGateway = stockAnalysisGateway;
    }

    public StockAnalysisResult analyzeIndex(StockAnalysisCommand command) {
        var analysis = stockAnalysisGateway.analyze(command.toApiRequest());
        var analysisMap = analysis.indexAnalysis();
        var indexAnalysisList = analysisMap.entrySet().stream()
                .map(entry -> new StockAnalysisResult.IndexAnalysis(entry.getKey(), entry.getValue()))
                .toList();
        return new StockAnalysisResult(indexAnalysisList);
    }
}

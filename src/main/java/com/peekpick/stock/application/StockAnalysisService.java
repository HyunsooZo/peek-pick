package com.peekpick.stock.application;

import com.peekpick.stock.infrastructure.gateway.StockAnalysisGateway;
import org.springframework.stereotype.Service;

@Service
public class StockAnalysisService {
    private final StockAnalysisGateway stockAnalysisGateway;
    private final String PROMPT = """
            %s 현재 시각을 기준으로
            %s 에 대한 \"동향 분석\"과 \"투자 이슈\", \"추천하는 투자 방향\"을 구분하여 알려줘. 
            가장 첫 줄은 지수 이름만을 말하고 한줄 띈 다음 작성해줘
            """.trim();

    public StockAnalysisService(StockAnalysisGateway stockAnalysisGateway) {
        this.stockAnalysisGateway = stockAnalysisGateway;
    }

    public StockAnalysisResult analyzeIndex(StockAnalysisCommand command) {
        var analysis = stockAnalysisGateway.analyze(command.copyWithPrompt(PROMPT).toApiRequest());
        var analysisMap = analysis.indexAnalysis();
        var indexAnalysisList = analysisMap.entrySet().stream()
                .map(entry -> new StockAnalysisResult.IndexAnalysis(entry.getKey(), entry.getValue()))
                .toList();
        return new StockAnalysisResult(indexAnalysisList);
    }
}

package com.peekpick.stock.domain.repository;

import com.peekpick.stock.application.StockApplicationData;
import com.peekpick.stock.domain.model.Stock;

import java.util.List;

public interface StockRepository {
    List<Stock> fetchAll();

    void updateAll(List<StockApplicationData.StockAnalysisResult.IndexAnalysis> indexAnalyses);
}

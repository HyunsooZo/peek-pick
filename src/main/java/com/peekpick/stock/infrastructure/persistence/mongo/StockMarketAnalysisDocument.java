package com.peekpick.stock.infrastructure.persistence.mongo;

record StockMarketAnalysisDocument(
        String marketName,
        String marketAnalysis
) {}
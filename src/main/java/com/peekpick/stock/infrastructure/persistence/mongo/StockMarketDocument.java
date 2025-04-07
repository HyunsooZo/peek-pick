package com.peekpick.stock.infrastructure.persistence.mongo;

import com.peekpick.stock.domain.model.IndexType;

record StockMarketDocument(
        IndexType market,
        Double index,
        Double fluctuation
) {}

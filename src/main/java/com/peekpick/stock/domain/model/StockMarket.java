package com.peekpick.stock.domain.model;

record StockMarket(
        IndexType market,
        Double index,
        Double fluctuation
) {
}

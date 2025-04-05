package com.peekpick.stock.domain.model;

record StockMarketAnalysis(
        String marketName,
        String marketCode,
        String marketAnalysis
) {
    StockMarketAnalysis {
        if (marketName == null || marketName.trim().isEmpty()) {
            throw new IllegalArgumentException("Stock name must not be blank");
        }
        if (marketCode == null || marketCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Stock code must not be blank");
        }
        if (marketAnalysis == null || marketAnalysis.trim().isEmpty()) {
            throw new IllegalArgumentException("Analysis summary must not be blank");
        }
    }

    String toHtmlString() {
        return """
                    <div class="page">
                        <div class="stock-info">
                            <h2>$marketName ($marketCode)</h2>
                            <div class="analysis">
                                <p>$marketAnalysis</p>
                            </div>
                        </div>
                    </div>
                """.trim();
    }

    public String toHtmlTitle() {
        return marketName + " (" + marketCode + ")";
    }
}
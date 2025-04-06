package com.peekpick.stock.domain.model;

record StockMarketAnalysis(
        String marketName,
        String marketAnalysis
) {
    StockMarketAnalysis {
        if (marketName == null || marketName.trim().isEmpty()) {
            throw new IllegalArgumentException("Stock name must not be blank");
        }
        if (marketAnalysis == null || marketAnalysis.trim().isEmpty()) {
            throw new IllegalArgumentException("Analysis summary must not be blank");
        }
    }

    String toHtmlString() {
        return String.format("""
                    <div class="page">
                        <div class="stock-info">
                            <h2>%s</h2>
                            <div class="analysis">
                                <p>%s</p>
                            </div>
                        </div>
                    </div>
                """.trim(),
                this.marketName,
                this.marketAnalysis
        );
    }
}
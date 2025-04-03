package com.peekpick.notification.domain

data class Content (
    private val stockName: String,
    private val stockCode: String,
    private val analysisSummary: String
) {
    init {
        require(stockName.isNotBlank()) { "Stock name must not be blank" }
        require(stockCode.isNotBlank()) { "Stock code must not be blank" }
        require(analysisSummary.isNotBlank()) { "Analysis summary must not be blank" }
    }

    fun toHtmlString(): String {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Stock Analysis Report</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4; }
                    .container { max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
                    h1 { color: #333333; }
                    .stock-info { margin: 20px 0; }
                    .stock-info h2 { color: #007bff; }
                    .analysis { background-color: #f9f9f9; padding: 15px; border-radius: 5px; }
                    .footer { margin-top: 20px; text-align: center; color: #777777; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Stock Analysis Report</h1>
                    <div class="stock-info">
                        <h2>$stockName ($stockCode)</h2>
                        <div class="analysis">
                            <p>$analysisSummary</p>
                        </div>
                    </div>
                    <div class="footer">
                        <p>Generated by PeekPick &copy; 2025</p>
                    </div>
                </div>
            </body>
            </html>
        """.trimIndent()
    }

    fun toHtmlTitle(): String = "$stockName ($stockCode)"

    fun toHtmlBody(): String = "<p>$analysisSummary</p>"
}

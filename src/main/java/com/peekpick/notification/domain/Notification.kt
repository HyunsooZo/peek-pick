package com.peekpick.notification.domain

data class Notification(
    val id: String?,
    val recipient: Recipient,
    val contents: MutableList<Content>,
    val status: NotificationStatus
) {
    fun getScheduledTime(): Int {
        return recipient.recipientScheduledTime
    }

    fun getRecipientName(): String {
        return recipient.recipientName
    }

    fun getRecipientAddress(): String {
        return recipient.recipientAddress
    }

    fun isPending(): Boolean {
        return status == NotificationStatus.PENDING
    }

    fun isSent(): Boolean {
        return status == NotificationStatus.SENT
    }

    fun isFailed(): Boolean {
        return status == NotificationStatus.FAILED
    }

    fun addContent(content: Content) {
        if (this.contents.contains(content) || this.contents.size >= 5) {
            return
        }
        this.contents.add(content)
    }

    fun generatePagedHtml(): String {
        val contentPages = contents.mapIndexed { index, content ->
            """
            <div class="page" id="page$index" style="${if (index != 0) "display:none;" else ""}">
                <h2>${content.toHtmlTitle()}</h2>
                <div class="analysis">${content.toHtmlBody()}</div>
            </div>
            """
        }.joinToString("\n")

        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Stock Analysis - PeekPick</title>
                <style>
                    body { font-family: Arial, sans-serif; padding: 20px; background-color: #f4f4f4; }
                    .container { background: white; padding: 20px; border-radius: 8px; max-width: 600px; margin: 0 auto; }
                    .page { margin-bottom: 20px; }
                    .controls { text-align: center; margin-top: 20px; }
                    button { margin: 0 5px; padding: 5px 10px; }
                    .footer { margin-top: 30px; text-align: center; color: #777; font-size: 12px; }
                </style>
                <script>
                    let currentPage = 0;
                    function showPage(index) {
                        const pages = document.querySelectorAll('.page');
                        pages.forEach((p, i) => {
                            p.style.display = i === index ? 'block' : 'none';
                        });
                        currentPage = index;
                    }
                    function nextPage() {
                        const pages = document.querySelectorAll('.page');
                        if (currentPage < pages.length - 1) showPage(currentPage + 1);
                    }
                    function prevPage() {
                        if (currentPage > 0) showPage(currentPage - 1);
                    }
                </script>
            </head>
            <body>
                <div class="container">
                    <h1>Stock Analysis Report</h1>
                    $contentPages
                    <div class="controls">
                        <button onclick="prevPage()">Previous</button>
                        <button onclick="nextPage()">Next</button>
                    </div>
                    <div class="footer">
                        <p>Generated by PeekPick &copy; 2025</p>
                    </div>
                </div>
            </body>
            </html>
        """.trimIndent()
    }

    fun subject(): String {
        return "Stock Analysis Report For ${this.recipient.recipientName}"
    }
}

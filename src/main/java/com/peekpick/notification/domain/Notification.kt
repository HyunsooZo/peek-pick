package com.peekpick.notification.domain

data class Notification(
    val id: String?,
    val recipient: Recipient,
    val contents: MutableList<String>,
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

    fun addContent(content: String) {
        if (this.contents.contains(content) || this.contents.size >= 5) {
            return
        }
        this.contents.add(content)
    }

    fun assembleContents(
        pagedHtmlFormat: String,
        contentPages: String
    ): String {
        return String.format(pagedHtmlFormat, contentPages)
    }

    fun subject(): String {
        return "Stock Analysis Report For ${this.recipient.recipientName}"
    }
}

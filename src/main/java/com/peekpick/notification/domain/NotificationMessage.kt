package com.peekpick.notification.domain

data class NotificationMessage(
    val id: String?,
    val recipient: Recipient,
    val contents: String,
    val status: NotificationStatus
) {
    fun scheduledTime(): Int {
        return recipient.recipientScheduledTime
    }

    fun recipientName(): String {
        return recipient.recipientName
    }

    fun recipientChannel(): Channel {
        return recipient.recipientChannel
    }

    fun recipientAddress(): String {
        return recipient.recipientAddress
    }

    fun isSent(): Boolean {
        return status == NotificationStatus.SENT
    }

    fun isFailed(): Boolean {
        return status == NotificationStatus.FAILED
    }

    fun subject(): String {
        return "[PeekPick] ${this.recipient.recipientName}님을 위한 오늘의 픽픽 맞춤 주식 동향 "
    }

    fun content(): String {
        return this.contents
    }

    fun fail(): NotificationMessage {
        return this.copy(status = NotificationStatus.FAILED)
    }

    fun success():NotificationMessage{
        return this.copy(status = NotificationStatus.SENT)
    }

}

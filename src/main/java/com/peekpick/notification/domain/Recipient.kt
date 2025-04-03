package com.peekpick.notification.domain

data class Recipient(
    val recipientName: String,
    val recipientAddress: String,
    val recipientChannel: Channel,
    val recipientScheduledTime: Int
) {
    init {
        require(recipientName.isNotBlank()) { "Recipient name must not be blank" }
        require(recipientAddress.isNotBlank()) { "Recipient email must not be blank" }
        require(recipientScheduledTime in 0..24) { "Scheduled time must be between 0 and 24" }
    }
}
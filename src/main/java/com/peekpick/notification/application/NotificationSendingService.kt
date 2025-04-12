package com.peekpick.notification.application

import com.peekpick.notification.domain.Channel
import com.peekpick.notification.infrastructure.gateway.NotificationGateway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NotificationSendingService(
    private val notificationGateway: NotificationGateway
) {
    private val log: Logger = LoggerFactory.getLogger(NotificationSendingService::class.java)

    fun sendNotification(
        recipientName: String,
        recipientAddress: String,
        recipientChannel: Channel,
        recipientScheduledTime: Int,
        subject: String,
        content: String
    ) {
        require(recipientName.isNotBlank()) { "Recipient name must not be blank" }
        require(recipientAddress.isNotBlank()) { "Recipient email must not be blank" }
        require(recipientScheduledTime in 0..24) { "Scheduled time must be between 0 and 24" }

        notificationGateway.sendNotification(
            address = recipientAddress,
            subject = subject,
            content = content
        )
    }
}
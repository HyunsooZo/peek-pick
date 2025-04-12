package com.peekpick.notification.infrastructure.persistence.mongo

import com.peekpick.notification.domain.NotificationMessage
import com.peekpick.notification.domain.NotificationStatus
import com.peekpick.notification.domain.Recipient
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "notifications")
class NotificationDocument {
    var id: String? = null
    var recipient: RecipientDocument = RecipientDocument()
    var subject: String = ""
    var content: String = ""
    var status: NotificationStatus = NotificationStatus.PENDING
    var createdAt: Long? = null
    var updatedAt: Long? = null

    constructor()

    constructor(
        id: String?,
        recipient: RecipientDocument,
        subject: String,
        content: String,
        status: NotificationStatus,
        createdAt: Long?,
        updatedAt: Long?
    ) {
        this.id = id
        this.recipient = recipient
        this.subject = subject
        this.content = content
        this.status = status
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }

    companion object {
        fun from(notificationMessage: NotificationMessage): NotificationDocument {
            return NotificationDocument(
                id = notificationMessage.id,
                recipient = RecipientDocument(
                    recipientName = notificationMessage.recipientName(),
                    recipientAddress = notificationMessage.recipientAddress(),
                    recipientChannel = notificationMessage.recipientChannel(),
                    recipientScheduledTime = notificationMessage.scheduledTime()
                ),
                subject = notificationMessage.subject(),
                content = notificationMessage.contents,
                status = notificationMessage.status,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
        }
    }


    fun toDomain(): NotificationMessage {
        return NotificationMessage(
            id = this.id,
            recipient = Recipient(
                recipientName = this.recipient.recipientName,
                recipientAddress = this.recipient.recipientAddress,
                recipientChannel = this.recipient.recipientChannel,
                recipientScheduledTime = this.recipient.recipientScheduledTime
            ),
            contents = this.content,
            status = this.status
        )
    }
}
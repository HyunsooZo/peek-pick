package com.peekpick.notification.infrastructure.persistence.repository

import com.peekpick.notification.domain.NotificationMessage
import com.peekpick.notification.domain.NotificationRepository
import com.peekpick.notification.infrastructure.persistence.mongo.NotificationDocument
import com.peekpick.notification.infrastructure.persistence.mongo.NotificationMongoRepository
import org.springframework.stereotype.Repository

@Repository
class NotificationRepositoryImpl(
    private val notificationMongoRepository: NotificationMongoRepository,
) : NotificationRepository {
    override fun save(notificationMessage: NotificationMessage): NotificationMessage {
        return notificationMongoRepository.save(NotificationDocument.from(notificationMessage)).toDomain();
    }
}
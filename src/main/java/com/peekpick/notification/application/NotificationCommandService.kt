package com.peekpick.notification.application

import com.peekpick.notification.domain.NotificationMessage
import com.peekpick.notification.domain.NotificationRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NotificationCommandService(
    private val notificationRepository: NotificationRepository,
) {
    private val log = LoggerFactory.getLogger(NotificationCommandService::class.java)

    fun save(notificationMessage: NotificationMessage): NotificationMessage {
        log.info("[NOTIFICATION COMMAND SERVICE] Saving notification: $notificationMessage")
        val savedNotification = notificationRepository.save(notificationMessage)
        log.info("[NOTIFICATION COMMAND SERVICE] Notification saved! id: ${savedNotification.id}")
        return savedNotification
    }
}
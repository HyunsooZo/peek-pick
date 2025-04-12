package com.peekpick.notification.application

import com.peekpick.notification.domain.NotificationRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NotificationQueryService(
    private val notificationRepository: NotificationRepository,
) {
    private val log = LoggerFactory.getLogger(NotificationQueryService::class.java)
}
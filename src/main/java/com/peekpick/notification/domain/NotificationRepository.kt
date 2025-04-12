package com.peekpick.notification.domain

interface NotificationRepository {
    fun save(notificationMessage: NotificationMessage): NotificationMessage
}
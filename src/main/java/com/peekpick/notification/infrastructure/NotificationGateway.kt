package com.peekpick.notification.infrastructure

import com.peekpick.notification.domain.Notification

interface NotificationGateway {
    fun sendNotification(notification: Notification)
}
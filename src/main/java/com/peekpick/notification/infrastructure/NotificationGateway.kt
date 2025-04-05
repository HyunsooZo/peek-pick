package com.peekpick.notification.infrastructure

interface NotificationGateway {
    fun sendNotification(
        address: String,
        subject: String,
        content: String
    )
}
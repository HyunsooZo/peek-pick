package com.peekpick.notification.infrastructure.gateway

interface NotificationGateway {
    fun sendNotification(
        address: String,
        subject: String,
        content: String
    )
}
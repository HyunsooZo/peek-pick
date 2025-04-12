package com.peekpick.notification.infrastructure.queue

import com.peekpick.notification.domain.NotificationMessage

interface MessageQueue {
    fun size(): Int
    fun isEmpty(): Boolean
    fun dequeue(): NotificationMessage?
    fun enqueue(message: NotificationMessage)
    fun poll(): NotificationMessage?
}
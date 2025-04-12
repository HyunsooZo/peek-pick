package com.peekpick.notification.application

import com.peekpick.notification.infrastructure.queue.MessageQueue
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@EnableScheduling
class NotificationScheduler(
    private val notificationSendingService: NotificationSendingService,
    @Qualifier("inMemory") private val queue: MessageQueue,
) {
    @Scheduled(fixedRate = 3600000)
    fun scheduleNotification() {
        if (queue.isEmpty()) {
            return
        }
        val notificationMessage = queue.poll()
        if (notificationMessage != null) {
            notificationSendingService.sendNotification(
                recipientName = notificationMessage.recipientName(),
                recipientAddress = notificationMessage.recipientAddress(),
                recipientChannel = notificationMessage.recipientChannel(),
                recipientScheduledTime = notificationMessage.scheduledTime(),
                subject = notificationMessage.subject(),
                content = notificationMessage.content()
            )
        }
    }

}
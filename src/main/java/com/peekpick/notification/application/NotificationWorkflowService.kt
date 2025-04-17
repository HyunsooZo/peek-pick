package com.peekpick.notification.application

import com.peekpick.notification.infrastructure.queue.MessageQueue
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class NotificationWorkflowService(
    @Qualifier("inMemory")
    private val queue: MessageQueue,
    private val notificationSender: NotificationSender,
    private val notificationCommandService: NotificationCommandService,
) {
    private val log = LoggerFactory.getLogger(NotificationWorkflowService::class.java)

    fun processNext() {
        if (queue.isEmpty()) return
        val message = queue.poll() ?: return
        try {
            notificationSender.sendNotification(
                recipientName = message.recipientName(),
                recipientAddress = message.recipientAddress(),
                recipientChannel = message.recipientChannel(),
                recipientScheduledTime = message.scheduledTime(),
                subject = message.subject(),
                content = message.content()
            )
            notificationCommandService.save(message.success())
        } catch (e: Exception) {
            log.error("[NOTIFICATION PROCESSOR] Error while sending: ${e.message}")
            log.info("[NOTIFICATION PROCESSOR] Re-enqueueing message: $message")
            queue.enqueue(message)
            notificationCommandService.save(message.fail())
        }
    }
}

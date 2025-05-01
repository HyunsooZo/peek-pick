package com.peekpick.notification.application.usecase

import com.peekpick.notification.application.service.MessageSendingService
import com.peekpick.notification.application.service.NotificationCommandService
import com.peekpick.notification.infrastructure.queue.MessageQueue
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

interface ProcessNotificationUseCase{
    fun proceed()
}
@Service
class ProcessNotificationUseCaseImpl(
    @Qualifier("inMemory")
    private val queue: MessageQueue,
    private val messageSendingService: MessageSendingService,
    private val notificationCommandService: NotificationCommandService
) : ProcessNotificationUseCase {
    private val log = LoggerFactory.getLogger(ProcessNotificationUseCase::class.java)
    override fun proceed() {
        if (queue.isEmpty()) return
        val message = queue.poll() ?: return
        try {
            messageSendingService.sendNotification(
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

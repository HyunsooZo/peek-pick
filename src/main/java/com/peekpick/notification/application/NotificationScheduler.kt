package com.peekpick.notification.application

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@EnableScheduling
class NotificationScheduler(
    private val notificationWorkflowService: NotificationWorkflowService
) {
    private val log = LoggerFactory.getLogger(NotificationScheduler::class.java)
    @Scheduled(fixedRate = 3600000)
    fun scheduleNotification() {
        log.info("[NOTIFICATION SCHEDULER] Scheduling notification..")
        notificationWorkflowService.processNext()
        log.info("[NOTIFICATION SCHEDULER] Notification scheduled!")
    }
}
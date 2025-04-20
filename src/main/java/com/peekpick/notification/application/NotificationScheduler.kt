package com.peekpick.notification.application

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@EnableScheduling
class NotificationScheduler(
    private val notificationWorkflowService: NotificationWorkflowService,
    private val notificationSettingService: NotificationSettingService
) {
    private val log = LoggerFactory.getLogger(NotificationScheduler::class.java)

    @Scheduled(fixedRate = 3600000)
    fun scheduleNotification() {
        log.info("[NOTIFICATION SCHEDULER] Scheduling notification..")
        notificationWorkflowService.processNext()
        log.info("[NOTIFICATION SCHEDULER] Notification scheduled!")
    }

    @Scheduled(cron = "0 50 * * * ?")
    fun setupNotification() {
        log.info("[NOTIFICATION SCHEDULER] Setting up notification..")
        notificationSettingService.createNotificationMessagesByScheduledTime(LocalDateTime.now().hour + 1)
        log.info("[NOTIFICATION SCHEDULER] Notification set up!")
    }
}
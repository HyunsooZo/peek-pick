package com.peekpick.notification.application

import com.peekpick.notification.application.usecase.CreateNotificationUseCase
import com.peekpick.notification.application.usecase.ProcessNotificationUseCase
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@EnableScheduling
class NotificationSettingScheduler(
    private val processNotificationUseCase: ProcessNotificationUseCase,
    private val createNotificationUseCase: CreateNotificationUseCase,
) {
    private val log = LoggerFactory.getLogger(NotificationSettingScheduler::class.java)

    @Scheduled(fixedRate = 3600000)
    fun scheduleNotification() {
        log.info("[NOTIFICATION SCHEDULER] Scheduling notification..")
        processNotificationUseCase.proceed()
        log.info("[NOTIFICATION SCHEDULER] Notification scheduled!")
    }

    @Scheduled(cron = "0 50 * * * ?")
    fun setupNotification() {
        log.info("[NOTIFICATION SCHEDULER] Setting up notification..")
        createNotificationUseCase.createNotificationMessagesByScheduledTime(LocalDateTime.now().hour + 1)
        log.info("[NOTIFICATION SCHEDULER] Notification set up!")
    }
}
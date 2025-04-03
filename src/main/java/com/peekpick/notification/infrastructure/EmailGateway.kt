package com.peekpick.notification.infrastructure

import com.peekpick.notification.domain.Notification
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component

@Component("email")
class EmailGateway(
    private val javaMailSender: JavaMailSender,
) : NotificationGateway {
    private val log: Logger = LoggerFactory.getLogger(EmailGateway::class.java)

    override fun sendNotification(notification: Notification) {
        log.info("[EmailGateway] Sending email notification to ${notification.getRecipientAddress()}")
        val pagedHtml = notification.generatePagedHtml()
        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setTo(notification.getRecipientAddress())
        helper.setSubject(notification.subject())
        helper.setText(pagedHtml, true)
        javaMailSender.send(message)
        log.info("[EmailGateway] Email notification sent to ${notification.getRecipientAddress()}")
    }
}
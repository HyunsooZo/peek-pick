package com.peekpick.notification.infrastructure.gateway

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

    override fun sendNotification(
        address: String,
        subject: String,
        content: String
    ) {
        log.info("[EmailGateway] Sending email notification to ${address}")
        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setTo(address)
        helper.setSubject(subject)
        helper.setText(content, true)
        javaMailSender.send(message)
        log.info("[EmailGateway] Email notification sent to ${address}")
    }
}
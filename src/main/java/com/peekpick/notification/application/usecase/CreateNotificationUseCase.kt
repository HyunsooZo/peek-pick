package com.peekpick.notification.application.usecase

import com.peekpick.member.application.MemberQueryService
import com.peekpick.notification.domain.Channel
import com.peekpick.notification.domain.NotificationMessage
import com.peekpick.notification.domain.NotificationStatus
import com.peekpick.notification.domain.Recipient
import com.peekpick.notification.infrastructure.queue.MessageQueue
import com.peekpick.stock.application.StockQueryService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

interface CreateNotificationUseCase{
    fun createNotificationMessagesByScheduledTime(time: Int)
}
@Service
class CreateNotificationUseCaseImpl(
    @Qualifier("inMemory")
    private val messageQueue: MessageQueue,
    private val memberQueryService: MemberQueryService,
    private val stockQueryService: StockQueryService,
) : CreateNotificationUseCase {
   override fun createNotificationMessagesByScheduledTime(time: Int) {
        val analysisMap = stockQueryService.fetchAllAnalysisStocks()
            .associateBy { it.stockMarket }

        val members = memberQueryService.findByScheduledTime(time)

        members.forEach { member ->
            val relevantAnalysis = member.stocks.mapNotNull { analysisMap[it] }.map { it.indexAnalysis }
            if (relevantAnalysis.isEmpty()) return@forEach
            val messageContent = relevantAnalysis.joinToString(separator = "\n")
            val message = NotificationMessage(
                id = null,
                recipient = Recipient(
                    recipientName = member.nickname,
                    recipientAddress = member.email,
                    recipientChannel = Channel.EMAIL,
                    recipientScheduledTime = time
                ),
                contents = messageContent,
                status = NotificationStatus.PENDING
            )
            messageQueue.enqueue(message)
        }
    }
}

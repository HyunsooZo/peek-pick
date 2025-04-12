package com.peekpick.notification.infrastructure.persistence.mongo

import com.peekpick.notification.domain.Channel

data class RecipientDocument(
    var recipientName: String = "",
    var recipientAddress: String = "",
    var recipientChannel: Channel = Channel.EMAIL,
    var recipientScheduledTime: Int = 8
)
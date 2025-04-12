package com.peekpick.notification.infrastructure.queue

import com.peekpick.notification.domain.NotificationMessage
import org.springframework.stereotype.Component
import java.util.*

@Component("inMemory")
class InMemoryQueue : MessageQueue {
    private val queue: Queue<NotificationMessage> = LinkedList()

    override fun enqueue(message: NotificationMessage) {
        queue.add(message)
    }

    override fun dequeue(): NotificationMessage? {
        return if (queue.isNotEmpty()) {
            queue.peek()
        } else {
            null
        }
    }

    override fun poll(): NotificationMessage? {
        return if (queue.isNotEmpty()) {
            queue.poll()
        } else {
            null
        }
    }

    override fun isEmpty(): Boolean {
        return queue.isEmpty()
    }

    override fun size(): Int {
        return queue.size
    }
}
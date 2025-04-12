package com.peekpick.notification.infrastructure.persistence.mongo

import org.springframework.data.mongodb.repository.MongoRepository

interface NotificationMongoRepository : MongoRepository<NotificationDocument, String> {
}
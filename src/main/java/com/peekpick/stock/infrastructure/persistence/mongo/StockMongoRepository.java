package com.peekpick.stock.infrastructure.persistence.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockMongoRepository extends MongoRepository<StockDocument, String> {

}
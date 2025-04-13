package com.peekpick.stock.infrastructure.persistence.repository;

import com.peekpick.stock.application.StockApplicationData;
import com.peekpick.stock.domain.model.Stock;
import com.peekpick.stock.domain.repository.StockRepository;
import com.peekpick.stock.infrastructure.persistence.mongo.StockDocument;
import com.peekpick.stock.infrastructure.persistence.mongo.StockMongoRepository;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StockRepositoryImpl implements StockRepository {
    private final StockMongoRepository stockMongoRepository;
    private final MongoTemplate mongoTemplate;

    public StockRepositoryImpl(
            StockMongoRepository stockMongoRepository,
            MongoTemplate mongoTemplate
    ) {
        this.mongoTemplate = mongoTemplate;
        this.stockMongoRepository = stockMongoRepository;
    }

    @Override
    public List<Stock> fetchAll() {
        return stockMongoRepository.findAll().stream().map(StockDocument::toDomain).toList();
    }

    @Override
    public void updateAll(List<StockApplicationData.StockAnalysisResult.IndexAnalysis> indexAnalyses) {
        BulkOperations ops = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, StockDocument.class);
        for (var indexAnalysis : indexAnalyses) {
            final var query = Query.query(Criteria.where("IndexType").is(indexAnalysis.indexName()));
            final var update = new Update()
                    .set("marketName", indexAnalysis.indexName())
                    .set("marketAnalysis", indexAnalysis.indexAnalysis());
            ops.updateMulti(query, update);
        }
        ops.execute();
    }
}

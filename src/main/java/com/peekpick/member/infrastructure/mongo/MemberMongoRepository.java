package com.peekpick.member.infrastructure.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberMongoRepository extends MongoRepository<MemberDocument, String> {
    boolean existsByEmail(String email);
}

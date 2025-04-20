package com.peekpick.member.infrastructure.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MemberMongoRepository extends MongoRepository<MemberDocument, String> {
    boolean existsByEmail(String email);

    MemberDocument findByEmail(String email);

    List<MemberDocument> findAllByPreferenceHour(Integer preferenceHour);
}

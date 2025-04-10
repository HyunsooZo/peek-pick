package com.peekpick.member.infrastructure.repository;

import com.peekpick.member.domain.model.Member;
import com.peekpick.member.domain.repository.MemberRepository;
import com.peekpick.member.infrastructure.mongo.MemberDocument;
import com.peekpick.member.infrastructure.mongo.MemberMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberMongoRepository memberMongoRepository;

    public MemberRepositoryImpl(MemberMongoRepository memberMongoRepository) {
        this.memberMongoRepository = memberMongoRepository;
    }

    @Override
    public boolean existsByEmail(String email) {
        return memberMongoRepository.existsByEmail(email);
    }

    @Override
    public Member save(Member member) {
        return memberMongoRepository.save(MemberDocument.from(member)).toDomain();
    }

    @Override
    public Member findByEmail(String email) {
        return memberMongoRepository.findByEmail(email).toDomain();
    }
}

package com.peekpick.member.domain.repository;

import com.peekpick.member.domain.model.Member;

public interface MemberRepository {
    boolean existsByEmail(String email);

    Member save(Member member);

    Member findByEmail(String email);
}

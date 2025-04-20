package com.peekpick.member.domain.repository;

import com.peekpick.member.domain.model.Member;

import java.util.List;

public interface MemberRepository {
    boolean existsByEmail(String email);

    Member save(Member member);

    Member findByEmail(String email);

    List<Member> findByScheduledTime(Integer time);
}

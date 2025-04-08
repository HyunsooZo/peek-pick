package com.peekpick.member.application.service;

import com.peekpick.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberQueryService {
    private final MemberRepository memberRepository;

    public MemberQueryService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
}

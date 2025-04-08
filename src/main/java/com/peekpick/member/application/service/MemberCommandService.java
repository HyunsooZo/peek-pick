package com.peekpick.member.application.service;

import com.peekpick.member.domain.model.Member;
import com.peekpick.member.domain.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberCommandService {
    private final MemberRepository memberRepository;
    private final Logger logger = LoggerFactory.getLogger(MemberCommandService.class);

    public MemberCommandService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member save(Member member) {
        logger.info("[MEMBER COMMAND SERVICE] Saving member...");
        var savedMember = memberRepository.save(member);
        logger.info("[MEMBER COMMAND SERVICE] Successfully saved member");
        return savedMember;
    }
}

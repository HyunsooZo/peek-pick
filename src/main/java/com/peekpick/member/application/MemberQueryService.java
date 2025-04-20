package com.peekpick.member.application;

import com.peekpick.member.domain.model.Member;
import com.peekpick.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberQueryService {
    private final MemberRepository memberRepository;

    MemberQueryService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public List<MemberApplicationData.MemberInfoResult> findByScheduledTime(Integer time) {
        return memberRepository.findByScheduledTime(time).stream().map(Member::toApplicationData).toList();
    }
}

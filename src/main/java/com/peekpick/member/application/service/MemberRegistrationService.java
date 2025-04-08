package com.peekpick.member.application.service;

import com.peekpick.member.application.data.MemberInfoResult;
import com.peekpick.member.application.data.MemberRegistrationCommand;
import com.peekpick.member.domain.model.Member;
import org.springframework.stereotype.Service;

@Service
public class MemberRegistrationService {
    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    public MemberRegistrationService(
            MemberQueryService memberQueryService,
            MemberCommandService memberCommandService
    ) {
        this.memberQueryService = memberQueryService;
        this.memberCommandService = memberCommandService;
    }

    public MemberInfoResult register(MemberRegistrationCommand command) {
        this.assertEmailNotExists(command.email());
        final var member = new Member(command.nickname(), command.email(), command.preferenceHour(), command.stocks());
        final var savedMember = memberCommandService.save(member);
        return new MemberInfoResult(savedMember.nickname(), savedMember.email());
    }

    private void assertEmailNotExists(String email) {
        if (memberQueryService.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
    }
}

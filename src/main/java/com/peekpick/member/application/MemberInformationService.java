package com.peekpick.member.application;

import com.peekpick.member.domain.model.Member;
import org.springframework.stereotype.Service;

@Service
public class MemberInformationService {
    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    public MemberInformationService(
            MemberQueryService memberQueryService,
            MemberCommandService memberCommandService
    ) {
        this.memberQueryService = memberQueryService;
        this.memberCommandService = memberCommandService;
    }

    public MemberApplicationData.MemberInfoResult register(MemberApplicationData.MemberRegistration command) {
        if (memberQueryService.existsByEmail(command.email())) {
            throw new IllegalArgumentException("Email already exists");
        }
        final var member = new Member(command.nickname(), command.email(), command.preferenceHour(), command.stocks());
        final var savedMember = memberCommandService.save(member);
        return new MemberApplicationData.MemberInfoResult(
                savedMember.nickname(),
                savedMember.email(),
                savedMember.preferenceHour(),
                savedMember.stocks(),
                savedMember.notificationEnabled()
        );
    }

    public MemberApplicationData.MemberInfoResult update(MemberApplicationData.MemberModification command) {
        final var member = memberQueryService.findByEmail(command.email());
        final var updatedMember = member.updateEmail(command.email())
                .updateNickname(command.nickname())
                .updatePreferenceHour(command.preferenceHour())
                .updateStocks(command.stocks())
                .updateNotification(command.notificationEnabled());
        final var savedMember = memberCommandService.save(updatedMember);
        return new MemberApplicationData.MemberInfoResult(
                savedMember.nickname(),
                savedMember.email(),
                savedMember.preferenceHour(),
                savedMember.stocks(),
                savedMember.notificationEnabled()
        );
    }
}

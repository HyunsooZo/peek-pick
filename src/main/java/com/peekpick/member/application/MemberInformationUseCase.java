package com.peekpick.member.application;

import com.peekpick.member.domain.model.Member;
import org.springframework.stereotype.Service;

public interface MemberInformationUseCase {
    MemberApplicationData.MemberInfoResult register(MemberApplicationData.MemberRegistration command);
    MemberApplicationData.MemberInfoResult update(MemberApplicationData.MemberModification command);

@Service
class MemberInformationUseCaseImpl implements MemberInformationUseCase {
    private final MemberQueryService memberQueryService;
    private final com.peekpick.member.application.MemberCommandService memberCommandService;

    public MemberInformationUseCaseImpl(
            MemberQueryService memberQueryService,
            com.peekpick.member.application.MemberCommandService memberCommandService
    ) {
        this.memberQueryService = memberQueryService;
        this.memberCommandService = memberCommandService;
    }

    @Override
    public MemberApplicationData.MemberInfoResult register(MemberApplicationData.MemberRegistration command) {
        if (memberQueryService.existsByEmail(command.email())) {
            throw new IllegalArgumentException("Email already exists");
        }
        final var member = new Member(command.nickname(), command.email(), command.preferenceHour(), command.stocks());
        final var savedMember = memberCommandService.save(member);
        return savedMember.toApplicationData();

    }

    @Override
    public MemberApplicationData.MemberInfoResult update(MemberApplicationData.MemberModification command) {
        final var member = memberQueryService.findByEmail(command.email());
        final var updatedMember = member.updateEmail(command.email())
                .updateNickname(command.nickname())
                .updatePreferenceHour(command.preferenceHour())
                .updateStocks(command.stocks())
                .updateNotification(command.notificationEnabled());
        final var savedMember = memberCommandService.save(updatedMember);
        return savedMember.toApplicationData();
    }
}
}

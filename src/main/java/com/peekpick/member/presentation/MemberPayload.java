package com.peekpick.member.presentation;

import com.peekpick.member.application.data.MemberInfoResult;
import com.peekpick.member.application.data.MemberRegistrationCommand;

import java.util.List;

public record MemberPayload() {
    public record RegisterRequest(
            String nickname,
            String email,
            Integer preferenceHour,
            List<String> stocks
    ) {
        public MemberRegistrationCommand toCommand() {
            return new MemberRegistrationCommand(nickname, email, preferenceHour, stocks);
        }
    }

    public record RegisterResponse(
            String nickname,
            String email
    ) {
        public static RegisterResponse from(MemberInfoResult register) {
            return new RegisterResponse(register.nickname(), register.email());
        }
    }
}

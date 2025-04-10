package com.peekpick.member.presentation;

import com.peekpick.member.application.data.MemberInfoResult;
import com.peekpick.member.application.data.MemberModification;
import com.peekpick.member.application.data.MemberRegistration;

import java.util.List;

public record MemberInformationPayload() {

    public record InformationRequest(
            String nickname,
            String email,
            Integer preferenceHour,
            List<String> stocks,
            Boolean notificationEnabled
    ) {
        public MemberRegistration toRegistration() {
            return new MemberRegistration(nickname, email, preferenceHour, stocks);
        }

        public MemberModification toModification() {
            return new MemberModification(nickname, email, preferenceHour, stocks, notificationEnabled);
        }
    }

    public record InformationResponse(
            String nickname,
            String email,
            Integer preferenceHour,
            List<String> stocks,
            Boolean notificationEnabled
    ) {
        public static InformationResponse from(MemberInfoResult register) {
            return new InformationResponse(
                    register.nickname(),
                    register.email(),
                    register.preferenceHour(),
                    register.stocks(),
                    register.notificationEnabled()
            );
        }
    }
}

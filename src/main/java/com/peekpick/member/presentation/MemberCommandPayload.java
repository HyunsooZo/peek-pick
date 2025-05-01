package com.peekpick.member.presentation;

import com.peekpick.member.application.MemberApplicationData;

import java.util.List;

public record MemberCommandPayload() {

    public record InformationRequest(
            String nickname,
            String email,
            Integer preferenceHour,
            List<String> stocks,
            Boolean notificationEnabled
    ) {
        public MemberApplicationData.MemberRegistration toRegistration() {
            return new MemberApplicationData.MemberRegistration(nickname, email, preferenceHour, stocks);
        }

        public MemberApplicationData.MemberModification toModification() {
            return new MemberApplicationData.MemberModification(nickname, email, preferenceHour, stocks, notificationEnabled);
        }
    }

    public record InformationResponse(
            String nickname,
            String email,
            Integer preferenceHour,
            List<String> stocks,
            Boolean notificationEnabled
    ) {
    }
}

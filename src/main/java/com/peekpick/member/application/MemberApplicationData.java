package com.peekpick.member.application;

import java.util.List;

public record MemberApplicationData() {
    public record MemberInfoResult(
            String nickname,
            String email,
            Integer preferenceHour,
            List<String> stocks,
            Boolean notificationEnabled
    ) {}

    public record MemberModification(
            String nickname,
            String email,
            Integer preferenceHour,
            List<String> stocks,
            Boolean notificationEnabled
    ) {}

    public record MemberRegistration(
            String nickname,
            String email,
            Integer preferenceHour,
            List<String> stocks
    ) {}

}


package com.peekpick.member.application.data;

import java.util.List;

public record MemberModification(
        String nickname,
        String email,
        Integer preferenceHour,
        List<String> stocks,
        Boolean notificationEnabled
) {
}

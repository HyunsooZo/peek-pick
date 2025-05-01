package com.peekpick.member.presentation;

import com.peekpick.member.application.MemberApplicationData;

public class MemberCommandMapper {
    public static MemberCommandPayload.InformationResponse mapToResponse(
            MemberApplicationData.MemberInfoResult memberInformation
    ) {
        return new MemberCommandPayload.InformationResponse(
                memberInformation.nickname(),
                memberInformation.email(),
                memberInformation.preferenceHour(),
                memberInformation.stocks(),
                memberInformation.notificationEnabled()
        );
    }
}
